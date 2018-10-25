package org.demon.starter.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.demon.starter.web.context.RequestContext;
import org.demon.starter.web.converter.DateConvert;
import org.demon.starter.common.constants.SysContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC 相关配置
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnClass({HttpServlet.class})
@EnableWebMvc
public class MvcAutoConfiguration extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DateConvert customDateFormat;

    /**
     * 解决中文乱码及 json 数字long 类型 前端超过15位的 问题
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        boolean hasJsonConverter = false;
        boolean hasStringConverter = false;
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                buildJackson2HttpMessageConverter((MappingJackson2HttpMessageConverter) converter);
                hasJsonConverter = true;
            } else if (converter instanceof StringHttpMessageConverter) {
                buildStringHttpMessageConverter((StringHttpMessageConverter) converter);
                hasStringConverter = true;
            }
        }
        if (!hasJsonConverter) {
            converters.add(buildJackson2HttpMessageConverter(new MappingJackson2HttpMessageConverter()));
        }
        if (!hasStringConverter) {
            converters.add(buildStringHttpMessageConverter(new StringHttpMessageConverter()));
        }
    }

    /**
     * 解决 返回JSON 中文乱码
     */
    private MappingJackson2HttpMessageConverter buildJackson2HttpMessageConverter(
            MappingJackson2HttpMessageConverter jsonConverter) {
        // 设置全局日期格式化
//		jsonConverter.getObjectMapper().setDateFormat(new SimpleDateFormat(DateConstants.yyyyMMddHHmmssSSS));
        jsonConverter.getObjectMapper().setDateFormat(customDateFormat);
//		jsonConverter.getObjectMapper().getDeserializationConfig().with(customDateFormat);
//		jsonConverter.getObjectMapper().getSerializationConfig().with(new SimpleDateFormat(DateConstants.yyyyMMddHHmmssSSS_D));
        jsonConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 数字也加引号
//		jsonConverter.getObjectMapper().configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(new MediaType("application", "text", Charset.forName("UTF-8")));

        mediaTypes.addAll(jsonConverter.getSupportedMediaTypes());
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        return jsonConverter;
    }

    /**
     * 解决 返回String 中文乱码
     */
    private StringHttpMessageConverter buildStringHttpMessageConverter(StringHttpMessageConverter stringConverter) {
        stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType("text", "html", Charset.forName("UTF-8")));

        mediaTypes.addAll(stringConverter.getSupportedMediaTypes());
        stringConverter.setSupportedMediaTypes(mediaTypes);
        return stringConverter;
    }

    /**
     * 设置 log日志 requestid 参数过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean logRequestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogRequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("Log-Request-Filter");
        //设置过滤器 优先级最高
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    /**
     * 设置 log日志 requestid 参数
     */
    class LogRequestFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            try {
                HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
                MDC.put(SysContants.REQUEST_ID, RequestContext.getRequestId());

                chain.doFilter(httpServletRequest, response);
            } finally {
                // 清除线程中的Local对象
                MDC.remove(RequestContext.getRequestId());
            }
        }

        @Override
        public void destroy() {
        }
    }
}
