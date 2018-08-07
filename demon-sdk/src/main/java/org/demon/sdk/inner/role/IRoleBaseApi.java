package org.demon.sdk.inner.role;

import org.demon.sdk.entity.Role;
import org.demon.sdk.environment.Env;

/**
 * 角色操作相关接口
 */
public interface IRoleBaseApi {

    /**
     * 创建角色
     *
     * @param env
     * @param role 角色信息
     * @return
     */
    Role createRole(Env env, Role role) throws Exception;

    /**
     * 编辑角色
     *
     * @param env
     * @param role 角色信息
     * @return
     */
    Role editRole(Env env, Role role) throws Exception;

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     */
    boolean deleteRole(Env env, Long roleId) throws Exception;
}
