package org.demon.module.user;

import org.demon.sdk.config.DefaultConfig;
import org.javatuples.Pair;

public class UserConfig extends DefaultConfig {

    public static final String MODULE_NAME = "user";

    /**
     * 默认用户类型是 type=1(客户)
     */
    public static final int defaultUserType = 1;

    /**
     * 用户名特殊字符过滤规则
     */
    public static final String REGEX_USER_NAME = ".*(:|\\*|\\?|<|>|\\||\"|\\\\|/).*";

    /**
     * 特殊字符过滤规则
     */
    public static final String REGEX_SPECIAL_STRING = ".*(:|\\*|\\?|<|>|\\||\"|\\\\|/).*";

    /**
     * 邮箱正则
     */
    public static final String REGEX_USER_EMAIL = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";

    /** 正常状态，可正常登录并进行操作 */
    public static final int STATUS_NORMAL = 1;
    /** 锁定状态，不能进行正常登录以及操作 */
    public static final int STATUS_LOCK = 1 << 2;
    /** 删除状态 */
    public static final int STATUS_DELETE = 1 << 3;


    static {
        RIGHT_CHECK_USER = new Pair<>("RIGHT_CHECK_USER", "查看用户");
        RIGHT_SET_USER = new Pair<>("RIGHT_SET_USER", "设置用户");
        RIGHT_CREATE_USER = new Pair<>("RIGHT_CREATE_USER", "创建用户");
        RIGHT_DELETE_USER = new Pair<>("RIGHT_DELETE_USER", "删除用户");
        RIGHT_RESET_PSW = new Pair<>("RIGHT_RESET_PSW", "重置密码");
        RIGHT_LOCK_USER = new Pair<>("RIGHT_LOCK_USER", "锁定用户");
        RIGHT_UNLOCK_USER = new Pair<>("RIGHT_UNLOCK_USER", "解锁用户");
    }

    /**
     * 查看用户需要该权限
     */
    public static final Pair<String, String> RIGHT_CHECK_USER;

    /**
     * 设置用户需要该权限
     */
    public static final Pair<String, String> RIGHT_SET_USER;

    /**
     * 创建用户需要该权限
     */
    public static final Pair<String, String> RIGHT_CREATE_USER;

    /**
     * 删除用户需要该权限
     */
    public static final Pair<String, String> RIGHT_DELETE_USER;

    /**
     * 重置密码需要该权限
     */
    public static final Pair<String, String> RIGHT_RESET_PSW;

    /**
     * 锁定用户
     */
    public static final Pair<String, String> RIGHT_LOCK_USER;

    /**
     * 解锁用户
     */
    public static final Pair<String, String> RIGHT_UNLOCK_USER;
}
