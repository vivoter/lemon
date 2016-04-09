package com.lemon.model;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.contant]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/7 11:04]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/7 11:04]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public enum StatusCode {
    SUCCESS("00000","成功"),
    UN_LOGIN("10000","APP用户未登录！"),
    CAPTCHA_ERROR("10001"," 短信验证码错误！"),
    HAD_REGISTERED("10002","手机号已经被注册！"),
    NOT_REGISTERED("10003","未注册账户"),
    NAME_PWD_ERROR("10004","账号密码错误"),
    DEVICE_NOT_EXIST("10005","设备不存在"),
    DEVICE_NOT_ACTIVATE("10006","设备未激活"),
    SERVER_ERROR("99999","服务器内部异常");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private StatusCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return code;
    }
}
