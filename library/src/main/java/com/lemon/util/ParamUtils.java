/**
 *
 */
package com.lemon.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @projectName EMALib
 * @PackageName com.android.emalib.util
 * @Title ParamUtils
 * @Author Luxiaofeng
 * @Date 2014-10-22
 * @Version V1.0
 */
public class ParamUtils {

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value.trim());
    }

    public static boolean isEmpty(byte[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isEmpty(String[] value) {
        return value == null || value.length <= 0;
    }

    public static boolean isEmpty(int[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isEmpty(double[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isEmpty(char[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isEmpty(Field[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isEmpty(Method[] value) {
        return value == null || value.length == 0;
    }


    public static boolean isEmpty(List<?> value) {
        return value == null || value.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> value) {
        return value == null || value.size() == 0;
    }

    public static boolean isEqual(Object value1, Object value2) {
        if (isNull(value1) || isNull(value2)) {
            return false;
        }

        return value1.equals(value2);
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNull(Object[] value) {
        return value == null || value.length <= 0;
    }

    //StringUtils.isNumber(String str);
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static int s2i(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String i2s(int value) {
        return String.valueOf(value);
    }

    public static Long s2l(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return 0l;
        }
    }

    public static String l2s(long value) {
        return String.valueOf(value);
    }

    public static String map2String(Map<String, String> params) {
        String strParams = "";
        if (!ParamUtils.isEmpty(params)) {
            strParams += "?";
            for (Map.Entry<String, String> entity : params.entrySet()) {
                strParams += entity.getKey() + "=" + entity.getValue() + "&";
            }
            if (strParams.endsWith("&")) {
                strParams = strParams.substring(0, strParams.length() - 1);
            }
        }
        return strParams;
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
                + "(^0[3-9] {1}d{2}-?d{7,8}$)|"
                + "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
                + "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}