package org.demon.sdk.environment;

/**
 * Env 工具类
 */
public class EnvUtils {

    /**
     * 获取系统内部使用的Env
     * @return
     */
    public static Env systemEnv() {
        Env env = new Env();
        env.moduleName = "system";
        env.continueRight = true;

        return env;
    }
}
