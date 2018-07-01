package com.demon.web.autoconfigure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xubei.framework.boot.web.context.IApplicationContext;
import com.xubei.framework.boot.web.context.RequestContext;
import com.xubei.framework.boot.web.convert.DateConverter;
import com.xubei.framework.common.constants.DateConstants;
import com.xubei.framework.common.entity.ResponseObj;
import com.xubei.framework.util.net.IpUtils;
import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Spring MVC 相关配置
 * @author :<a href="mailto:zhangyingjie@xubei.com">章英杰</a>
 * @date :2017-02-06 14:03:35
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnClass({ HttpServlet.class })
//@ConditionalOnProperty(prefix = "system.mvc", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableWebMvc
public class MvcAutoConfiguration extends WebMvcConfigurerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(MvcAutoConfiguration.class);


    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * SpringMVC 全局日期转换器
     */
    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            genericConversionService.addConverter(new DateConverter());
        }
    }

    /**
     * 统一处理验证失败异常 使用此切片后@Valid注解验证的参数后不用再加Errors或Bindingesult
     */
    @RestControllerAdvice
    class ValidateControllerAdvice {
//		@Autowired
//		private ObjectMapper objectMapper;
        /**
         * bean校验未通过异常
         *
         * @see javax.validation.Valid
         * @see org.springframework.validation.Validator
         * @see org.springframework.validation.DataBinder
         */
//		@ExceptionHandler({MethodArgumentNotValidException.class,BindException.class, BadSqlGrammarException.class})
        @ExceptionHandler({Exception.class})
        public ResponseObj validExceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
            List<FieldError> fieldErrors = Collections.EMPTY_LIST;
            List<ObjectError> objectErrors = Collections.EMPTY_LIST;

            //是否为sql错误异常
            boolean isBadSql=false;
            //异常消息提示
            String msgExecption="arg exception";

            if(e instanceof BindException){
                fieldErrors=((BindException)e).getBindingResult().getFieldErrors();
            } else if(e instanceof  MethodArgumentNotValidException){
                objectErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            }else if(e instanceof  BadSqlGrammarException){
                logger.error("入参导致分表定位异常",e);
                isBadSql=true;
            }else {
                logger.error("系统异常",e);
                msgExecption="system exception";
            }

            if(!isBadSql){
                StringBuilder errs=new StringBuilder("{");
                if(null!=fieldErrors){
                    for (FieldError error:fieldErrors){
                        errs.append(error.getField()+":"+error.getDefaultMessage()).append(",");
                    }
                }
                if(null!=objectErrors){
                    for (ObjectError error:objectErrors) {
                        errs.append(error.getObjectName()+":"+error.getDefaultMessage()).append(",");
                    }
                }

                errs.append("e"+":"+e.getMessage());

                errs.append("}");
                logger.error("客户端IP:{},请求:{} 异常原因：{}", IpUtils.getIpAddr(request),request.getRequestURL(),errs.toString());

            }

//			if(isAjax(request)){
//				try {
//					JsonGenerator jsonGenerator=objectMapper.getFactory().createGenerator(response.getOutputStream());
//					if(jsonGenerator!=null){
//						jsonGenerator.writeObject(ResponseObj.ERROR(msgExecption));
//					}
//				} catch (IOException e1) {
//					logger.error("一般不会发生的validExceptionHandler异常！",e1);
//				}
            return ResponseObj.ERROR(msgExecption);
//			}else {
//				//将验证异常定向到error/valid 页面
//				ModelAndView modelAndView=new ModelAndView();
//				modelAndView.addObject("fieldErrors",fieldErrors);
//				modelAndView.addObject("isBadSql",isBadSql);
//				modelAndView.setViewName("error/valid");
//				return modelAndView;
//			}


        }
    }

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
            }
            else if (converter instanceof StringHttpMessageConverter) {
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
     * request对象线程共享过滤器
     * @return
     */
    @Bean
    public AopFilter aopFilter() {
        return new AopFilter();
    }
    /**
     * 初始化 servlet 环境变量
     * @return
     */
    @Bean
    public ServletContextListener contextListener() {
        return new ServletContextListener() {
            @Autowired(required = false)
            private List<IApplicationContext> applicationContexts = Collections.emptyList();

            @Override
            public void contextInitialized(ServletContextEvent servletContextEvent) {
                applicationContexts.forEach((a) -> a.getAttr()
                        .forEach((s, o) -> servletContextEvent.getServletContext().setAttribute(s, o)));
            }
            @Override
            public void contextDestroyed(ServletContextEvent servletContextEvent) {
            }
        };
    }
    /**
     * 解决 返回JSON 中文乱码
     */
    private MappingJackson2HttpMessageConverter buildJackson2HttpMessageConverter(
            MappingJackson2HttpMessageConverter jsonConverter) {
        // 设置全局日期格式化
        jsonConverter.getObjectMapper().setDateFormat(new SimpleDateFormat(DateConstants.yyyyMMddHHmmssSSS));
//		jsonConverter.getObjectMapper().getSerializationConfig().with(new SimpleDateFormat(DateConstants.yyyyMMddHHmmssSSS));
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
     * 1.用于相同线程间共享Request对象 。 2.强制浏览器ajax在302时可以读取http的Response信息
     */
    class AopFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            try {
                // 向request上下文context中设置 HttpServletRequest请求对象
                RequestContext.setHttpServletRequest((HttpServletRequest) request);
                chain.doFilter(request, response);
            } finally {
                // 清除线程中的Local对象
                RequestContext.clearHttpReq();
            }

            try {
                /**
                 * 强制浏览器ajax在302时可以读取http的Response信息 方法： 针对 ajax 的302请求去除Location的返回头，替换成Location2,
                 * 前端针对ajax complete 做统一处理
                 */
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                if (isAjax((HttpServletRequest) request) && httpResponse.getStatus() == 302) {
//					HttpServletResponseWrapper firewalledResponse = (HttpServletResponseWrapper) response;
//					HttpServletResponseWrapper multiSessionHttpServletResponse = (HttpServletResponseWrapper) firewalledResponse
//							.getResponse();
//					HttpServletResponseWrapper SessionRepositoryResponseWrapper = (HttpServletResponseWrapper) multiSessionHttpServletResponse
//							.getResponse();

                    while (response instanceof HttpServletResponseWrapper){
                        response=((HttpServletResponseWrapper) response).getResponse();
                    }

                    ResponseFacade responseFacade = (ResponseFacade)response;

                    Field responseField = ReflectionUtils.findField(ResponseFacade.class, "response");
                    responseField.setAccessible(true);
                    Response responseOpeater = (Response) ReflectionUtils.getField(responseField, responseFacade);
                    String locationHead="Location";
                    String location = ((HttpServletResponse) response).getHeader(locationHead);
                    responseOpeater.getCoyoteResponse().getMimeHeaders().removeHeader(locationHead);
                    responseOpeater.getCoyoteResponse().addHeader(locationHead + "2", location);
                }
            } catch (Exception e) {
                logger.error("不会发生的Filter异常！", e);
            }
        }
        @Override
        public void destroy() {
        }
    }

    /**
     * 是否为Ajax请求
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}

