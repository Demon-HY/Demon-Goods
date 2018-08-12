package org.demon.starter.common.jdbc;

import com.alibaba.fastjson.JSONObject;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.utils.ValidUtils;
import org.demon.utils.beans.MapUtils;
import org.demon.utils.db.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Demon-HY on 2018/3/15 0015.
 */
@Service
public class CommonDaoImpl<T> implements CommonDao<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private GenertedJdbcTemplate jdbcTemplate;

	private GenertedJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public T selectById(Object idObj, Class<T> entityClass) {
		try {
			String idName = DBUtils.getPrimyName(entityClass);
			if (idName == null || idName.length() < 1) return null;

			StringBuilder sql = DBUtils.getSelectFrom(entityClass);
			if (sql == null) return null;

			sql.append(" WHERE ").append(idName).append(" = ? ");

			Object[] arg = {idObj};

			if (logger.isDebugEnabled()) {
				logger.debug("SQL: {}, Params: {}", sql.toString(), idObj);
			}
			List list = getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(entityClass), arg);
			if (list == null || list.isEmpty()) {
				return null;
			}

			return (T) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> selectByCriteria(CommonDao.Criteria criteria, Class<T> entityClass) throws SQLException {
		StringBuilder sql = DBUtils.getSelectFrom(entityClass);
		if (sql == null) return null;

		sql.append(criteria.getCriteriaSQL());

		Object[] params = criteria.getParam().toArray(new Object[criteria.getParam().size()]);

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: {}, Params: {}", sql.toString(), params);
		}

		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(entityClass), params);
	}

    @Override
    public T selectOneByCriteria(CommonDao.Criteria criteria, Class<T> entityClass) throws SQLException {
        StringBuilder sql = DBUtils.getSelectFrom(entityClass);
        if (sql == null) return null;
        sql.append(criteria.getCriteriaSQL());

        Object[] params = criteria.getParam().toArray(new Object[criteria.getParam().size()]);

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: {}, Params: {}", sql.toString(), params);
		}

        List<T> list = getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(entityClass), params);
        if (ValidUtils.isBlank(list) || list.size() != 1) {

            return null;
        }
        return list.get(0);
    }

	@Override
	public Long countByCriteria(CommonDao.Criteria criteria, Class<T> entityClass) {
		String sql = "SELECT COUNT(1) AS num FROM `" + DBUtils.getTableName(entityClass) + "` " +  criteria.getCriteriaSQL();

		Object[] params = criteria.getParam().toArray(new Object[criteria.getParam().size()]);

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: {}, Params: {}", sql, params);
		}

		Map<String, Object> map = jdbcTemplate.queryForMap(sql, params);
		return (Long) map.get("num");
	}

	@Override
	public int removeById(Object idObj, Class<T> entityClass) {
		String sql = new StringBuilder()
				.append("DELETE FROM `")
				.append(DBUtils.getTableName(entityClass))
				.append("` WHERE ")
				.append(DBUtils.getPrimyName(entityClass))
				.append(" = ? ").toString();

		Object[] arg = {idObj};

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: {}, Params: {}", sql, idObj);
		}

		return getJdbcTemplate().update(sql, arg);
	}

	@Override
	public int insert(T entity) throws SQLException {
		Field[] fields = entity.getClass().getDeclaredFields();
		Map<String, Object> obj = MapUtils.objectToMap(entity);

		StringBuilder sql1 = new StringBuilder("INSERT INTO `")
				.append(DBUtils.getTableName(entity.getClass()))
				.append("` (");
		StringBuilder sql2 = new StringBuilder(" VALUES(");
		List<Object> args = new ArrayList<>();

		String pkName = DBUtils.getPrimyName(entity.getClass());

		// 主键ID字段,为了回写自增ID到对象中
		Field pkField = null;

		for (Field field : fields) {
			if (field.getAnnotation(Column.class) == null) continue;

			String key = field.getName(); // 实体类字段名

			String name = field.getAnnotation(Column.class).name(); // 数据库字段名
			if (name.equals(pkName)) {
				pkField = field;
				continue;
			}
			Object arg = obj.get(key);
			if (ValidUtils.isEmpty(arg)) continue;

			sql1.append(name).append(",");
			sql2.append("?,");
			args.add(arg);
		}

		sql1.deleteCharAt(sql1.length() - 1);
		sql1.append(") ");
		sql2.deleteCharAt(sql2.length() - 1);
		sql2.append(") ");
		String sql = sql1.append(sql2).toString();

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: {}, Params: {}", sql, args.toArray());
		}

		int generatedId = getJdbcTemplate().updateGenerated(sql, args.toArray());

        if (pkField != null) {
            try {
                pkField.setAccessible(true);
                if (pkField.getType() == Integer.class) {
                    pkField.setInt(entity, new Integer(generatedId));
                }
                else if (pkField.getType() == Long.class) {
					pkField.set(entity, new Long(new Integer(generatedId).longValue()));
                }
                else if (pkField.getType() == String.class) {
                    pkField.set(entity, String.valueOf(generatedId));
                } else {
                    logger.warn("主键类型目前只支持 Integer/Long/String, 实体类: {} 的主键类型为: {}",
                            entity.getClass().getName(), pkField.getType());
                    throw new SQLException("主键类型目前只支持 Integer/Long/String");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

		return generatedId;
	}

	public void test(T entity) throws IllegalAccessException {
		Field[] fields = entity.getClass().getDeclaredFields();

		String pkName = DBUtils.getPrimyName(entity.getClass());

		// 主键ID字段,为了回写自增ID到对象中
		Field pkField = null;

		for (Field field : fields) {
			if (field.getAnnotation(Column.class) == null) continue;

			String name = field.getAnnotation(Column.class).name(); // 数据库字段名
			if (name.equals(pkName)) {
				pkField = field;
				break;
			}
		}

		if (pkField != null) {
			pkField.setAccessible(true);
			Long gen = 20L;
			pkField.set(entity, gen);
		}

		System.out.println("1");
	}

	@Override
	public int update(T entity) {
		Map<String, Object> obj = MapUtils.objectToMap(entity);
		StringBuilder sql1 = new StringBuilder("UPDATE `")
				.append(DBUtils.getTableName(entity.getClass()))
				.append("` SET ");
		String pkName = DBUtils.getPrimyName(entity.getClass());
		StringBuilder sql2 = new StringBuilder(" WHERE " + pkName + " = ? ");
		List<Object> args = new ArrayList<>();
		for (String key : obj.keySet()) {
			if (key.equals(pkName)) continue;

			Object arg = obj.get(key);
			if (ValidUtils.isEmpty(arg)) continue;

			sql1.append(key).append(" = ?,");
			args.add(arg);
		}

		sql1.deleteCharAt(sql1.length() - 1);
		args.add(obj.get(pkName));
		String sql = sql1.append(sql2).toString();

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: {}, Params: {}", sql, args.toArray());
		}

		return getJdbcTemplate().update(sql, args.toArray());
	}

	@Override
	public CommonDao.Criteria createCriteria() {
		return new Criteria();
	}

	/**
	 * 查询条件的实现
	 */
	class Criteria implements CommonDao.Criteria {
		private boolean not; //是否标记了非
		private boolean begin; //是否正在拼接第一个条件
		private boolean or;//是否修改连接词为OR
		StringBuilder criteriaSQL; //从where开始的条件sql
		List<Object> param; //参数列表
		String limitStr; //限制条数

		Criteria() {
			criteriaSQL = new StringBuilder("");
			param = new LinkedList<>();
			not = false;
			or = false;
			begin = true;
			limitStr = "";
		}

		public Criteria not() {
			not = true;
			return this;
		}

		@Override
		public CommonDao.Criteria or() {
			or = true;
			return this;
		}

		private void link() {
			//判断是否是第一个条件
			// ，如果是就加WHERE不加连接词
			// ，不是就直接加连接词
			if (begin) {
				criteriaSQL.append(" WHERE ");
			} else {
				if (or) {
					criteriaSQL.append(" OR ");
				} else {
					criteriaSQL.append(" AND ");
				}
			}
			or = false;
		}

		public Criteria eq(String field, Object val) {
			link();
			if (not) {
				criteriaSQL.append(field)
						.append(" != ?");
			} else {
				criteriaSQL.append(field)
						.append(" = ?");
			}
			not = false;
			begin = false;
			param.add(val);
			return this;
		}

		public Criteria like(String field, Object val) {
			link();
			if (not) {
				criteriaSQL.append(field)
						.append(" NOT LIKE ?");
			} else {
				criteriaSQL.append(field)
						.append(" LIKE ?");
			}
			not = false;
			begin = false;
			param.add("%" + val + "%");
			return this;
		}

		public Criteria between(String field, Object val1, Object val2) {
			link();
			if (not) {
				criteriaSQL.append(field)
						.append(" NOT BETWEEN ? AND ? ");
			} else {
				criteriaSQL.append(field)
						.append(" BETWEEN ? AND ? ");
			}
			not = false;
			begin = false;
			param.add(val1);
			param.add(val2);
			return this;
		}

		@Override
		public CommonDao.Criteria limit(int start, int row) {
			limitStr = " limit " + start + "," + row;
			return this;
		}

		public List<Object> getParam() {
			return this.param;
		}

		public StringBuilder getCriteriaSQL() {
			return new StringBuilder(criteriaSQL.toString() + limitStr);
		}
	}

	/**
	 * 获取字段信息以及描述
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	JSONObject getColumnCommentByTableName(String tableName) throws Exception {
		JSONObject obj = new JSONObject();

		Connection conn = getJdbcTemplate().getDataSource().getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(" SHOW FULL columns FROM " + tableName);
			while (rs.next()) {
				obj.put(rs.getString("Field"), rs.getString("Comment"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			assert rs != null;
			rs.close();
			stmt.close();
			conn.close();
		}
		return obj;
	}

	/**
	 * 获取表字段信息
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	List<TableFieldInfo> getFieldInfoByTableName(String tableName) throws Exception {
		List<TableFieldInfo> fieldInfos = new ArrayList<>();

		Connection conn = getJdbcTemplate().getDataSource().getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(" SHOW FULL columns FROM " + tableName);
			TableFieldInfo fieldInfo = new TableFieldInfo();
			while (rs.next()) {
				fieldInfo.setFieldName(rs.getString("Field"));
				fieldInfo.setFieldComment(rs.getString("Comment"));
				fieldInfo.setFieldType(rs.getString("Type"));
				String key = rs.getString("Key");
				if (key != null && key.equals("PRI")) {
					fieldInfo.setPrimary(true);
				} else {
					fieldInfo.setPrimary(false);
				}
				fieldInfo.setNull(rs.getString("Null").equals("YES"));

				fieldInfos.add(fieldInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			assert rs != null;
			rs.close();
			stmt.close();
			conn.close();
		}
		return fieldInfos;
	}

	/**
	 * 获取表信息
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	TableInfo getTableInfoByTableName(String tableName) throws Exception {
		Connection conn = getJdbcTemplate().getDataSource().getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		TableInfo tableInfo = new TableInfo();
		try {
			rs = stmt.executeQuery(" SHOW CREATE TABLE " + tableName);
			if (rs != null && rs.next()) {
				String createDDL = rs.getString(2);
				String comment = parse(createDDL);

				tableInfo.setTableName(tableName);
				tableInfo.setTableComment(comment);
				tableInfo.setListFieldInfos(getFieldInfoByTableName(tableName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			assert rs != null;
			rs.close();
			stmt.close();
			conn.close();
		}
		return tableInfo;
	}

	/**
	 * 返回注释信息
	 * @param all 数据库获取的字段描述， e.g:`update_time` datetime NOT NULL COMMENT '更新时间'
	 */
	private static String parse(String all) {
		String comment;
		int index = all.indexOf("COMMENT='");
		if (index < 0) {
			return "";
		}
		comment = all.substring(index + 9);
		comment = comment.substring(0, comment.length() - 1);
		return comment;
	}
}
