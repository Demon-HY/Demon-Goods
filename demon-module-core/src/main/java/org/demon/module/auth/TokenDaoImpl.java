package org.demon.module.auth;

import org.demon.sdk.entity.Token;
import org.demon.starter.common.jdbc.CommonDao;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TokenDaoImpl extends CommonDaoImpl<Token> {

	@Resource
	private JdbcTemplate jdbcTemplate;

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 获取 token
	 * @param token 用户唯一标识
	 * @return
	 */
	public Token getToken(String token) {
		CommonDao.Criteria criteria = this.createCriteria();
		criteria.eq("token", token);
		return selectOneByCriteria(criteria, Token.class);
	}
}
