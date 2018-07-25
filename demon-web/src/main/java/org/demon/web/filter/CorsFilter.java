package org.demon.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.annotation.WebFilter;
import java.net.MalformedURLException;

/**
 * 设置允许跨域请求
 */
@WebFilter(value = "/*",asyncSupported=true)
public class CorsFilter extends org.springframework.web.filter.CorsFilter {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private static UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    static {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
    }

    public CorsFilter(){
        super(source);
        logger.info("初始化CORS 过滤器完成。。。");
    }

}
