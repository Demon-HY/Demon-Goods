package org.demon.web.autoconfigure.http;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RestTemplate 封装类,方便操作
 */
public class RestBaseTemplate extends RestTemplate {

    public RestBaseTemplate(ClientHttpRequestFactory factory) {
        super(factory);
    }

    private <T> T restTemplate(String url, Map<String, Object> params, Class<T> var, HttpMethod method) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        if (params != null) {
            map.setAll(params);
        }
        switch (method) {
            case POST:
                return this.postForObject(url, map, var);
            case GET:
                String getParams;
                if (params == null) {
                    getParams = url;
                } else {
                    getParams = "?" + map.keySet().stream().map(k -> String.format("%s={%s}", k, k))
                            .collect(Collectors.joining("&"));
                }
                return this.getForObject(url + getParams, var, params);
            default:
                return this.postForObject(url, map, var);
        }
    }

    public String get(String url) {
        return get(url, new HashMap<>(1));
    }

    public String get(String url, Map<String, Object> params) {
        return restTemplate(url, params, String.class, HttpMethod.GET);
    }

    public String post(String url, Map<String, Object> params) {
        return restTemplate(url, params, String.class, HttpMethod.POST);
    }

    public <T> T post(String url, Map<String, Object> params, Class<T> var) {
        return restTemplate(url, params, var, HttpMethod.POST);
    }

    static class Test {
        public static void main(String[] args) {
            RestBaseTemplate restBaseTemplate = new RestBaseTemplate(createFactory());
            String result = restBaseTemplate.get("http://order-server.xubei.com/hello");
            System.out.println(result);

            Map<String, Object> param = new HashMap<>(4);
            param.put("orderNo", "12nyk6v1u02rk");
            param.put("sign", "6BDF7DDFFE934C7AA17D8B844663DAED");
            result = restBaseTemplate.get("http://order-server.xubei.com/order/user/findOrderItemByOrderNo", param);
            System.out.println(result);
        }

        //创建HTTP客户端工厂
        private static ClientHttpRequestFactory createFactory() {
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
            connManager.setMaxTotal(30);
            connManager.setDefaultMaxPerRoute(10);

            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                    HttpClients.createMinimal(connManager));
            clientHttpRequestFactory.setConnectTimeout(2000);
            clientHttpRequestFactory.setConnectionRequestTimeout(2000);
            clientHttpRequestFactory.setReadTimeout(10000);
            return clientHttpRequestFactory;
        }
    }
}
