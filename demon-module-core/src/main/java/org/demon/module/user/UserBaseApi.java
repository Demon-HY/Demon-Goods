package org.demon.module.user;

import org.demon.sdk.entity.User;
import org.demon.sdk.entity.request.UserLoginVo;
import org.demon.sdk.environment.Env;
import org.demon.sdk.inner.IUserBaseApi;
import org.springframework.stereotype.Service;

@Service
public class UserBaseApi implements IUserBaseApi {

    @Override
    public User createUser(Env env, UserLoginVo userLoginVo) {
        return null;
    }

    @Override
    public User clearPassword(User user) {
        user.password = "********";
        return user;
    }
}
