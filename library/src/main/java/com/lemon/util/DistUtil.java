package com.lemon.util;

import java.text.DecimalFormat;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.util]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/19 10:26]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/19 10:26]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DistUtil {

    public static String convertDistance(double distance) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        if (distance >= 1000) {
            return decimalFormat.format(distance / 1000) + "公里";
        } else {
            return decimalFormat.format(distance) + "米";
        }

    }

}
