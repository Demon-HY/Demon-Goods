package org.demon.starter.web;

import org.demon.starter.web.context.SpringContext;
import org.demon.starter.web.converter.DateConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationPid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;

/**
 * APP 相关配置
 */
@Configuration
public class AppAutoConfiguration {

    private Logger logger= LoggerFactory.getLogger(AppAutoConfiguration.class);

    @Value("${server.tomcat.max-connections:${server.tomcat.maxConnections:800}}")
    private int tomcatMaxConnections=800;
    @Value("${server.tomcat.max-threads:${server.tomcat.maxThreads:500}}")
    private int tomcatMaxThreads=500;
    @Value("${server.tomcat.accept-count:${server.tomcat.acceptCount:200}}")
    private int tomcatAcceptCount=200;

    static {
        //兼容在header里面有json时报 The valid characters are defined in RFC 7230 and RFC 3986
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","|{}");
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
    }

    /**
     * 首页跳转路径,默认重定向到首页
     */
    @Value("${server.path.index:redirect:http://www.demon.com}")
    private String indexPath;

    /**
     * 默认开启 web 访问的日志
     * 增大tomcat的并发数
     */
    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
    public ServerProperties serverProperties() {
        ServerProperties serverProperties = new ServerProperties();

        ServerProperties.Tomcat tomcat = serverProperties.getTomcat();

        //设置tomcat的参数
        tomcat.setMaxConnections(tomcatMaxConnections);
        tomcat.setMaxThreads(tomcatMaxThreads);
        tomcat.setAcceptCount(tomcatAcceptCount);

        //默认开启 web 访问的日志
        tomcat.setBasedir(new File("."+File.separator));

        ServerProperties.Tomcat.Accesslog accesslog = tomcat.getAccesslog();
        accesslog.setEnabled(true);
        accesslog.setRenameOnRotate(true);
        accesslog.setPattern("%t [%I] %h %{X-Forwarded-For}i \"%r\" %S %s %b %D");
        accesslog.setDirectory("logs");

        return serverProperties;
    }

    /**
     * 输入主程的PID
     */
    @Bean
    public ApplicationPid applicationPid(){
        ApplicationPid applicationPid=new ApplicationPid();
        try {
            applicationPid.write(new File("pid"));
        } catch (IOException e) {
            logger.warn("pid文件输出失败", e);
        }
        return applicationPid;
    }

    /**
     * 首页
     */
    @Controller
    @RequestMapping(value = "")
    public class index {
        @RequestMapping(value = "", method = RequestMethod.GET)
        public String index() {
            return indexPath;
        }
    }

    /**
     * 自定义 数据转换器  日期
     * @return
     */
    @Bean
    public DateConvert customDateFormat(){
        return new DateConvert();
    }

    @Bean
    public SpringContext springContext(){
        return new SpringContext();
    }
}
