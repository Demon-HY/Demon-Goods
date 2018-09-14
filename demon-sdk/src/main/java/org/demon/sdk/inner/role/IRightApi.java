package org.demon.sdk.inner.role;

import org.demon.sdk.model.entity.Right;
import org.demon.sdk.environment.Env;

import java.util.List;

/**
 * 权限接口
 *
 * @author Demon-HY
 */
public interface IRightApi {

    /**
     * 创建权限
     *
     * @param env
     * @param right 权限
     */
    void createRight(Env env, Right right) throws Exception;

    /**
     * 删除权限
     *
     * @param env
     * @param right 权限
     */
    void deleteRight(Env env, Right right) throws Exception;

    /**
     * 设置角色权限
     *
     * @param env
     * @param roleId 角色ID
     * @param rights 权限集合
     */
    boolean setRoleRight(Env env, Long roleId, List<Right> rights) throws Exception;

    /**
     * save item which in oldRights but no in rights to removeRights
     *
     * @param oldRights    旧权限
     * @param rights       新权限
     * @param removeRights 需要删除的权限
     * @param addRights    需要新增的权限
     */
    void checkId(List<Right> oldRights, List<Right> rights, List<Right> removeRights, List<Right> addRights);

    /**
     * 获取所有权限
     *
     * @param env
     */
    List<Right> getRights(Env env) throws Exception;

    /**
     * 获取所有权限
     *
     * @param env
     * @param moduleName 模块名
     */
    List<Right> getRights(Env env, String moduleName) throws Exception;

    /**
     * 获取所有角色权限
     *
     * @param env
     * @param roleId 角色ID
     */
    List<Right> getRoleRights(Env env, Long roleId) throws Exception;

    /**
     * 获取权限
     *
     * @param env
     * @param rightName 权限名
     * @return
     */
    Right getRight(Env env, String rightName) throws Exception;


}
