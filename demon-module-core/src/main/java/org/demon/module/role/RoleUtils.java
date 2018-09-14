package org.demon.module.role;

import org.demon.sdk.model.entity.Role;

/**
 * 角色工具类
 */
public class RoleUtils {

    /**
     * 验证角色是否为超级管理员用户
     * @param role
     * @return
     */
    public static boolean checkSuperAdmin(Role role) {
        return role.name.equals(RoleConfigAbstract.SUPER_ADMIN_NAME);
    }
}
