package org.demon.web.http;

import io.swagger.annotations.ApiOperation;
import org.demon.module.auth.AuthApi;
import org.demon.module.auth.AuthConfig;
import org.demon.sdk.entity.request.UserLoginVo;
import org.demon.sdk.entity.vo.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.utils.ClientResult;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.starter.autoconfigure.annotion.RequestEnv;
import org.demon.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录鉴权接口
 */
@RestController
@RequestMapping(ApiURL.API_PREFIX + AuthConfig.MODULE_NAME)
public class AuthHttpApi {

    @Autowired
    private AuthApi authApi;

    @ApiOperation(value = "登录", httpMethod = "POST")
    @RequestMapping(value = ApiURL.ANNO_PATH + "login.do", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientResult<Login> login(@ApiIgnore @RequestEnv Env env, @RequestBody UserLoginVo userLoginVo) throws Exception {
        if (ValidUtils.isBlanks(userLoginVo.account, userLoginVo.password, userLoginVo.type)) {
            return ClientResult.error(RetCodeEnum.ERR_BAD_PARAMS);
        }

        Login login = authApi.login(env, userLoginVo);

        return ClientResult.success(login);
    }

    @ApiOperation(value = "验证登录", httpMethod = "GET")
    @RequestMapping(value = ApiURL.ANNO_PATH + "checkLogin.do")
    public ClientResult<Login> checkLogin(@ApiIgnore @RequestEnv Env env) throws Exception {

        Login login = authApi.checkLogin(env);

        return ClientResult.success(login);
    }

    @ApiOperation(value = "退出登录", httpMethod = "GET")
    @RequestMapping(value = "logout.do")
    public ClientResult logout(@ApiIgnore @RequestEnv Env env) throws Exception {

        authApi.logout(env);

        return ClientResult.success();
    }

}
