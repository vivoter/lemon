package com.lemon.event;

/**
 * 项目名称:  [House]
 * 包:        [com.lemon.event]
 * 类描述:    [类描述]
 * 创建人:    [xiaofeng.lu]
 * 创建时间:  [2016/3/17 14:45]
 * 修改人:    [xiaofeng.lu]
 * 修改时间:  [2016/3/17 14:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class ServerErrorEvent {

    public ServerErrorEvent(String msg) {
        this.msg = msg;
    }

    public ServerErrorEvent() {
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ServerErrorEvent{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
