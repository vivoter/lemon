package com.lemon.example.ui;

import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.config.Config;
import com.lemon.example.R;
import com.lemon.example.model.CarModel;
import com.lemon.util.ParamUtils;
import com.lemon.util.RandomUtils;

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
@Layout(id = R.layout.activity_cache)
public class CacheActivity extends LemonActivity {

    @FieldView(id = R.id.tvValue)
    public TextView tvValue;
    @FieldView(id = R.id.tvRule)
    public TextView tvRule;


    @Override
    protected void initView() {
        String message = "注意事项:\n1.能用缓存存储的尽量不存数据库\n" +
                "2.Activity传递数据也可以通过共享缓存传递\n" +
                "3.缓存可以直接通过class存,或者取";
        tvRule.setText(message);
    }

    @OnClick(id = R.id.btnPut)
    public void putClick() {
        CarModel carModel = new CarModel();
        carModel.setId(RandomUtils.getRandom(1000));
        carModel.setName("car:"+ RandomUtils.getRandom(1000));
        cacheManager.putBean(CarModel.class,carModel);
        tvValue.setText("    ");
    }

    @OnClick(id = R.id.btnGet)
    public void getClick() {
        CarModel model = cacheManager.getBean(CarModel.class);

        if(ParamUtils.isNull(model)){
            return;
        }

        tvValue.setText(model.toString());
    }

}
