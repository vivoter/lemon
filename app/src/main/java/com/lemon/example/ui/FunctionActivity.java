package com.lemon.example.ui;

import android.os.Message;
import android.widget.ListView;

import com.lemon.LemonActivity;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.example.R;
import com.lemon.example.adapter.FunctionAdapter;
import com.lemon.example.model.FunctionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/7 17:06]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/7 17:06]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id= R.layout.activity_function)
public class FunctionActivity extends LemonActivity {
    @FieldView(id = R.id.lvFunction)
    public ListView listView;

    @Override
    protected void initData() {
        handler.sendEmptyMessageDelayed(1,1000);
    }

    private void initFunctions(){
        List<FunctionModel> list = new ArrayList<>();
        FunctionModel functionModel = null;

        functionModel = new FunctionModel();
        functionModel.setActivityCls(BeanAnnotationActivity.class);
        functionModel.setName("Bean注解模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(BeanXmlActivity.class);
        functionModel.setName("XML注解模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(AnnotationsActivity.class);
        functionModel.setName("Activity注入模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(FragmentContainerActivity.class);
        functionModel.setName("Fragment注入模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(NetActivity.class);
        functionModel.setName("网络模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(DatabaseActivity.class);
        functionModel.setName("数据库模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(InitActivity.class);
        functionModel.setName("初始化模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(ConfigActivity.class);
        functionModel.setName("配置模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(CacheActivity.class);
        functionModel.setName("缓存模块");
        list.add(functionModel);

        functionModel = new FunctionModel();
        functionModel.setActivityCls(ToastActivity.class);
        functionModel.setName("Toast模块");
        list.add(functionModel);
        listView.setAdapter(new FunctionAdapter(handler, mContext, list));
    }

    @Override
    public void notificationMessage(Message msg) {
        initFunctions();
    }

}
