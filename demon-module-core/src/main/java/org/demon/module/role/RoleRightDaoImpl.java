package org.demon.module.role;

import org.demon.sdk.entity.RoleRight;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RoleRightDaoImpl extends CommonDaoImpl<RoleRight> {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
