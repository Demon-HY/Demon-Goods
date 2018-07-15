package org.demon.module.auth;

import org.demon.sdk.entity.request.UserLoginVo;
import org.demon.sdk.entity.vo.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.utils.ClientResult;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.starter.autoconfigure.annotion.RequestEnv;
import org.demon.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/" + AuthConfig.MODULE_NAME)
public class AuthHttpApi {

    @Autowired
    private AuthApi authApi;

    @RequestMapping(AuthConfig.ANNO_PATH + "login.do")
    public ClientResult login(@RequestEnv Env env, UserLoginVo userLoginVo) throws Exception {
        if (ValidUtils.isBlanks(userLoginVo.account, userLoginVo.password, userLoginVo.type)) {
            return ClientResult.error(RetCodeEnum.ERR_BAD_PARAMS);
        }

        Login login = authApi.login(env, userLoginVo);

        return ClientResult.success(login);
    }
}
