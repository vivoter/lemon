package com.lemon.example.ui;

import android.widget.Button;
import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.example.R;
import com.lemon.model.param.AppUpdateParam;
import com.lemon.model.result.AppUpdateResult;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.ui]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 10:44]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 10:44]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id= R.layout.activity_net)
public class NetActivity extends LemonActivity {
    @FieldView(id=R.id.btnGet)
    public Button btnGet;

    @FieldView(id=R.id.btnPost)
    public Button btnPost;

    @FieldView(id=R.id.btnClear)
    public Button btnClear;

    @FieldView(id=R.id.tvResult)
    public TextView tvResult;

    @OnClick(id=R.id.btnGet)
    public void getClick(){
        AppUpdateParam param = new AppUpdateParam();
        apiManager.update(param);
    }

    @OnClick(id=R.id.btnClear)
    public void clearClick(){
        tvResult.setText("");
    }

    public void onEventMainThread(AppUpdateResult result){
        tvResult.setText(result.getRetData().toString());
    }

}
