package org.demon.module.user;

import org.demon.sdk.entity.IdCard;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class IdCardDaoImpl extends CommonDaoImpl<IdCard> {

	@Resource
	private JdbcTemplate jdbcTemplate;

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
