package org.demon.module.user.api;

import org.demon.module.user.dao.UserDaoImpl;
import org.demon.sdk.entity.user.User;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.ParamException;
import org.demon.sdk.inner.user.IUserQueryApi;
import org.demon.sdk.retcode.RetStat;
import org.demon.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryApi implements IUserQueryApi {

    @Autowired
    private UserDaoImpl userDao;

    @Override
    public User getUser(Env env, Long userId) throws ParamException {
        if (ValidUtils.isBlank(userId)) {
            throw new ParamException(RetStat.getMsgByStat(RetStat.ERR_BAD_PARAMS, "userId"));
        }

        return userDao.selectById(userId, User.class);
    }

    @Override
    public User getUserByName(Env env, String account) throws ParamException {
        if (ValidUtils.isBlank(account)) {
            throw new ParamException(RetStat.getMsgByStat(RetStat.ERR_BAD_PARAMS, "account"));
        }
        return userDao.findByAccount(account);
    }

    @Override
    public void checkUserStatus(Env env, User user) {

    }
}
