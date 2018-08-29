package org.demon.starter.autoconfigure.web;

import org.demon.starter.autoconfigure.annotion.RequestEnv;
import org.demon.starter.common.logger.AbstractLogClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 请求参数拦截器,为了在请求中加入默认的 Evn 对象
 */
public class RequestJsonHandlerMethodArgumentResolver extends AbstractLogClass implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestEnv.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        logger.debug("请求参数拦截器 开始解析请求......");

        Object env = nativeWebRequest.getAttribute("env", RequestAttributes.SCOPE_REQUEST);

        logger.debug("请求参数拦截器 结束解析请求......");
        return env;
    }
}
