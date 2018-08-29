package org.demon.module.right;

import org.demon.module.right.exception.RightCheckException;
import org.demon.module.role.RoleUtils;
import org.demon.sdk.entity.Right;
import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.vo.UserRoleVo;
import org.demon.sdk.environment.Env;
import org.demon.sdk.inner.role.IRightApi;
import org.demon.sdk.inner.role.IRoleQueryApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.starter.common.logger.AbstractLogClass;
import org.demon.utils.ValidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限校验
 */
@Service
public class RightUtils extends AbstractLogClass {

    @Autowired
    private IRightApi rightApi;
    @Autowired
    private IRoleQueryApi roleQueryApi;

    /**
     * 验证当前用户是否有操作该接口的权限
     *
     * @param env
     * @param moduleName 权限所在模块名
     * @param rightName    权限名
     */
    public void checkRight(Env env, String moduleName, String rightName) throws Exception {
        if (env.continueRight) {
//           logger.warn("模块:{} 内部调用接口,跳过权限校验", env.moduleName);
           return;
        }

        // 检查参数
        if (ValidUtils.isBlank(moduleName) || ValidUtils.isBlank(rightName)) {
            throw new RightCheckException(RetCodeEnum.ERR_RIGHT_INVALID);
        }
        // 检查用户信息是否存在
        if (env.login == null) {
            throw new RightCheckException(RetCodeEnum.ERR_USER_NOT_LOGIN);
        }

        // 获取用户所有角色及权限
        UserRoleVo userRoleRight = roleQueryApi.getUserRoleRight(env, env.userId);
        List<Role> roles = userRoleRight.roles;
        if (ValidUtils.isBlank(roles)) {
            throw new RightCheckException(RetCodeEnum.ERR_USER_NOT_ROLE);
        }

        // 获取要验证的权限信息

        for (Role role : roles) {
            // 超级管理员拥有所有权限,不需要校验直接跳过
            if (RoleUtils.checkSuperAdmin(role)) {
                logger.info("超级管理员跳过权限验证");
                return;
            }

            if (ValidUtils.isBlank(role.rights)) {
                logger.info("角色: {} 没有设置权限", role.name);
                continue;
            }

            for (Right right : role.rights) {
                if (right.name.equals(rightName)) {
                    return;
                }
            }
        }

        logger.error("用户: {} 没有访问权限: {} 的权限", env.userId, rightName);
        throw new RightCheckException(RetCodeEnum.ERR_FORBIDDEN);
    }

}
