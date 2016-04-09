package com.lemon.util;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

/**
 * 项目名称:  [ecp-android]
 * 包:        [com.module.util]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/8/6 9:44]
 * 修改人:    [xflu]
 * 修改时间:  [2015/8/6 9:44]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class MethodUtil extends MethodUtils {
    /**
     * 获取当前执行方法的名称,调此方法的上一层
     * @return
     */
    public static String getCurrentMethodName(){
    	return Thread.currentThread().getStackTrace()[4].getMethodName();
    }
    
    /**
     * 获取当前执行方法的名称,调此方法的上一层
     * @return
     */
    public static String getCurrentClassName(){
        return ClassUtils.getShortClassName(Thread.currentThread().getStackTrace()[4].getClassName());
    }

    /**
     * 获取方法
     * @param cls
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class<?> cls,String methodName,Class<?>... parameterTypes){
        try {
            AssertUtil.notNull(cls);
            AssertUtil.hasText(methodName);
            return cls.getMethod(methodName,parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
