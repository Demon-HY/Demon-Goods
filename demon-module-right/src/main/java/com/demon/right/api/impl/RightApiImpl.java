package com.demon.right.api.impl;

import com.demon.right.dao.RightDaoImpl;
import com.demon.web.common.environment.Env;
import com.demon.web.utils.ClientResult;
import com.demon.right.api.RightApi;
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
