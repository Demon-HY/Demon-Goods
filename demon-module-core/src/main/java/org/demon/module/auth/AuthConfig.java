package org.demon.module.auth;

import org.demon.sdk.config.DefaultConfig;

public class AuthConfig extends DefaultConfig {

    /**
     * 默认token的寿命,单位秒
     */
    public static long DEFAULT_TOKEN_AGE = 3600;

    /**
     * Session 中的登录信息
     */
    public static final String SESSION_TOKEN = "token";

    public static final String LOGINID_EMAIL = "email";
    public static final String LOGINID_PHONE = "phone";
    public static final String LOGINID_NAME = "name";

    /**
     * MD5加密标识
     */
    public static final String ALG_MD5 = "MD5";

    /**
     * SSHA加密标识
     */
    public static final String ALG_SSHA = "SSHA";
}
