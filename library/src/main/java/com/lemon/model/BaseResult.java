package com.lemon.model;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.model]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 14:17]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 14:17]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class BaseResult<T> {
    protected String retMsg;
    protected String retCode;
    protected T retData;
    protected BaseParam param;

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public T getRetData() {
        return retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }

    public BaseParam getParam() {
        return param;
    }

    public void setParam(BaseParam param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "retMsg='" + retMsg + '\'' +
                ", retCode='" + retCode + '\'' +
                ", retData=" + retData +
                ", param=" + param +
                '}';
    }
}
