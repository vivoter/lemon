package com.lemon.net;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.ParamType;
import com.lemon.annotation.ReturnType;
import com.lemon.model.BaseParam;
import com.lemon.model.param.AppUpdateParam;
import com.lemon.model.result.AppUpdateResult;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.net]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 14:07]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 14:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class ApiManager {

    @Autowired
    public Context mContext;
    @Autowired
    public NetEngine netEngine;

    @ParamType(value = AppUpdateParam.class)
    @ReturnType(value = AppUpdateResult.class)
    public void update(BaseParam param) {
        netEngine.invoke(param);
    }



}
