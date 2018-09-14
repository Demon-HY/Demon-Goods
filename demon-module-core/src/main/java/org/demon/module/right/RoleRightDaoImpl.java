package org.demon.module.right;


import org.demon.sdk.model.entity.Right;
import org.demon.sdk.model.entity.RoleRight;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;

@Repository
public class RoleRightDaoImpl extends CommonDaoImpl<RoleRight> {

    @Resource
    private GenertedJdbcTemplate jdbcTemplate;

    private GenertedJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 设置角色权限
     * @param roleId 角色ID
     * @param addRight 角色权限
     */
    public int setRoleRight(Long roleId, Right addRight) throws SQLException {
        RoleRight roleRight = new RoleRight(roleId, addRight.rightId);
        return insert(roleRight);
    }
}
