package com.demon.right.dao;

import com.demon.right.entity.Role;
import com.demon.web.common.jdbc.CommonDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RoleDaoImpl extends CommonDaoImpl<Role> {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
