package org.demon.right.api;

import org.demon.web.common.environment.Env;
import org.demon.web.utils.ClientResult;

/**
 * 内部模块访问接口
 *
 * @author Demon-Coffee
 * @since 1.0
 */
public interface RightApi {

	ClientResult findList(Env env);

	ClientResult update(Env env);

	ClientResult delete(Env env);

	ClientResult save(Env env);

	ClientResult findDetail(Env env);
}
