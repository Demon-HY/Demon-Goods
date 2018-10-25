package org.demon.starter.web.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * APP 上下文件信息
 *
 * @author :<a href="mailto:zhangyingjie@xubei.com">章英杰</a>
 * @date :2017-02-06 14:03:44
 */
public class SpringContext implements ApplicationContextAware{
    private static ApplicationContext applicationContext;

    /**
     * 获取Spring 注入的bean
     * @param clasz
     * @return
     */
    public static <T> T getBean(Class<T> clasz){
        return applicationContext.getBean(clasz);
    }
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
    public static <T> T getBean(String beanName,Class<T> clasz){
        return applicationContext.getBean(beanName,clasz);
    }
    /**
     * 按类型获取Spring注入的 所有 实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz){

        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 获取Spring 容器上下文对象
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext=appContext;
    }
}
