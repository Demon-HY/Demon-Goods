package org.demon.sdk.inner.role;

import org.demon.sdk.entity.Right;
import org.demon.sdk.environment.Env;

import java.sql.SQLException;
import java.util.List;

/**
 * 内部模块访问接口
 *
 * @author Demon-HY
 */
public interface IRightApi {

    /**
     * 创建权限
     * @param right
     */
    void createRight(Env env, Right right) throws Exception;

    /**
     * 删除权限
     * @param right
     */
    void deleteRight(Env env, Right right) throws Exception;

    /**
     * 设置角色权限
     * @param env
     * @param roleId
     * @param rights
     * @return
     * @throws Exception
     */
    boolean setRoleRight(Env env, Long roleId, List<Right> rights) throws Exception;

    /**
     * save item which in oldRights but no in rights to removeRights
     * @param oldRights 旧权限
     * @param rights 新权限
     * @param removeRights 需要删除的权限
     * @param addRights 需要新增的权限
     */
    void checkId(List<Right> oldRights, List<Right> rights, List<Right> removeRights, List<Right> addRights);

    /**
     * 获取所有权限
     * @param env
     * @return
     * @throws Exception
     */
    List<Right> getRights(Env env) throws Exception;

    /**
     * 获取所有权限
     * @param env
     * @param moduleName 模块名
     * @return
     * @throws Exception
     */
    List<Right> getRights(Env env, String moduleName) throws Exception;

    /**
     * 获取所有角色权限
     * @param env
     * @return
     * @throws Exception
     */
    List<Right> getRoleRights(Env env, Long roleId) throws Exception;

    /**
     * 获取权限
     * @param env
     * @param rightName 权限名
     * @return
     */
    Right getRight(Env env, String rightName) throws Exception;


}
