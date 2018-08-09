package org.demon.module.role;

import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.User;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDao;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.demon.utils.db.DBUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleDaoImpl extends CommonDaoImpl<Role> {

	@Resource
	private GenertedJdbcTemplate jdbcTemplate;

	private GenertedJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 获取角色
	 * @param roleName
	 * @return
	 */
    public Role getRole(String roleName) throws SQLException {
		CommonDao.Criteria criteria = this.createCriteria();
		criteria.eq("name", roleName);

		return selectOneByCriteria(criteria, Role.class);
	}

	public Role getRole(Long roleId) {
    	return selectById(roleId, Role.class);
	}

	/**
	 * 获取用户角色信息
	 * @param userId 用户ID
	 * @return
	 */
	public List<Role> getUserRoles(Long userId) throws SQLException {
		StringBuilder sb = DBUtils.getSelectFrom(Role.class);
		sb.append(" role left join role_user on role.`id`=role_user.`role_id` where role_user.`user_id`=? ");

		return getJdbcTemplate().query(sb.toString(), new BeanPropertyRowMapper<>(Role.class), userId);
	}

	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> getRoles() throws SQLException {
		CommonDao.Criteria criteria = createCriteria();
		return selectByCriteria(criteria, Role.class);
	}

	/**
	 * 获取角色用户
	 * @param role 角色信息
	 * @return
	 */
	public List<User> getRoleUsers(Role role) throws SQLException {
		StringBuilder sb = DBUtils.getSelectFrom(User.class);
		sb.append(" user left join role_user on role.`id`=role_user.`role_id` where role.user.`role_id=? ");

		return getJdbcTemplate().query(sb.toString(), new BeanPropertyRowMapper<>(User.class), role.id);
	}

	public void addRole(Role role) throws SQLException {
		insert(role);
	}
}
