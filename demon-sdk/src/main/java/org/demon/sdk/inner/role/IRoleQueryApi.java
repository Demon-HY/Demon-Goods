package org.demon.sdk.inner.role;

import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.vo.RoleUserVo;
import org.demon.sdk.entity.vo.UserRoleVo;
import org.demon.sdk.environment.Env;

import java.util.List;

/**
 * 角色查询相关接口
 */
public interface IRoleQueryApi {

    /**
     * 获取所有角色
     *
     * @param env
     * @return
     */
    List<Role> getRoles(Env env) throws Exception;

    /**
     * 获取角色信息
     *
     * @param env
     * @param roleName 角色名
     * @return
     */
    Role getRole(Env env, String roleName) throws Exception;

    /**
     * 获取角色信息
     *
     * @param env
     * @param roleId 角色ID
     * @return
     */
    Role getRole(Env env, Long roleId) throws Exception;

    /**
     * 枚举用户角色信息
     *
     * @param env
     * @param userId 用户ID
     * @return
     */
    UserRoleVo getUserRole(Env env, Long userId) throws Exception;

    /**
     * 枚举用户角色信息以及权限
     *
     * @param env
     * @param userId 用户ID
     * @return
     */
    UserRoleVo getUserRoleRight(Env env, Long userId) throws Exception;

    /**
     * 获取角色用户
     *
     * @param env
     * @param roleId 角色ID
     * @return
     */
    RoleUserVo getRoleUser(Env env, Long roleId) throws Exception;

    /**
     * 获取角色用户
     *
     * @param env
     * @param roleName 角色名称
     * @return
     */
    RoleUserVo getRoleUser(Env env, String roleName) throws Exception;

    RoleUserVo getRoleUser(Env env, Role role) throws Exception;
}
