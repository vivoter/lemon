package com.lemon.example.ui;

import android.app.Fragment;

import com.lemon.LemonActivity;
import com.lemon.annotation.Layout;
import com.lemon.example.R;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.ui]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 10:28]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 10:28]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id = R.layout.activity_fagment_container)
public class FragmentContainerActivity extends LemonActivity {

    @Override
    protected void initView() {
        Fragment fg1 = new DemoFragement();
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fg1)
                .show(fg1).commit();
    }

}
