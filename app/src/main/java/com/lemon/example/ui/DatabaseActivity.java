package com.lemon.example.ui;

import android.widget.Button;
import android.widget.TextView;

import com.lemon.LemonActivity;
import com.lemon.LemonContext;
import com.lemon.LemonDaoManager;
import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.bean.BeanFactory;
import com.lemon.example.R;
import com.lemon.example.model.CarModel;
import com.lemon.util.ParamUtils;
import com.lemon.util.RandomUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.ui]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 11:22]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 11:22]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Layout(id = R.layout.activity_database)
public class DatabaseActivity extends LemonActivity {

    @FieldView(id = R.id.tvResult)
    public TextView tvResult;

    @OnClick(id = R.id.btnAdd)
    public void addClick() {
        CarModel model = new CarModel();
        model.setName("car:" + RandomUtils.getRandom(1000));
        daoManager.create(CarModel.class, model);
        lemonMessage.sendMessage("添加完成");
        queryClick();
    }

    @OnClick(id = R.id.btnUpdate)
    public void updateClick() throws SQLException {
        List<CarModel> list = daoManager.queryAllOrderBy(CarModel.class, "id", false);
        if (ParamUtils.isEmpty(list)) {
            return;
        }

        for (CarModel model : list) {
            model.setName(model.getName()+":update:"+RandomUtils.getRandom(1000));
            daoManager.update(CarModel.class,model);
        }
        lemonMessage.sendMessage("更新完成");
        queryClick();
    }

    @OnClick(id = R.id.btnRemove)
    public void removeClick() {
        try {
            daoManager.deleteAll(CarModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lemonMessage.sendMessage("删除完成");
        queryClick();
    }

    @OnClick(id = R.id.btnQuery)
    public void queryClick() {
        clearClick();
        List<CarModel> list = daoManager.queryAllOrderBy(CarModel.class, "id", false);
        StringBuilder sb = new StringBuilder();
        if (ParamUtils.isEmpty(list)) {
            return;
        }

        for (CarModel model : list) {
            sb.append(model.toString()).append("\n");
        }
        tvResult.setText(sb.toString());
    }

    @OnClick(id = R.id.btnClear)
    public void clearClick() {
        tvResult.setText("");
    }

}
