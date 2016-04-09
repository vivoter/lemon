package com.lemon.converter;

import com.lemon.model.BaseParam;
import com.lemon.util.AssertUtil;
import com.lemon.util.MethodUtil;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.converter]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 14:20]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 14:20]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class BaseParamConverter {

    public String convert(BaseParam param) {

        try {
            StringBuilder sbParams = new StringBuilder();
            Field[] fields = ArrayUtils.addAll(param.getClass().getDeclaredFields(), param.getClass().getSuperclass().getDeclaredFields());
            AssertUtil.notEmpty(fields);

            String methodName = "";
            String propertyName = "";
            int length = fields.length;
            for (int i = 0; i < length; i++) {
                propertyName = fields[i].getName();
                if (propertyName.equals("class")) {
                    continue;
                }
                /*if(fields[i].getType().toString().equals("boolean")){
                    methodName = "is"+ convertFirstCharUppcase(propertyName);
                }else {
                    methodName = "get" + convertFirstCharUppcase(propertyName);
                }*/
                methodName = "get" + convertFirstCharUppcase(propertyName);
                Object result = MethodUtil.invokeMethod(param, methodName);
                if (result != null) {
                    if (i == (length - 1)) {
                        sbParams.append(propertyName).append("=").append(result);
                    } else {
                        sbParams.append(propertyName).append("=").append(result).append("&");
                    }
                } else {
                    if (isEmptyValueInsert()) {
                        if (i == (length - 1)) {
                            sbParams.append(propertyName).append("=");
                        } else {
                            sbParams.append(propertyName).append("=").append("&");
                        }
                    }
                }
            }

            return sbParams.toString();
        } catch (Exception e) {
            throw new RuntimeException("BaseParamConverter convert Error : " + e.getMessage(), e);
        }
    }

    protected boolean isEmptyValueInsert() {
        return false;
    }

    private static String convertFirstCharUppcase(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
