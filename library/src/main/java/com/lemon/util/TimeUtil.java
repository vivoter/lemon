package com.lemon.util;

import java.util.Date;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.util]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/19 22:31]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/19 22:31]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class TimeUtil {

    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    public static String getLossDate(long timeStamp) {
        try {
            Date d2 = new Date(timeStamp);
            Date d1 = new Date();

            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            String result = "" + days + "天" + hours + "小时" + minutes + "分";
            return result;
        } catch (Exception e) {
        }
        return "";
    }
}
