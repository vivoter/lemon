package com.lemon;

import android.content.Context;

import com.lemon.bean.BeanFactory;
import com.lemon.init.InitEngine;
import com.lemon.util.ParamUtils;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/17 16:17]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/17 16:17]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ApplicationEngine {

    private static Context mContext;

    /**
     * 初始化基础模块
     * this method insert into Application onCreate method
     * @param context
     */
    public static void start(Context context){
        mContext = context;

        //init beanfactory
        BeanFactory.getInstance().setContext(mContext);
        BeanFactory.getInstance().parser();

        //start initializers
        InitEngine initEngine = BeanFactory.getInstance().getBean(InitEngine.class);
        if(!ParamUtils.isNull(initEngine)){
            initEngine.start();
        }
    }

}
