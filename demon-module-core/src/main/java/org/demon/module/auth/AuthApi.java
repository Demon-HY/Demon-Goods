package org.demon.module.auth;

import org.demon.module.user.UserQueryApi;
import org.demon.module.user.UserDaoImpl;
import org.demon.sdk.model.entity.Token;
import org.demon.sdk.model.entity.User;
import org.demon.sdk.model.dto.request.UserLoginVo;
import org.demon.sdk.model.vo.LoginVo;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.type.PostLoginEvent;
import org.demon.sdk.event.type.PostLogoutEvent;
import org.demon.sdk.event.type.PreLoginEvent;
import org.demon.sdk.event.type.PreLogoutEvent;
import org.demon.starter.exception.LogicalException;
import org.demon.sdk.inner.user.IAuthApi;
import org.demon.starter.utils.RetCodeEnum;
import org.demon.starter.common.logger.AbstractLogClass;
import org.demon.utils.datetime.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class AuthApi extends AbstractLogClass implements IAuthApi {

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
    public LoginVo login(Env env, UserLoginVo userLoginVo) throws Exception {
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

        LoginVo loginVo = new LoginVo(token, user);

        // save loginVo to redis
        authRedisApi.saveLoginInfo(loginVo);

        // 发送登录后事件
        PostLoginEvent postLoginEvent = new PostLoginEvent(this, env, loginVo);
        applicationContext.publishEvent(postLoginEvent);
        if (!preLoginEvent.isContinue) {
            logger.warn("{} 事件被 {} 拦截, 拦截原因: {}",
                    PostLoginEvent.EVENT_TYPE, postLoginEvent.lastHandler.getName(), postLoginEvent.breakReason);
            throw new LogicalException(preLoginEvent.retCodeEnum);
        }

        return loginVo;
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
        tokenDao.removeById(env.loginVo.token.id, Token.class);
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
    public LoginVo checkLogin(Env env) throws Exception {
        String token = env.token;
        if (null == token) {
            throw new IllegalArgumentException();
        }

        LoginVo loginVo = env.loginVo;
        if (loginVo == null) {
            // 从数据库中获取 token
            Token tokenInfo = tokenDao.getToken(token);
            if (tokenInfo == null) {
                throw new LogicalException(RetCodeEnum.ERR_TOKEN_NOT_FOUND);
            }
            if (tokenInfo.expires.compareTo(DateUtils.getCurrentTimeDate()) <= 0) {
                throw new LogicalException(RetCodeEnum.ERR_TOKEN_EXPIRED);
            }
            User user = userDao.selectById(tokenInfo.uid, User.class);
            loginVo = new LoginVo(tokenInfo, user);

            // 保存登录信息到缓存
        }

        long uid = loginVo.user.uid;
        String tokenUid = loginVo.token.token;
        // 检查当前 token 和 用户的ID是否匹配
        if (tokenUid.length() > 11) {
            String[] splitToken = tokenUid.split("@");
            if (splitToken.length == 2) {
                if (!splitToken[1].endsWith(uid + "")) {
                    throw new LogicalException(RetCodeEnum.ERR_TOKEN_UID_MISMATCHING);
                }
            }
        }

        // check user status
        userQueryApi.checkUserStatus(env, loginVo.user);

        return loginVo;
    }
}
