package org.demon.module.role;

import org.demon.sdk.entity.Role;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RoleDaoImpl extends CommonDaoImpl<Role> {

	@Resource
	private JdbcTemplate jdbcTemplate;

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
