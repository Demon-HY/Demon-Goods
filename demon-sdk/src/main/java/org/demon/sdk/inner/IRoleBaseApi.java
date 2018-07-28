package org.demon.sdk.inner;

import org.demon.sdk.entity.Role;
import org.demon.sdk.environment.Env;

/**
 * 角色操作相关接口
 */
public interface IRoleBaseApi {

    /**
     * 创建角色
     * @param env
     * @param roleName 角色名
     * @param description 角色描述
     * @return
     */
    Role addRole(Env env, String roleName, String description);

    /**
     * 编辑角色
     * @param env
     * @param role 角色信息
     * @return
     */
    Role editRole(Env env, Role role);

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return
     */
    boolean removeRole(Env env, Long roleId);
}
