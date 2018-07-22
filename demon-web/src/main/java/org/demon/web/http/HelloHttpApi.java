package org.demon.web.http;

import io.swagger.annotations.ApiOperation;
import org.demon.sdk.environment.Env;
import org.demon.sdk.utils.ClientResult;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.starter.autoconfigure.annotion.RequestEnv;
import org.demon.utils.ValidUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloHttpApi {

    @ApiOperation(value = "测试hello", httpMethod = "GET")
    @RequestMapping("/hello")
    public ClientResult hello(@RequestEnv Env env, String flag) {
        if (ValidUtils.isBlank(flag)) {
            return ClientResult.error(RetCodeEnum.ERR_BAD_PARAMS);
        }

        return ClientResult.success(flag);
    }

    @ApiOperation(value = "测试 Redis 实现 Session 共享", httpMethod = "GET")
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
