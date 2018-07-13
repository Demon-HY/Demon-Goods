package org.demon.module.auth;

import org.demon.module.user.dao.UserDaoImpl;
import org.demon.sdk.entity.user.User;
import org.demon.sdk.entity.user.vo.UserLoginVo;
import org.demon.sdk.entity.user.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.type.PreLoginEvent;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.inner.user.IAuthApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class AuthApi implements IAuthApi {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserDaoImpl userDao;

    @Override
    public Login login(Env env, UserLoginVo userLoginVo) throws LogicalException {
        if (userLoginVo.tokenAge == null) {
            userLoginVo.tokenAge = AuthConfig.defaultTokenAge;
        }

        Login login = null;

        // 发送登录前事件
        PreLoginEvent preLoginEvent = new PreLoginEvent(this, env, userLoginVo);
        applicationContext.publishEvent(preLoginEvent);
        if (!preLoginEvent.isContinue) {
            logger.warn("{} 事件被拦截, 拦截原因: {}", PreLoginEvent.EVENT_TYPE, preLoginEvent.breakReason);
            throw new LogicalException(preLoginEvent.retCodeEnum);
        }

        User user = userDao.findByAccount(userLoginVo.account);
        if (user == null) {
            throw new LogicalException(RetCodeEnum.ERR_NO_SUCH_ACCOUNT);
        }
        // check user status



        // 发送登录后事件

        return null;
    }

    @Override
    public boolean logout(Env env, String token) {
        return false;
    }

    @Override
    public Login checkLogin(Env env, String token) {
        return null;
    }
}
