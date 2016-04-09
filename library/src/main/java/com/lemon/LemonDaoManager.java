package com.lemon;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.exception.AppException;
import com.lemon.util.LogUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/1/18 14:11]
 * 修改人:    [xflu]
 * 修改时间:  [2016/1/18 14:11]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class LemonDaoManager {
    @Autowired
    public LemonDatabaseHelper lemonDatabaseHelper;

    public <T> void create(Class<T> cls, Object object) {
        try {
            lemonDatabaseHelper.getDao(cls).create(object);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.e(e);
        }
    }

    public <T> List<T> queryAll(Class<T> cls) {
        try {
            return lemonDatabaseHelper.getDao(cls).queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.e(e);
        }
        return null;
    }

    public <T> List<T> queryAllOrderBy(Class<T> cls,String orderColumn,boolean ascending){
        try {
            QueryBuilder queryBuilder = lemonDatabaseHelper.getDao(cls).queryBuilder();
            queryBuilder.orderBy(orderColumn, ascending);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            return query(cls, preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.e(e);
        }
        return null;
    }

    @Deprecated //unable to use
    private <T> List<T> query(Class<T> cls, String sql) {
        try {
            GenericRawResults<T> rawResults = lemonDatabaseHelper.getDao(cls).queryRaw(sql);
            List<T> resultList = rawResults.getResults();
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T queryOne(Class<T> cls, String idName, String idValue) throws SQLException {
        List<T> lst = query(cls, idName, idValue);
        if (null != lst && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }


    public <T> T queryOne(Class<T> cls, String[] attributeNames, String[] attributeValues) throws SQLException {
        List<T> lst = query(cls, attributeNames, attributeValues);
        if (null != lst && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    public <T> List<T> query(Class<T> cls, String attributeName, String attributeValue) throws SQLException {
        QueryBuilder queryBuilder = lemonDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where().eq(attributeName, attributeValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(cls, preparedQuery);
    }

    public <T> List<T> query(Class<T> cls, String attributeName, String attributeValue,String orderColumn,boolean ascending) throws SQLException {
        QueryBuilder queryBuilder = lemonDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where().eq(attributeName, attributeValue);
        queryBuilder.orderBy(orderColumn,ascending);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(cls, preparedQuery);
    }

    public <T> List<T> query(Class<T> cls, PreparedQuery<T> preparedQuery) throws SQLException {
        Dao dao = lemonDatabaseHelper.getDao(cls);
        return dao.query(preparedQuery);
    }

    public <T> List<T> query(Class<T> cls, String[] attributeNames, String[] attributeValues) throws SQLException {
        if (attributeNames.length != attributeValues.length) {
            throw new AppException("params size is not equal");
        }
        QueryBuilder queryBuilder = lemonDatabaseHelper.getDao(cls).queryBuilder();
        Where wheres = queryBuilder.where();
        for (int i = 0; i < attributeNames.length; i++) {
            wheres.eq(attributeNames[i], attributeValues[i]).and();
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(cls, preparedQuery);
    }

    public <T> int delete(Class<T> cls, PreparedDelete<T> preparedDelete) throws SQLException {
        Dao dao = lemonDatabaseHelper.getDao(cls);
        return dao.delete(preparedDelete);
    }

    public <T> int delete(Class<T> cls, T t) throws SQLException {
        Dao dao = lemonDatabaseHelper.getDao(cls);
        return dao.delete(t);
    }

    public <T> int delete(Class<T> cls, List<T> lst) throws SQLException {
        Dao dao = lemonDatabaseHelper.getDao(cls);
        return dao.delete(lst);
    }

    public <T> int delete(Class<T> cls, String[] attributeNames, String[] attributeValues) throws SQLException {
        List<T> lst = query(cls, attributeNames, attributeValues);
        if (null != lst && !lst.isEmpty()) {
            return delete(cls, lst);
        }
        return 0;
    }

    public <T> int deleteById(Class<T> cls, String idName, String idValue) throws SQLException {
        T t = queryOne(cls, idName, idValue);
        if (null != t) {
            return delete(cls, t);
        }
        return 0;
    }

    public <T> int deleteAll(Class<T> cls)throws SQLException {
       return delete(cls,queryAll(cls));
    }

    public <T> int update(Class<T> cls, T t) throws SQLException {
        Dao dao = lemonDatabaseHelper.getDao(cls);
        return dao.update(t);
    }

    public <T> Dao getDao(Class<T> cls)throws SQLException {
        return lemonDatabaseHelper.getDao(cls);
    }

    public <T> boolean isTableExsits(Class<T> cls) throws SQLException {
        return lemonDatabaseHelper.getDao(cls).isTableExists();
    }

    public <T> long count(Class<T> cls) throws SQLException {
        return lemonDatabaseHelper.getDao(cls).countOf();
    }
}
