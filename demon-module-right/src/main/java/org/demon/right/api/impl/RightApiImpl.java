package org.demon.right.api.impl;

import org.demon.right.dao.RightDaoImpl;
import org.demon.starter.common.environment.Env;
import org.demon.starter.utils.ClientResult;
import org.demon.right.api.RightApi;
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
public class RightApiImpl implements RightApi {

	private Logger logger = Logger.getLogger(RightApiImpl.class);

	@Autowired
	private RightDaoImpl rightDao;

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
