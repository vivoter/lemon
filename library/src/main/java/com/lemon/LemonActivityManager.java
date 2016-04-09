package com.lemon;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.event.ActivityEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/3 23:54]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/3 23:54]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class LemonActivityManager {

    @Autowired
    public Context mContext;
    private Map<String, Activity> activityMap;

    @InitMethod
    public void init() {
        EventBus.getDefault().register(this);
        activityMap = new HashMap<>();
    }

    public void onEventMainThread(ActivityEvent event) {
        activityMap.put(event.getActivityName(), event.getActivity());
    }

    public Activity getCurrentActivity(){
        return activityMap.get(getCurrentActivityName());
    }

    private String getCurrentActivityName() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getClassName();
    }

}
