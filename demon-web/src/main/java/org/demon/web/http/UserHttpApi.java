package org.demon.web.http;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.demon.module.auth.AuthConfig;
import org.demon.module.user.UserBaseApi;
import org.demon.module.user.UserConfig;
import org.demon.sdk.entity.User;
import org.demon.sdk.entity.request.UserCreateVo;
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

@Api(description = "用户接口")
@RestController
@RequestMapping("/api/" + UserConfig.MODULE_NAME)
public class UserHttpApi {

    @Autowired
    private UserBaseApi userBaseApi;

    /**
     * 创建用户
     *
     * @param env
     * @param userCreateVo
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "创建用户", httpMethod = "POST")
    @RequestMapping(value = AuthConfig.ANNO_PATH + "createUser.do", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientResult createUser(@ApiIgnore @RequestEnv Env env,
                                   @RequestBody UserCreateVo userCreateVo) throws Exception {
        if (ValidUtils.isBlanks(userCreateVo.name, userCreateVo.password)) {
            return ClientResult.error(RetCodeEnum.ERR_BAD_PARAMS);
        }

        User user = userBaseApi.createUser(env, userCreateVo);

        return ClientResult.success(user);
    }


}
