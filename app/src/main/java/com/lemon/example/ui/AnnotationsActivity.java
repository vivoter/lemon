package com.lemon.example.ui;

import android.widget.Button;
import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.config.Config;
import com.lemon.example.R;

@Layout(id = R.layout.activity_anotations)
public class AnnotationsActivity extends LemonActivity {

    @FieldView(id = R.id.btnShow)
    public Button btnShow;
    @FieldView(id = R.id.tvValue)
    public TextView tvValue;

    @Override
    protected void initView() {
        String message ="主要学习 @Layout @FieldView @OnClick 三个注解用法";

        tvValue.setText(message);
    }

    @OnClick(id = R.id.btnShow)
    public void showClick() {
        lemonMessage.sendMessage("showClick");
    }
}
