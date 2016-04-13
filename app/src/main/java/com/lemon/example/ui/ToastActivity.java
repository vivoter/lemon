package com.lemon.example.ui;

import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.LemonContext;
import com.lemon.LemonMessage;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.example.R;

import java.io.IOException;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.ui]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 13:08]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 13:08]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id= R.layout.activity_toast)
public class ToastActivity extends LemonActivity {

    @FieldView(id=R.id.tvValue)
    private TextView tvValue;

    @Override
    protected void initView() {
        String message ="功能说明:任意位置,想toast消息,不需要考虑线程子线程\n" +
                "使用方式:LemonContext.getBean(LemonMessage.class).sendMessage(\"message\")";

        tvValue.setText(message);
    }

    @OnClick(id = R.id.btnToast)
    public void toastClick(){
        LemonContext.getBean(LemonMessage.class).sendMessage("toast message");
    }
}
