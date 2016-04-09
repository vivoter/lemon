package com.lemon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.annotation]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/18 13:22]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/18 13:22]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InitMethod {
    String name() default "";
}
