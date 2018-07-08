package com.demon.right.api;

import com.demon.web.common.environment.Env;
import com.demon.web.utils.ClientResult;

/**
 * 内部模块访问接口
 *
 * @author Demon-Coffee
 * @since 1.0
 */
public interface RoleApi {

	ClientResult findList(Env env);

	ClientResult update(Env env);

	ClientResult delete(Env env);

	ClientResult save(Env env);

	ClientResult findDetail(Env env);
}
