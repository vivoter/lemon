package com.lemon;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.util.SettingUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.util]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/7 11:31]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/7 11:31]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class LemonCacheManager {

    private static Map<String, Object> cacheMap = new HashMap<>();

    @Autowired
    public Context mContext;

    public void putBean(String key, Object object) {
        cacheMap.put(key, object);
    }

    public Object getBean(String key) {
        return cacheMap.get(key);
    }

    public boolean containBean(String key) {
        return cacheMap.containsKey(key);
    }

    public void putBean(Class cls, Object object) {
        cacheMap.put(cls.getSimpleName(), object);
    }

    public <T> T getBean(String key, Class<T> cls) {
        return (T) cacheMap.get(key);
    }

    public <T> T getBean(Class cls) {
        return (T) cacheMap.get(cls.getSimpleName());
    }

    public void removeBean(String key) {
        if (cacheMap.containsKey(key)) {
            cacheMap.remove(key);
        }
    }
}
