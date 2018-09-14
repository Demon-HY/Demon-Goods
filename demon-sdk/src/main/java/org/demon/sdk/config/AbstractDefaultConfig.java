package org.demon.sdk.config;

/**
 * 每个模块都需要实现该类，里面会配置模块的基本信息和权限控制
 */
public abstract class AbstractDefaultConfig {

    /**
     * 模块名
     */
    public static final String MODULE_NAME = "";

    /**
     * 支持匿名访问的路径
     */
    public static final String ANNO_PATH = "/anon/";
}
