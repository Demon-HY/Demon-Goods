package org.demon.starter.autoconfigure.http;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * HTTP 封装类
 */
@Configuration
@ConditionalOnClass(value = {RestTemplate.class, HttpClient.class})
public class RestTemplateConfiguration {

    @Value("${remote.maxTotalConnect:200}")//连接池的最大连接数默认值
    private int maxTotalConnect;
    @Value("${remote.maxConnectPerRoute:100}")//单个主机的最大连接数
    private int maxConnectPerRoute;
    @Value("${remote.connectTimeout:2000}")//连接超时默认2s
    private int connectTimeout;
    @Value("${remote.readTimeout:3000}")//读取超时默认3s
    private int readTimeout;

    //创建HTTP客户端工厂
    private ClientHttpRequestFactory createFactory() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(maxTotalConnect);
        connManager.setDefaultMaxPerRoute(maxConnectPerRoute);

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.createMinimal(connManager));
        clientHttpRequestFactory.setConnectTimeout(connectTimeout);
        clientHttpRequestFactory.setConnectionRequestTimeout(connectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        return clientHttpRequestFactory;
    }

    //

    /**
     * 初始化RestTemplate,并加入spring的Bean工厂，由spring统一管理
     *
     * @return
     * @Qualifier("thirdHttpRestTemplate")
     */
    @Bean("thirdHttpRestTemplate")
    public RestBaseTemplate getRestTemplate() {
        RestBaseTemplate restBaseTemplate = new RestBaseTemplate(this.createFactory());
        List<HttpMessageConverter<?>> converterList = restBaseTemplate.getMessageConverters();
//        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
//        HttpMessageConverter<?> converterTarget = null;
//        for (HttpMessageConverter<?> item : converterList) {
//            if (StringHttpMessageConverter.class == item.getClass()) {
//                converterTarget = item;
//                break;
//            }
//        }
//        if (null != converterTarget) {
//            converterList.remove(converterTarget);
//        }
//        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //加入FastJson转换器
        converterList.add(new FastJsonHttpMessageConverter());
        return restBaseTemplate;
    }
}
