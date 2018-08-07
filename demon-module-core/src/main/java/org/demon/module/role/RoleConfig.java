package org.demon.module.role;

import org.demon.sdk.config.DefaultConfig;
import org.javatuples.Pair;

/**
 * 角色模块配置文件
 */
public class RoleConfig extends DefaultConfig {

    // 模块名
    public static final String MODULE_NAME = "role";

    /** 查看角色需要该权限 */
    public static final Pair<String, String> RIGHT_CHECK_ROLE;
    /** 创建角色需要该权限 */
    public static final Pair<String, String> RIGHT_CREATE_ROLE;
    /** 删除角色需要该权限 */
    public static final Pair<String, String> RIGHT_DELETE_ROLE;
    /** 设置角色权限需要该权限 */
    public static final Pair<String, String> RIGHT_SET_ROLE_RIGHT;
    /** 设置用户角色需要该权限 */
    public static final Pair<String, String> RIGHT_SET_USER_ROLE;
    /** 设置角色用户需要该权限 */
    public static final Pair<String, String> RIGHT_SET_ROLE_USER;
    /** 查看用户角色需要该权限 */
    public static final Pair<String, String> RIGHT_CHECK_USER_ROLE;
    /** 查看角色用户需要该权限 */
    public static final Pair<String, String> RIGHT_CHECK_ROLE_USER;
    /** 查看角色权限需要该权限 */
    public static final Pair<String, String> RIGHT_CHECK_ROLE_RIGHT;

    static {
        RIGHT_CHECK_ROLE = new Pair<>("RIGHT_CHECK_ROLE", "查看角色");
        RIGHT_CREATE_ROLE = new Pair<>("RIGHT_CREATE_ROLE", "创建角色");
        RIGHT_DELETE_ROLE = new Pair<>("RIGHT_DELETE_ROLE", "删除角色");
        RIGHT_SET_ROLE_RIGHT = new Pair<>("RIGHT_SET_ROLE_RIGHT", "设置角色权限");
        RIGHT_SET_USER_ROLE = new Pair<>("RIGHT_SET_USER_ROLE", "设置用户角色");
        RIGHT_SET_ROLE_USER = new Pair<>("RIGHT_SET_ROLE_USER", "设置角色用户");
        RIGHT_CHECK_USER_ROLE = new Pair<>("RIGHT_CHECK_USER_ROLE", "查看用户角色");
        RIGHT_CHECK_ROLE_USER = new Pair<>("RIGHT_CHECK_ROLE_USER", "查看角色用户");
        RIGHT_CHECK_ROLE_RIGHT = new Pair<>("RIGHT_CHECK_ROLE_RIGHT", "查看角色权限");
    }
}