package com.lemon.example.ui;

import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.config.Config;
import com.lemon.example.R;

@Layout(id= R.layout.activity_config)
public class ConfigActivity extends LemonActivity {

    @FieldView(id = R.id.tvValue)
    public TextView tvValue;

    @Override
    protected void initView() {
        String message ="功能：配置模块主要将一些常用的静态数据放置在配置文件中\n" +
                "配置:打开asserts/config/config.json,将需要配置的数据配置到json文件\n" +
                "使用:Config.getValue(\"key\"),Config.getIntValue(\"key\"),Config.getBooleanValue(\"key\")\n" +
                "例子:Config.getValue(\"debug\") = " + Config.getValue("debug");

        tvValue.setText(message);
    }
}
