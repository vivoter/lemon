package com.lemon.init;

import com.lemon.util.LogUtils;
import com.lemon.util.ParamUtils;

import java.util.List;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.init]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 11:24]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 11:24]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class InitEngine {

    public List<AbstractInitializer> initializers;

    /**
     * 初始化引擎,程序启动的时候会被调用
     */
    public void start() {
        try {
            if(ParamUtils.isEmpty(initializers)){
                return;
            }
            for (AbstractInitializer initializer : initializers) {
                initializer.initialize();
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

}
