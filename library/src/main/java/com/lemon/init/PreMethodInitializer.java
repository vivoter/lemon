package com.lemon.init;

import com.lemon.bean.BeanFactory;
import com.lemon.util.LogUtils;
import com.lemon.util.ParamUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.init]
 * 类描述:    [执行配置好的初始化方法]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/18 14:15]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/18 14:15]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class PreMethodInitializer extends AbstractInitializer {
    @Override
    public Object initialize(Object... objects) throws Exception {

        Map<String,Object> beanMap = BeanFactory.getInstance().getBeanMap();
        Map<String,String> initMethodMap = BeanFactory.getInstance().getInitMethodMap();
        for (Map.Entry<String, String> entry : initMethodMap.entrySet()) {
            try {
                Object target = beanMap.get(entry.getKey());
                if(ParamUtils.isNull(target)){
                    LogUtils.e("PreMethodInitializer initialize , can not find bean :" + entry.getKey());
                    continue;
                }
                Method initMethod = target.getClass().getMethod(entry.getValue());
                if (!ParamUtils.isNull(initMethod)) {
                    initMethod.invoke(target);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("PreMethodInitializer initialize :" + e.getMessage());
            }
        }
        return null;
    }
}
