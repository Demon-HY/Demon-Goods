package org.demon.sdk.inner;

import org.demon.sdk.entity.Role;
import org.demon.sdk.environment.Env;

import java.util.List;

/**
 * 内部模块访问接口
 *
 * @author Demon-HY
 */
public interface IRoleApi {

    /**
     * 获取所有角色
     * @param env
     * @return
     */
    List<Role> getRoles(Env env) throws Exception;
}
