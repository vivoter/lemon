package com.lemon;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.model.StatusCode;
import com.lemon.model.bean.UpdateInfo;
import com.lemon.model.result.AppUpdateResult;
import com.lemon.util.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import de.greenrobot.event.EventBus;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/20 23:37]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/20 23:37]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class LemonUpdate {

    private volatile static com.lemon.LemonUpdate LemonUpdate;

    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;

    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL   = 1;

    @Autowired
    public Context mContext;
    //通知对话框
    private Dialog noticeDialog;
    //下载对话框
    private Dialog downloadDialog;
    // 进度条
    private ProgressDialog mProgressDialog ;
    //'已经是最新' 或者 '无法获取最新版本' 的对话框
    private Dialog latestOrFailDialog;
    //进度条
    private ProgressBar mProgress;
    //显示下载数值
    private TextView mProgressText;
    //查询动画
    private ProgressDialog mProDialog;
    //进度值
    private int progress = 0;
    //下载线程
    private Thread downLoadThread;
    //终止标记
    private boolean interceptFlag;
    //提示语
    private String updateMsg = "";
    //返回的安装包url
    private String apkUrl = "";
    //下载包保存路径
    private String savePath = "";
    //apk保存完整路径
    private String apkFilePath = "";
    //临时下载文件路径
    private String tmpFilePath = "";
    //下载文件大小
    private String apkFileSize;
    //已下载文件大小
    private String tmpFileSize;

    private String curVersionName = "";
    private int curVersionCode;

    @InitMethod
    public void init(){
        curVersionName = AppUtils.getVerName(mContext);
        curVersionCode = AppUtils.getVerCode(mContext);
        EventBus.getDefault().register(this);
    }

    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgressDialog.setProgress(msg.arg1);
                    break;
                case DOWN_OVER:
                    mProgressDialog.dismiss();
                    installApk();
                    break;
                case DOWN_NOSDCARD:
                    mProgressDialog.dismiss();
                    Toast.makeText(mContext, "无法下载安装文件，请检查SD卡是否挂载", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public void onEventMainThread(AppUpdateResult result){
        if(result.getRetCode().equals(StatusCode.SUCCESS.getCode())){
            UpdateInfo updateInfo = result.getRetData();
            if(curVersionCode < Integer.parseInt(updateInfo.getVersionCode())){
                apkUrl = updateInfo.getVersionDownUrl();
                updateMsg = updateInfo.getVersionDescription();
                showNoticeDialog();
            }else{
                showLatestOrFailDialog(DIALOG_TYPE_LATEST);
            }
        }
    }


    /**
     * 显示'已经是最新'或者'无法获取版本信息'对话框
     */
    private void showLatestOrFailDialog(int dialogType) {
        if (dialogType == DIALOG_TYPE_LATEST) {
            Toast.makeText(mContext,"您当前已经是最新版本",Toast.LENGTH_SHORT).show();
        } else if (dialogType == DIALOG_TYPE_FAIL) {
            Toast.makeText(mContext,"无法获取版本更新信息",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示版本更新通知对话框
     */
    private void showNoticeDialog(){
        Builder builder = new Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("立即更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示下载对话框
     */
    @SuppressWarnings("deprecation")
    private void showDownloadDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("正在下载中,请稍等...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int i)
            {
                //点击“确定按钮”取消对话框
                mProgressDialog.cancel();
                interceptFlag = true;
            }
        });
        mProgressDialog.show();
        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                String apkName = curVersionName+".apk";
                String tmpApk = curVersionName+".tmp";
                //判断是否挂载了SD卡
                String storageState = Environment.getExternalStorageState();
                if(storageState.equals(Environment.MEDIA_MOUNTED)){
                    savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+curVersionName+"/Update/";
                    File file = new File(savePath);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    apkFilePath = savePath + apkName;
                    tmpFilePath = savePath + tmpApk;
                }

                //没有挂载SD卡，无法下载文件
                if(apkFilePath == null || apkFilePath == ""){
                    mHandler.sendEmptyMessage(DOWN_NOSDCARD);
                    return;
                }

                File ApkFile = new File(apkFilePath);

                //是否已下载更新文件
                if(ApkFile.exists()){
					/*downloadDialog.dismiss();
					installApk();
					return;*/
                    ApkFile.delete();
                }

                //输出临时下载文件
                File tmpFile = new File(tmpFilePath);
                FileOutputStream fos = new FileOutputStream(tmpFile);

                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                //显示文件大小格式：2个小数点显示
                DecimalFormat df = new DecimalFormat("0.00");
                //进度条下面显示的总文件大小
                apkFileSize = df.format((float) length / 1024 / 1024) + "MB";

                int count = 0;
                byte buf[] = new byte[1024];


                do{
                    int numread = is.read(buf);
                    count += numread;
                    //进度条下面显示的当前下载文件大小
                    tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
                    //当前进度值
                    progress =(int)(((float)count / length) * 100);
                    //更新进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = DOWN_UPDATE;
                    msg.arg1 = progress;
                    mHandler.sendMessage(msg);
//		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        //下载完成 - 将临时下载文件转成APK文件
                        if(tmpFile.renameTo(ApkFile)){
                            //通知安装

                            mHandler.sendEmptyMessage(DOWN_OVER);
                        }
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//点击取消就停止下载

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     */
    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    private void installApk(){
        File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

}
