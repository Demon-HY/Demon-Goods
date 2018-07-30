package org.demon.module.right;

import org.demon.sdk.config.DefaultConfig;
import org.javatuples.Pair;

/**
 * 角色模块配置文件
 */
public class RightConfig extends DefaultConfig {

    public static final String MODULE_NAME = "right";

    /**
     * 查看权限需要该权限
     */
    public static final Pair<String, String> RIGHT_CHECK_RIGHT = new Pair<String, String>("RIGHT_CHECK_RIGHT", "查看权限");
}
