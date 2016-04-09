package com.lemon.event;

import java.util.List;

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
public class BaseEvent<T> {


    private String className;
    private boolean result = true;

    private String msg;

    private T data;

    private List<T> dataList;

    private ShowType showType;

    public boolean isSuccess() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
    }

    public boolean isResult() {
        return result;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public enum ShowType {
        PROGRESS_BEGIN(1, "progress_begin"),
        PROGRESS_END(2, "progress_end");
        private String name;
        private int code;

        ShowType(int code, String name) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code + ":" + name;
        }
    }
}
