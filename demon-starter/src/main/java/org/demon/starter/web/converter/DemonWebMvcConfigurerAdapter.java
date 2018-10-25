//package org.demon.starter.web.converter;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.util.List;
//
//@Configuration
//public class DemonWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new RequestJsonHandlerMethodArgumentResolver());
//        super.addArgumentResolvers(argumentResolvers);
//    }
//
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**").allowedOrigins("*")
////                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
////                .allowCredentials(false).maxAge(3600);
////    }
//}
