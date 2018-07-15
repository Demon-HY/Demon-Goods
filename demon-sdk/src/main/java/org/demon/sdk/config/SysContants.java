package org.demon.sdk.config;

import java.io.Serializable;

/**
 * 系统常量
 */
public class SysContants implements Serializable {

    private static final long serialVersionUID = -3522414077760942120L;

    /**
     * 请求头中的客户端设备类型:web/pc/iphone/android
     */
    public static final String HEADER_DEVICE = "X-Device";
    /**
     * 请求头中的用户唯一标识
     */
    public static final String HEADER_TOKEN = "X-Token";
}
