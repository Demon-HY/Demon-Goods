package com.demon.web.http;

import com.demon.utils.ValidUtils;
import com.demon.web.utils.ClientResult;
import com.demon.web.utils.RetCodeEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloHttpApi {

    @RequestMapping("/hello")
    public ClientResult hello(String flag) {
        if (ValidUtils.isBlank(flag)) {
            return ClientResult.error(RetCodeEnum.ERR_BAD_PARAMS);
        }

        return ClientResult.success(flag);
    }

    /**
     * 测试 Redis 实现 Session 共享
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSessionId")
    public String getSessionId(HttpServletRequest request) {

        Object o = request.getSession().getAttribute("springboot");
        if (o == null) {
            o = "spring boot 牛逼了!!!有端口" + request.getLocalPort() + "生成";
            request.getSession().setAttribute("springboot", o);
        }

        return "端口=" + request.getLocalPort() + " sessionId=" + request.getSession().getId() + "<br/>" + o;

    }
}
