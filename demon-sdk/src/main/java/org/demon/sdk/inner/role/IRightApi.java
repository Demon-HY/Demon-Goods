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
     * 获取所有角色
     * @param env
     * @return
     * @throws Exception
     */
    List<Right> getRights(Env env) throws Exception;


    /**
     * 添加权限
     * @param right
     */
    void setRight(Right right) throws SQLException;

    /**
     * 删除权限
     * @param right
     */
    void deleteRight(Right right);
}
