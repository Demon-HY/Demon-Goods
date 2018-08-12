package org.demon.module.right;

import org.demon.sdk.entity.Right;
import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.RoleRight;
import org.demon.sdk.entity.User;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDao;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.demon.utils.db.DBUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RightDaoImpl extends CommonDaoImpl<Right> {

    @Resource
    private GenertedJdbcTemplate jdbcTemplate;

    private GenertedJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 获取所有权限
     */
    List<Right> getRights() throws SQLException {
        CommonDao.Criteria criteria = createCriteria();
        return selectByCriteria(criteria, Right.class);
    }

    /**
     * 获取模块所有权限
     * @param moduleName 模块名
     */
    List<Right> getRights(String moduleName) throws SQLException {
        CommonDao.Criteria criteria = createCriteria();
        criteria.eq("module", moduleName);
        return selectByCriteria(criteria, Right.class);
    }

    /**
     * 获取角色所有权限
     *
     * @param roleId 角色ID
     */
    List<Right> getRoleRights(Long roleId) throws SQLException {
        StringBuilder sb = DBUtils.getSelectFrom(Right.class);
        sb.append(" LEFT JOIN role_right on role_right.`right_id`=right.`id` ")
                .append(" WHERE role_right.role_id=? ");

        return getJdbcTemplate().query(sb.toString(), new BeanPropertyRowMapper<>(Right.class), roleId);
    }


    public Right getRight(String rightName) throws SQLException {
        CommonDao.Criteria criteria = this.createCriteria();
        criteria.eq("name", rightName);

        return selectOneByCriteria(criteria, Right.class);
    }
}
