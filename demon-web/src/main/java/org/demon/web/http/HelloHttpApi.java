package org.demon.web.http;

import org.demon.sdk.event.type.test.TestEvent;
import org.demon.sdk.utils.ClientResult;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.ValidUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloHttpApi {

    @Autowired
    private ApplicationContext applicationContext;

    @ApiOperation(value = "测试hello", httpMethod = "GET")
    @RequestMapping("/hello")
    public ClientResult hello(String flag) {
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

    @ApiOperation(value = "测试同步事件", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag", value = "标记:测试事件通过和事件拦截:1-事件通过,2-事件拦截", paramType = "query")
    })
    @RequestMapping("/event")
    public ClientResult testEvent(Integer flag) {
        if (ValidUtils.isBlank(flag)) {
            return ClientResult.error(RetCodeEnum.ERR_BAD_PARAMS);
        }

        TestEvent testEvent = new TestEvent(this, null, flag);
        applicationContext.publishEvent(testEvent);
        if (!testEvent.isContinue) {
            return ClientResult.errorMsg(testEvent.breakReason);
        }

        return ClientResult.success(null);
    }
}
