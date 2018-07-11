package org.demon.sdk.event.type.test;


import org.demon.sdk.environment.Env;
import org.demon.sdk.event.Event;

/**
 * Test 事件
 * <p>
 * Created by Demon-HY on 2018/4/30 0030.
 */
public class TestEvent extends Event {


    public Env env;

    public Integer flag;

    /**
     * 覆写构造函数
     *
     * @param source 触发事件的对象
     * @param env    上下文环境变量
     * @param flag   标记:测试事件通过和事件拦截:1-事件通过,2-事件拦截
     */
    public TestEvent(Object source, Env env, Integer flag) {
        super(source);
        this.env = env;
        this.flag = flag;
    }
}
