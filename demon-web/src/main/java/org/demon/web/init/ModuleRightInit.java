package org.demon.web.init;

import org.demon.utils.ValidUtils;
import org.demon.utils.beans.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URLClassLoader;

/**
 * 监听工程启动的监听事件,完成模块权限初始化
 */
@Component
public class ModuleRightInit implements ApplicationListener<ApplicationReadyEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ModuleRightInit() {
    }

    @Value("${module.properties}")
    private String MODULE_PROPERTY_FILE_NAME;

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
        if (applicationReadyEvent.getApplicationContext().getParent() != null) {
            return;
        }

        // 考虑到效率问题,核心模块的配置文件包直接写死
        if (ValidUtils.isBlank(MODULE_PROPERTY_FILE_NAME)) {
            logger.warn("需要在 application.properties 中设置 module.properties, 用来加载各模块的权限");
            System.exit(-1);
        }
        for (String moduleName : MODULE_PROPERTY_FILE_NAME.split(",")) {
            try {
                loadModuleRight(moduleName);
            } catch (ClassNotFoundException e) {
                logger.error("加载模块 {} 类失败.", moduleName, e);
                System.exit(-1);
            }
        }


        logger.info("监听工程启动的监听事件,开始模块权限初始化");
    }

    /**
     * 加载模块权限
     * @param moduleName 模块名
     */
    private void loadModuleRight(String moduleName) throws ClassNotFoundException {
        URLClassLoader classLoader = (URLClassLoader) ClassLoader
                .getSystemClassLoader();
        Class<?> cls = classLoader.loadClass(String.format("org.demon.module.user.%s.%sConfig",
                moduleName));
    }
}
