package org.demon.module.role;

import org.demon.sdk.entity.RoleRight;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RoleRightDaoImpl extends CommonDaoImpl<RoleRight> {

    @Resource
    private GenertedJdbcTemplate jdbcTemplate;

    private GenertedJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
