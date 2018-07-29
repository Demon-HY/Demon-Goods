package org.demon.utils.db;

import org.demon.utils.ValidUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;

/**
 * Mysql 工具类
 */
public class DBUtils {

    /**
     * 获取查询的字段信息
     *
     * @return [select ... from ]
     */
    public static StringBuilder getSelectFrom(Class<?> entityClass) {
        String tableName = getTableName(entityClass);
        String fieldNames = getFields(tableName, entityClass);
        if (fieldNames == null || fieldNames.length() < 1) return null;

        StringBuilder sql = new StringBuilder("SELECT " + fieldNames + " FROM ");
        sql.append(getTableName(entityClass)).append(" ");

        return sql;
    }

    public static String getTableName(Class<?> entityClass) {
        return entityClass.getAnnotation(Table.class).name();
    }

    public static String getPrimyName(Class<?> entityClass) {
        String idName;
        try {
            Field[] fields = entityClass.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    idName = f.getAnnotation(Column.class).name();
                    return idName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取表字段名
     *
     * @param tableName   表名,为空时返回的字段格式为:param1,param2,...
     *                    不为空时返回的格式为 tableName.param1,tableName.param2,...
     * @param entityClass
     * @return
     */
    public static String getFields(String tableName, Class<?> entityClass) {
        try {
            Field[] field = entityClass.getDeclaredFields();
            StringBuilder fields = new StringBuilder();
            for (Field f : field) {
                if (f.getAnnotation(Column.class) == null) continue;
                if (ValidUtils.isBlank(tableName)) {
                    fields.append(f.getAnnotation(Column.class).name()).append(",");
                } else {
                    fields.append(tableName).append(".").append(f.getAnnotation(Column.class).name()).append(",");
                }
            }
            return fields.length() == 0 ? null : fields.substring(0, fields.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
