package org.demon.module.right;

import org.demon.sdk.config.AbstractDefaultConfig;
import org.javatuples.Pair;

/**
 * 角色模块配置文件
 */
public class RightConfigAbstract extends AbstractDefaultConfig {

    public static final String MODULE_NAME = "right";

    static {
        RIGHT_CHECK_RIGHT = new Pair<>("RIGHT_CHECK_RIGHT", "查看权限");
        RIGHT_CREATE_RIGHT = new Pair<>("RIGHT_CREATE_RIGHT", "创建权限");
        RIGHT_EDIT_RIGHT = new Pair<>("RIGHT_EDIT_RIGHT", "编辑权限");
        RIGHT_DELETE_RIGHT = new Pair<>("RIGHT_DELETE_RIGHT", "删除权限");
        RIGHT_CHECK_ROLE_RIGHT = new Pair<>("RIGHT_CHECK_ROLE_RIGHT", "查看角色权限");
        RIGHT_SET_ROLE_RIGHT = new Pair<>("RIGHT_SET_ROLE_RIGHT", "设置角色权限");
    }

    /** 查看权限需要该权限 */
    public static final Pair<String, String> RIGHT_CHECK_RIGHT;

    /** 创建权限需要该权限 */
    public static final Pair<String, String> RIGHT_CREATE_RIGHT;

    /** 编辑权限需要该权限 */
    public static final Pair<String, String> RIGHT_EDIT_RIGHT;

    /** 删除权限需要该权限 */
    public static final Pair<String, String> RIGHT_DELETE_RIGHT;

    /** 查看角色权限需要该权限 */
    public static final Pair<String, String> RIGHT_CHECK_ROLE_RIGHT;

    /** 设置角色权限需要该权限 */
    public static final Pair<String, String> RIGHT_SET_ROLE_RIGHT;
}
