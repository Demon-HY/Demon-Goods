package org.demon.user.api.impl;

import org.demon.user.api.UserApi;
import org.demon.user.dao.UserDaoImpl;
import org.demon.web.common.environment.Env;
import org.demon.web.utils.ClientResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

/**
 * 业务逻辑处理类
 *
 * @author Demon-Coffee
 * @since 1.0
 */
@Service
public class UserApiImpl implements UserApi {

	private Logger logger = Logger.getLogger(UserApiImpl.class);

	@Autowired
	private UserDaoImpl userDao;

	@Override
	public ClientResult findList(Env env) {
		return null;
	}

	@Override
	public ClientResult update(Env env) {
		return null;
	}

	@Override
	public ClientResult delete(Env env) {
		return null;
	}

	@Override
	public ClientResult save(Env env) {
		return null;
	}

	@Override
	public ClientResult findDetail(Env env) {
		return null;
	}
}
