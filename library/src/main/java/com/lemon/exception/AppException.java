package com.lemon.exception;

/**
 * 项目名称:  [House]
 * 包:        [com.lemon.exception]
 * 类描述:    [类描述]
 * 创建人:    [xiaofeng.lu]
 * 创建时间:  [2016/3/17 14:45]
 * 修改人:    [xiaofeng.lu]
 * 修改时间:  [2016/3/17 14:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class AppException extends RuntimeException {


    public AppException(String message){
        super(message);
    }
}
