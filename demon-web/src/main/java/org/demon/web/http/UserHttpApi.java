package org.demon.web.http;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.demon.module.user.UserBaseApi;
import org.demon.module.user.UserConfigAbstract;
import org.demon.sdk.model.entity.User;
import org.demon.sdk.model.dto.create.UserCreateDto;
import org.demon.sdk.environment.Env;
import org.demon.sdk.retCode.BizRetCode;
import org.demon.starter.common.entity.ClientResult;
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
@RequestMapping(ApiURL.API_PREFIX + UserConfigAbstract.MODULE_NAME)
public class UserHttpApi {

    @Autowired
    private UserBaseApi userBaseApi;

    @ApiOperation(value = "创建用户", httpMethod = "POST")
    @RequestMapping(value = "createUser.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientResult createUser(@ApiIgnore @RequestEnv Env env,
                                   @RequestBody UserCreateDto userCreateDto) throws Exception {
        if (ValidUtils.isBlanks(userCreateDto.name, userCreateDto.password)) {
            return ClientResult.error(BizRetCode.ERR_BAD_PARAMS);
        }

        User user = userBaseApi.createUser(env, userCreateDto);

        return ClientResult.success(user);
    }


}
