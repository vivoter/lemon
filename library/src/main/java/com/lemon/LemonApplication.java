package com.lemon;

import android.app.Application;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/18 8:47]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/18 8:47]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class LemonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //init base module
        ApplicationEngine.start(getApplicationContext());
    }
}
