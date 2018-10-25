package org.demon.starter.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Swagger 相关配置
 */
@Configuration
@EnableSwagger2
@ConditionalOnClass(EnableSwagger2.class)
public class SwaggerConfig extends WebMvcConfigurerAdapter {
    /**
     * 从哪个包开始扫描
     */
    private final String SWAGGER_SCAN_BASE_PACKAGE = "org.demon";
    /**
     * API版本
     */
    @Value("${swagger.version:1.0-SNAPSHOT}")
    private String version;
    @Value("${swagger.title:Rest API 接口}")
    private String title;
    @Value("${swagger.description:Rest API 接口}")
    private String description;

    /**
     * 构建ApiInfo对象
     *
     * @return the api info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demon RESTful APIs")
                .description("Demon")
                .termsOfServiceUrl("")
                .contact(new Contact("Demon-HY", "", "heyan_kafeibuku@sina.com"))
                .version(version)
                .build();
    }

    /**
     * @return the docket
     */
    @Bean
    @ConditionalOnMissingBean
    public Docket customImplementation() {
//        Predicate<RequestHandler> predicate = input -> {
//            // 除非是在开发环境中否则不开启swagger2
//            String active = env.getProperty("spring.profiles.active");
//            if (!active.equalsIgnoreCase("dev")) {
//                return false;
//            }
//
//            Class<?> declaringClass = input.declaringClass();
//            if (declaringClass == BasicErrorController.class)// 排除
//                return false;
//            if (declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
//                return true;
//            if (input.isAnnotatedWith(ResponseBody.class)) // 被注解的方法
//                return true;
//            return false;
//        };

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
//                .apis(predicate)
                .build()
                .directModelSubstitute(LocalDate.class, java.util.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
    }


    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}
