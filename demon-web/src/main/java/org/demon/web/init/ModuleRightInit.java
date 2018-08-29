package org.demon.web.init;

import org.demon.sdk.entity.Right;
import org.demon.sdk.environment.Env;
import org.demon.sdk.environment.EnvUtils;
import org.demon.sdk.inner.role.IRightApi;
import org.demon.starter.common.logger.AbstractLogClass;
import org.demon.utils.ValidUtils;
import org.demon.utils.beans.StringUtils;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 监听工程启动的监听事件,完成模块权限初始化
 */
@Component
public class ModuleRightInit extends AbstractLogClass implements ApplicationListener<ApplicationReadyEvent> {

    public ModuleRightInit() {
    }

    /**
     * 所有要设置权限的模块
     */
    @Value("${module.properties}")
    private String MODULE_PROPERTY_FILE_NAME;

    /**
     * 权限字段名前缀
     */
    private static final String RIGHT_PREFIX = "RIGHT_";

    @Autowired
    private IRightApi rightApi;

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
            } catch (Exception e) {
                logger.error("系统异常", e);
                System.exit(-1);
            }
        }


        logger.info("监听工程启动的监听事件,开始模块权限初始化");
    }

    /**
     * 加载模块权限
     *
     * @param moduleName 模块名
     */
    private void loadModuleRight(String moduleName) throws Exception {
        URLClassLoader classLoader = (URLClassLoader) ClassLoader
                .getSystemClassLoader();

        // 加载权限配置类
        Field[] fields;
        Object obj;
        try {
            Class<?> rightInfoClass = classLoader.loadClass(String.format("org.demon.module.%s.%sConfig",
                    moduleName, StringUtils.uncapitalize(moduleName)));

            // 获取类中所有字段
            fields = rightInfoClass.getDeclaredFields();
            if (null == fields) {
                return;
            }

            obj = rightInfoClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("获取对象实例失败", e);
            return;
        }

        List<Right> rights = new ArrayList<>();

        for (Field field : fields) {
            String name = field.getName();
            if (name.startsWith(RIGHT_PREFIX)) {
                Object value = field.get(obj);
                if (null != value) {
                    @SuppressWarnings("rawtypes")
                    Pair<String, String> p = (Pair<String, String>) value;
                    Object rightName = p.getValue0();
                    Object displayName = p.getValue1();

                    Right right = new Right(rightName.toString(), 1, displayName.toString(), moduleName);
                    rights.add(right);
                }
            }
        }

        if (rights.size() == 0) {
            return;
        }

        Env env = EnvUtils.systemEnv();

        // 获取旧的权限
        List<Right> oldRights = rightApi.getRights(env, moduleName);
        // 需要删除的权限
        List<Right> removeRights = new LinkedList<>();
        // 需要新增的权限
        List<Right> addRights = new LinkedList<>();

        // 校验旧的权限是否需要删除
        rightApi.checkId(oldRights, rights, removeRights, addRights);

        // 设置新权限
        for (Right right : addRights) {
            rightApi.createRight(env, right);
        }

        // 删除旧权限
        for (Right right : removeRights) {
            rightApi.deleteRight(env, right);
        }

    }

}
