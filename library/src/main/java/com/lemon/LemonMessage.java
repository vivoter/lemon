package com.lemon;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.config.Config;
import com.lemon.event.ActivityEvent;
import com.lemon.event.NetNotAvailableEvent;
import com.lemon.event.NetStartEvent;
import com.lemon.event.NetStopEvent;
import com.lemon.event.ParamErrorEvent;
import com.lemon.event.ServerErrorEvent;
import com.lemon.event.ToastEvent;
import com.lemon.util.ParamUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/28 23:10]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/28 23:10]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component(name = "lemonMessage")
public class LemonMessage {

    @Autowired
    public Context mContext;
    private MaterialDialog materialProgressDialog;
    private Map<String, Activity> activityMap;
    private int default_dissmiss;

    @InitMethod
    public void init() {
        EventBus.getDefault().register(this);
        activityMap = new HashMap<>();
        default_dissmiss = Integer.valueOf(Config.getValue("dismiss_loading_time")) * 1000;
    }

    public void onEventMainThread(ActivityEvent event) {
        activityMap.put(event.getActivityName(), event.getActivity());
    }

    public void onEventMainThread(ServerErrorEvent event) {
        handler.sendEmptyMessage(0);
        toast("服务器访问异常");
    }

    public void onEventMainThread(NetNotAvailableEvent event) {
        toast("网络不可用");
    }

    public void onEventMainThread(NetStartEvent event) {
        if (ParamUtils.isNull(activityMap.get(getCurrentActivityName()))) {
            return;
        }

        if (ParamUtils.isNull(materialProgressDialog)) {
            materialProgressDialog = new MaterialDialog.Builder(activityMap.get(getCurrentActivityName())).title("正在加载")
                    .content("请稍等...")
                    .progress(true, 0).titleColor(Color.BLACK)
                    .contentColor(Color.BLACK)
                    .backgroundColor(Color.WHITE)
                    .progressIndeterminateStyle(false).build();
        }
        if (materialProgressDialog != null && !materialProgressDialog.isShowing()) {
            materialProgressDialog.show();
        }
        handler.sendEmptyMessageDelayed(1, default_dissmiss);
    }

    public void onEventMainThread(ToastEvent event){
        Toast.makeText(activityMap.get(getCurrentActivityName()),event.getMessage(),Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(NetStopEvent event) {
        if (materialProgressDialog != null && materialProgressDialog.isShowing()) {
            materialProgressDialog.dismiss();
        }
        materialProgressDialog = null;
    }

    public void onEventMainThread(ParamErrorEvent event) {
        handler.sendEmptyMessage(0);
        toast("参数错误");
    }

    private void toast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private String getCurrentActivityName() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Activity.ACTIVITY_SERVICE);
        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getClassName();
    }

    public void sendMessage(String message){
        EventBus.getDefault().post(new ToastEvent(message));
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!ParamUtils.isNull(materialProgressDialog)) {
                materialProgressDialog.dismiss();
                materialProgressDialog = null;
            }
        }
    };

}