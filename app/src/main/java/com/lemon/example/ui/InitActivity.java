package com.lemon.example.ui;

import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.example.R;

@Layout(id = R.layout.activity_init)
public class InitActivity extends LemonActivity {

    @FieldView(id = R.id.tvInit)
    public TextView tvInit;

    @Override
    protected void initView() {
        String message = "分两类初始化\n" +
                "1、APP启动初始化:继承AbstractInitializer,实现initialize方法。并且将类对象配置在assets/config/bean.xml里面\n" +
                "2、类初始化方法,有两种方式:2.1 通过配置文件的方式 init-method=\"方法名\" 2.2 通过annotation的方式\"方法前加上@InitMethod\"";
        tvInit.setText(message);
    }

}
