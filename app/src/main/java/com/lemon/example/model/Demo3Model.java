package com.lemon.example.model;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.model]
 * 类描述:    [类描述]
 * 创建人:    [xiaofeng.lu]
 * 创建时间:  [2016/4/13 16:23]
 * 修改人:    [xiaofeng.lu]
 * 修改时间:  [2016/4/13 16:23]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */

@Component
public class Demo3Model {
    @Autowired
    public Context mContext;
    private String name;
    private String sex;
    private int age;

    @InitMethod
    public void init() {
        name = this.getClass().getSimpleName();
        sex = "男";
        age = 18;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Demo3Model{" +
                "mContext=" + mContext +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
