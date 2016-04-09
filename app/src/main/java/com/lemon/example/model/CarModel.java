package com.lemon.example.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 项目名称:  [lemon]
 * 包:        [com.lemon.example.model]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/4/8 11:23]
 * 修改人:    [xflu]
 * 修改时间:  [2016/4/8 11:23]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@DatabaseTable(tableName = "tb_car")
public class CarModel {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
