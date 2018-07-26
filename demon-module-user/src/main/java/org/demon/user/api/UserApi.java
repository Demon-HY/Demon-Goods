package org.demon.user.api;

import org.demon.starter.common.environment.Env;
import org.demon.starter.utils.ClientResult;

/**
 * 内部模块访问接口
 *
 * @author Demon-HY
 * @since 1.0
 */
public interface UserApi {

	ClientResult findList(Env env);

	ClientResult update(Env env);

	ClientResult delete(Env env);

	ClientResult save(Env env);

	ClientResult findDetail(Env env);
}
