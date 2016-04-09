package com.lemon.event;

import android.app.Activity;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/1 15:45]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/1 15:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ActivityEvent {
    public Activity activity;

    public ActivityEvent(Activity activity){
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getActivityName(){
        return activity.getClass().getName();
    }
}
