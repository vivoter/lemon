package com.lemon.event;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.event]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/31 15:51]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/31 15:51]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ToastEvent {
    String message;

    public ToastEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
