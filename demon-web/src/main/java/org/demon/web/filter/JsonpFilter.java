package org.demon.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 全局统一jsonp 过虑器
 * 在GET方式的 url请求 发现 callback 参数 就判定为jsonp的请求，自动包装成jsonp的返回值
 */
@WebFilter(value = "/*")
public class JsonpFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * JSONP 的 参数名
     */
    @Value("${jsonp.callback.parameter:callback}")
    private String callBackParameter;

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("初始化JSONP 过滤器完成。。。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String callbackParameter = request.getParameter(callBackParameter);
        //检测是否为JSONP的请求
        if (StringUtils.isNotBlank(callbackParameter) && "get".equalsIgnoreCase(httpServletRequest.getMethod())) {
            //内存字节流 用于 接收Response中的原数据
            try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                /**
                 * 内部类HttpServletResponseWrapper 包装HttpServletResponse
                 */
                HttpServletResponseWrapper httpServletResponseWrapper = new HttpServletResponseWrapper(httpServletResponse) {
                    private ServletOutputStream servletOutputStream = null;
                    private PrintWriter printWriter = null;

                    /**
                     * 伪装ServletOutputStream
                     */
                    @Override
                    public ServletOutputStream getOutputStream() {
                        if (null == servletOutputStream) {
                            servletOutputStream = new MyOutputStream(byteArrayOutputStream);
                        }
                        return servletOutputStream;
                    }

                    /**
                     * 获取真实OutputStream
                     */
                    @Override
                    public PrintWriter getWriter() throws IOException {
                        if (null == printWriter) {
                            printWriter = super.getWriter();
                        }
                        return printWriter;
                    }
                };


                chain.doFilter(request, httpServletResponseWrapper);

                String orginBody = byteArrayOutputStream.toString();
                PrintWriter writer = httpServletResponseWrapper.getWriter();

                String newBody = callbackParameter + "(" + orginBody + ")";

                writer.write(newBody);
                if (logger.isDebugEnabled()) {
                    String requestUrl = httpServletRequest.getRequestURI() + (StringUtils.isEmpty(httpServletRequest.getQueryString()) ? "" : ("?" + httpServletRequest.getQueryString()));
                    logger.debug("请求:{}原始数据为:{}", requestUrl, orginBody);
                    logger.debug("请求:{}返回数据为:{}", requestUrl, newBody);
                }
                writer.flush();
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}

/**
 * 自定义ServletOutputStream重写wirte方法 用于伪装ServletOutputStream 接收respone原数据
 */
class MyOutputStream extends ServletOutputStream {
    /**
     * 用于接收原数据对象
     */
    private OutputStream outputStream;

    public MyOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
    }

}

