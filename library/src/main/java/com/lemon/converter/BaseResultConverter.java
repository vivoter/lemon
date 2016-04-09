package com.lemon.converter;

import com.lemon.event.ServerErrorEvent;
import com.lemon.model.BaseResult;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.converter]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 14:21]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 14:21]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class BaseResultConverter<T extends BaseResult> {
    protected static ObjectMapper objectMapper = new ObjectMapper();

    public <A> A convert(String result,Class<A> cls){
        try {
            return objectMapper.readValue(result, cls);
        } catch (IOException e) {
            String msg = "解析异常,请稍后重试" + "\n" + e.getMessage();
            EventBus.getDefault().post(new ServerErrorEvent(msg));
            throw new RuntimeException(String.format("BaseResultConvert:%s Error , %s", cls.getSimpleName(), e.getMessage()), e);
        }
    }
}
