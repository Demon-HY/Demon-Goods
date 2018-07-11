package org.demon.web.module;

/**
 * 每个模块都需要实现该类，里面会配置模块的基本信息和权限控制
 */
public abstract class DefaultConfig {

    /**
     * 模块名
     */
    public static final String MODULE_NAME = "";

    /**
     * 拥有该模块所有权限，这个在考虑到底需不需要
     */
    public static final String ACL_ALL = "所有权限";
}
