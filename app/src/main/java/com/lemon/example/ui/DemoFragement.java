package com.lemon.example.ui;

import android.widget.Button;
import android.widget.TextView;

import com.lemon.LemonFragment;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.example.R;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.ui]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 10:29]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 10:29]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id = R.layout.demo_fragement)
public class DemoFragement extends LemonFragment {
    @FieldView(id = R.id.btnShow)
    public Button btnShow;
    @FieldView(id = R.id.tvValue)
    public TextView tvValue;

    @Override
    protected void initView() {
        String message = "主要学习 @Layout @FieldView @OnClick 三个注解用法";

        tvValue.setText(message);
    }

    @OnClick(id = R.id.btnShow)
    public void showClick() {
        lemonMessage.sendMessage("showClick");
    }
}
