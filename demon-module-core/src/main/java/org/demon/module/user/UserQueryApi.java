package org.demon.module.user;

import org.demon.sdk.entity.User;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.exception.ParamException;
import org.demon.sdk.inner.IUserQueryApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQueryApi implements IUserQueryApi {

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private UserBaseApi userBaseApi;

    @Override
    public User getUser(Env env, Long userId) throws ParamException {
        if (ValidUtils.isBlank(userId)) {
            throw new ParamException();
        }

        return userBaseApi.clearPassword(userDao.selectById(userId, User.class));
    }

    @Override
    public User getUserByName(Env env, String account) throws ParamException {
        if (ValidUtils.isBlank(account)) {
            throw new ParamException();
        }
        return userBaseApi.clearPassword(userDao.findByAccount(account));
    }

    @Override
    public void checkUserStatus(Env env, User user) throws LogicalException {
        switch (user.status) {
            case UserConfig.STATUS_NORMAL :
                break;
            case UserConfig.STATUS_LOCK :
                throw new LogicalException(RetCodeEnum.ERR_USER_LOCKED);
            case UserConfig.STATUS_DELETE :
                throw new LogicalException(RetCodeEnum.ERR_USER_DELETE);
        }

        // TODO 检查用户所在角色是否被锁定

    }

    @Override
    public boolean checkPasswordIsLegal(String password) throws Exception {
        if (ValidUtils.isBlank(password) || password.matches("[u4e00-u9fa5]"))
            throw new LogicalException(RetCodeEnum.ERR_ILLEGAL_PASSWORD);
        return true;
    }


}
