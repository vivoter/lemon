package com.lemon.example.ui;

import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.LemonContext;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.example.R;
import com.lemon.example.model.Demo1Model;
import com.lemon.example.model.Demo2Model;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.ui]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 14:27]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 14:27]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id= R.layout.activity_bean_annotation)
public class BeanAnnotationActivity extends LemonActivity {
    @FieldView(id = R.id.tvValue)
    public TextView tvValue;

    @Override
    protected void initView() {
        String message ="1功能:如何使用注解依赖注入\n" +
                "2使用关键字：@Component @Autowired @RefBean @InitMethod\n" +
                "3使用方式：参考Demo1Model使用方法\n" +
                "4取得对象:LemonContext.getBean(Demo1Model.class)\n" +
                "5结果:"+LemonContext.getBean(Demo2Model.class).toString();
        tvValue.setText(message);
    }

}
