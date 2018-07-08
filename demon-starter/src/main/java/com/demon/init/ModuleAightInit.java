package com.demon.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听工程启动的监听事件,完成模块权限初始化
 */
@Component
public class ModuleAightInit implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ModuleAightInit() {}

    /**
     * 各模块初始化
     * 1. 扫描包,查找到所有的权限值
     * 2. 将查询到的权限值写入数据库
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 二次调用问题
        // 传统的application.xml和project-servlet.xml配置中会出现二次调用的问题。主要原因是初始化root容器之后，
        // 会初始化project-servlet.xml对应的子容器
        if(applicationReadyEvent.getApplicationContext().getParent() != null) {
            return;
        }

        logger.info("监听工程启动的监听事件,开始模块权限初始化");
    }
}
