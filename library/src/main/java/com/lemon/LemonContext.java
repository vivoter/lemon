package com.lemon;

import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.bean.BeanFactory;
import com.lemon.util.Inflector;
import com.lemon.util.LogUtils;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [类描述]
 * 创建人:    [xiaofeng.lu]
 * 创建时间:  [2016/3/17 14:45]
 * 修改人:    [xiaofeng.lu]
 * 修改时间:  [2016/3/17 14:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class LemonContext {

    private static BeanFactory beanFactory;

    @InitMethod
    public void init() {
        beanFactory = BeanFactory.getInstance();
    }

    /**
     * 获取Bean
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName) {
        if (!beanFactory.containsBean(beanName)) {
            LogUtils.e("can not find bean,name:" + beanName);
            return null;
        }
        return (T) beanFactory.getBean(beanName);
    }

    /**
     * 获取 Bean
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        String beanName = Inflector.getInstance().camelCase(cls.getSimpleName(), false);
        if (!beanFactory.containsBean(beanName)) {
            LogUtils.e("can not find bean,name:" + beanName);
            return null;
        }
        return (T) beanFactory.getBean(beanName);
    }

}
