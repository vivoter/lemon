package com.lemon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lemon.bean.BeanFactory;
import com.lemon.config.Config;
import com.lemon.util.ParamUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [类描述]
 * 创建人:    [xiaofeng.lu]
 * 创建时间:  [2016/3/17 14:45]
 * 修改人:    [xiaofeng.lu]
 * 修改时间:  [2016/3/17 14:45]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class LemonDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Map<String, Dao> daos = new HashMap<>();
    public Context mContext;
    public List<String> tables;

    public LemonDatabaseHelper(Context context) {
        super(context.getApplicationContext(), Config.getDbName(), null, Config.getVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase database,
                         ConnectionSource connectionSource) {
        dropTables();
        createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource, int oldVersion, int newVersion) {
        dropTables();
        createTables();
    }

    public void createTables(){
        try {
            if(ParamUtils.isEmpty(tables)){
                return;
            }
            for (String tableItem : tables) {
                Class cls = Class.forName(tableItem);
                TableUtils.createTableIfNotExists(connectionSource, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropTables(){
        try {
            for (String tableItem : tables) {
                Class cls = Class.forName(tableItem);
                TableUtils.dropTable(connectionSource, cls, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();

        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}