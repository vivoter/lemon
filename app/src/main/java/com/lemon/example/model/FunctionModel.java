package com.lemon.example.model;

import android.app.Activity;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.model]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 9:19]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 9:19]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class FunctionModel {

    private String name;
    private Class<? extends Activity> activityCls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Activity> getActivityCls() {
        return activityCls;
    }

    public void setActivityCls(Class<? extends Activity> activityCls) {
        this.activityCls = activityCls;
    }
}
