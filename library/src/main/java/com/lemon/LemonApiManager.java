package com.lemon;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.net.NetEngine;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 11:07]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 11:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class LemonApiManager {

    @Autowired
    public Context mContext;
    @Autowired
    public NetEngine netEngine;

}

