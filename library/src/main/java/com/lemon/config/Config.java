package com.lemon.config;

import android.content.Context;

import com.lemon.exception.AppException;
import com.lemon.util.LogUtils;
import com.lemon.util.ParamUtils;
import com.lemon.util.RegUtils;
import com.lemon.util.StringUtils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.config]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/18 14:35]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/18 14:35]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class Config {

    public Context mContext;
    public String configPath;
    public static String version = "1";
    public static String dbname = "lemon.db";
    public static Map<String,String> configMap = new HashMap<>();

    public void parser() throws IOException {
        String strConfig = StringUtils.inputStream2String(mContext.getAssets().open(configPath));
        if(ParamUtils.isEmpty(strConfig)){
            String message = "can not find the config.json , please check the path configPath:"+configPath;
            LogUtils.e(message);
            throw new AppException(message);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        configMap = objectMapper.readValue(strConfig,Map.class);
    }

    public static boolean isDebug(){
        return configMap.get("debug").equals("true");
    }

    public static String getServerUrl(){
        return configMap.get("server_url");
    }

    public static String getConvertExt(){
        return configMap.get("convert_ext");
    }

    public static String getValue(String key){
        return configMap.get(key);
    }

    public static int getIntValue(String key){
        return Integer.valueOf(configMap.get(key));
    }

    public static boolean getBooleanValue(String key){
        return Boolean.valueOf(configMap.get(key));
    }

    public static String getDbName(){
        return dbname;
    }

    public static int getVersion(){
        return Integer.valueOf(version);
    }
}
