package org.demon.module.auth;

import org.demon.module.user.UserQueryApi;
import org.demon.module.user.UserDaoImpl;
import org.demon.sdk.entity.Token;
import org.demon.sdk.entity.User;
import org.demon.sdk.entity.request.UserLoginVo;
import org.demon.sdk.entity.vo.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.type.PostLoginEvent;
import org.demon.sdk.event.type.PostLogoutEvent;
import org.demon.sdk.event.type.PreLoginEvent;
import org.demon.sdk.event.type.PreLogoutEvent;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.inner.user.IAuthApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.datetime.DateUtils;
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
    private UserQueryApi userQueryApi;
    @Autowired
    private AuthRedisApi authRedisApi;

    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private TokenDaoImpl tokenDao;

    @Override
    public Login login(Env env, UserLoginVo userLoginVo) throws Exception {
        // 发送登录前事件
        PreLoginEvent preLoginEvent = new PreLoginEvent(this, env, userLoginVo);
        applicationContext.publishEvent(preLoginEvent);
        if (!preLoginEvent.isContinue) {
            logger.warn("{} 事件被 {} 拦截, 拦截原因: {}",
                    PreLoginEvent.EVENT_TYPE, preLoginEvent.lastHandler.getName(), preLoginEvent.breakReason);
            throw new LogicalException(preLoginEvent.retCodeEnum);
        }

        User user = userDao.findByAccount(userLoginVo.account);
        if (user == null) {
            throw new LogicalException(RetCodeEnum.ERR_NO_SUCH_ACCOUNT);
        }
        // check password
        if (!AuthUtils.checkPassword(user, userLoginVo.password)) {
            // TODO 校验密码失败,后期可以锁定用户
            throw new LogicalException(RetCodeEnum.ERR_INVALID_PASSWORD);
        }
        // check user status
        userQueryApi.checkUserStatus(env, user);

        String userAgent = env.request.getHeader("User-Agent");

        if (userLoginVo.tokenAge == null) {
            userLoginVo.tokenAge = AuthConfig.DEFAULT_TOKEN_AGE;
        }

        Token token = Token.newToken(user.uid, userLoginVo.tokenAge, env.clientIP, env.device, userAgent);
        // save token
        tokenDao.insert(token);

        Login login = new Login(token, user);

        // save login to redis
        authRedisApi.saveLoginInfo(login);

        // 发送登录后事件
        PostLoginEvent postLoginEvent = new PostLoginEvent(this, env, login);
        applicationContext.publishEvent(postLoginEvent);
        if (!preLoginEvent.isContinue) {
            logger.warn("{} 事件被 {} 拦截, 拦截原因: {}",
                    PostLoginEvent.EVENT_TYPE, postLoginEvent.lastHandler.getName(), postLoginEvent.breakReason);
            throw new LogicalException(preLoginEvent.retCodeEnum);
        }

        return login;
    }

    @Override
    public boolean logout(Env env) throws LogicalException {
        // 发送退出登录前事件
        PreLogoutEvent preLogoutEvent = new PreLogoutEvent(this, env);
        applicationContext.publishEvent(preLogoutEvent);
        if (!preLogoutEvent.isContinue) {
            logger.warn("{} 事件被 {} 拦截, 拦截原因: {}",
                    PreLogoutEvent.EVENT_TYPE, preLogoutEvent.lastHandler.getName(), preLogoutEvent.breakReason);
            throw new LogicalException(preLogoutEvent.retCodeEnum);
        }

        // remove token
        tokenDao.removeById(env.login.token.id, Token.class);
        authRedisApi.clearLoginInfo(env.token);


        // 发送退出登录后事件
        PostLogoutEvent postLogoutEvent = new PostLogoutEvent(this, env);
        applicationContext.publishEvent(postLogoutEvent);
        if (!postLogoutEvent.isContinue) {
            logger.warn("{} 事件被 {} 拦截, 拦截原因: {}",
                    PreLoginEvent.EVENT_TYPE, postLogoutEvent.lastHandler.getName(), postLogoutEvent.breakReason);
            throw new LogicalException(postLogoutEvent.retCodeEnum);
        }

        return true;
    }

    @Override
    public Login checkLogin(Env env) throws LogicalException {
        String token = env.token;
        if (null == token) {
            throw new IllegalArgumentException();
        }

        Login login = env.login;
        if (login == null) {
            // 从数据库中获取 token
            Token tokenInfo = tokenDao.getToken(token);
            if (tokenInfo == null) {
                throw new LogicalException(RetCodeEnum.ERR_TOKEN_NOT_FOUND);
            }
            if (tokenInfo.expires.compareTo(DateUtils.getCurrentTimeDate()) <= 0) {
                throw new LogicalException(RetCodeEnum.ERR_TOKEN_EXPIRED);
            }
            User user = userDao.selectById(tokenInfo.uid, User.class);
            login = new Login(tokenInfo, user);

            // 保存登录信息到缓存
        }

        long uid = login.user.uid;
        String token_uid = login.token.token;
        // 检查当前 token 和 用户的ID是否匹配
        if (token_uid.length() > 11) {
            String[] splitToken = token_uid.split("@");
            if (splitToken.length == 2) {
                if (!splitToken[1].endsWith(uid + "")) {
                    throw new LogicalException(RetCodeEnum.ERR_TOKEN_UID_MISMATCHING);
                }
            }
        }

        // check user status
        userQueryApi.checkUserStatus(env, login.user);

        return login;
    }
}
