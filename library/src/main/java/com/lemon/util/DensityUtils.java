package com.lemon.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.util]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/2/21 17:17]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/2/21 17:17]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class DensityUtils {
    private DensityUtils() { }

    /**
     * Converts the given amount of pixels to a dp value.
     * @param pixels The pixel-based measurement
     * @return The measurement's value in dp, based on the device's screen density
     */
    public static float pxToDp(float pixels) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return pixels / (metrics.densityDpi / 160f);
    }

    /**
     * Converts the given dp measurement to pixels.
     * @param dp The measurement, in dp
     * @return The corresponding amount of pixels based on the device's screen density
     */
    public static float dpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }
}
