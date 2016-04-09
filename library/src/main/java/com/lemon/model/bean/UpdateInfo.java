package com.lemon.model.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/21 0:02]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/21 0:02]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateInfo {

    /**
     * versionName : zr-gps
     * versionCode : 2
     * versionDescription : versionDescription
     * versionDownUrl : http://120.25.152.206:8888/app/zrgps-20160120.apk
     * isForce : false
     */

    private String versionName;
    private String versionCode;
    private String versionDescription;
    private String versionDownUrl;
    private String isForce;

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    public void setVersionDownUrl(String versionDownUrl) {
        this.versionDownUrl = versionDownUrl;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public String getVersionDownUrl() {
        return versionDownUrl;
    }

    public String getIsForce() {
        return isForce;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "versionName='" + versionName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionDescription='" + versionDescription + '\'' +
                ", versionDownUrl='" + versionDownUrl + '\'' +
                ", isForce='" + isForce + '\'' +
                '}';
    }
}
