package org.demon.module.user;

import org.demon.module.auth.AuthUtils;
import org.demon.module.right.RightUtils;
import org.demon.sdk.model.entity.User;
import org.demon.sdk.model.dto.create.UserCreateDto;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.type.PostCreateUserEvent;
import org.demon.sdk.event.type.PreCreateUserEvent;
import org.demon.sdk.retCode.BizRetCode;
import org.demon.starter.exception.LogicalException;
import org.demon.sdk.inner.user.IUserBaseApi;
import org.demon.starter.common.logger.AbstractLogClass;
import org.demon.utils.ValidUtils;
import org.demon.utils.crypto.SSHAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserBaseApi extends AbstractLogClass implements IUserBaseApi {

    @Autowired
    private UserQueryApi userQueryApi;
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RightUtils rightUtils;

    @Override
    public User createUser(Env env, UserCreateDto userCreateDto) throws Exception {
        // 检查密码是否符合要求
        userQueryApi.checkPasswordIsLegal(userCreateDto.password);

        // TODO 校验权限,内部代码还未实现
        rightUtils.checkRight(env, UserConfigAbstract.MODULE_NAME, UserConfigAbstract.RIGHT_CREATE_USER.getValue0());

        AuthUtils.checkAccount("name", userCreateDto.name);

        // TODO 创建用户前事件
        PreCreateUserEvent preCreateUserEvent = new PreCreateUserEvent(this, env, userCreateDto);
        applicationContext.publishEvent(preCreateUserEvent);
        if (!preCreateUserEvent.isContinue) {
            logger.warn("{} 事件被 {} 拦截, 拦截原因: {}", PreCreateUserEvent.EVENT_TYPE,
                    preCreateUserEvent.lastHandler.getName(), preCreateUserEvent.breakReason);
            throw new LogicalException(preCreateUserEvent.retCode);
        }

        // 校验账号是否已存在
        User tempUser = userDao.findByAccount(userCreateDto.name);
        if (tempUser != null) {
            throw new LogicalException(BizRetCode.ERR_ACCOUNT_EXIST);
        }
        // 检查手机号是否唯一
        if (ValidUtils.isNotBlank(userCreateDto.phone)) {
            tempUser = userDao.findByPhone(userCreateDto.phone);
            if (tempUser != null) {
                throw new LogicalException(BizRetCode.ERR_PHONE_ALREADY_BOUND);
            }
        }
        // 补全用户信息
        User user = generateUser(userCreateDto);
        userDao.insert(user);

        // TODO 创建用户后事件
        PostCreateUserEvent postCreateUserEvent = new PostCreateUserEvent(this, env, user);
        applicationContext.publishEvent(postCreateUserEvent);
        if (!postCreateUserEvent.isContinue) {
            logger.warn("{} 事件被 {} 拦截, 拦截原因: {}", PostCreateUserEvent.EVENT_TYPE,
                    postCreateUserEvent.lastHandler.getName(), postCreateUserEvent.breakReason);
            throw new LogicalException(postCreateUserEvent.retCode);
        }

        return user;
    }

    @Override
    public User clearPassword(User user) {
        user.password = "********";
        return user;
    }

    /**
     * 生成用户
     *
     * @param userCreateDto
     * @return
     */
    private User generateUser(UserCreateDto userCreateDto) {
        User user = new User(userCreateDto.name, userCreateDto.nick, userCreateDto.phone, userCreateDto.email,
                userCreateDto.password, userCreateDto.qq);
        if (ValidUtils.isBlank(user.nick)) {
            user.nick = user.name;
        }
        user.password = SSHAUtils.getSaltedPassword(user.password);
        user.status = UserConfigAbstract.STATUS_NORMAL;
        user.type = 1;
        user.createTime = new Date();
        user.updateTime = new Date();
        user.loadTime = new Date();

        // 设置默认值
        if (ValidUtils.isBlank(user.phone)) {
            user.phone = "";
        }
        if (ValidUtils.isBlank(user.email)) {
            user.email = "";
        }
        if (ValidUtils.isBlank(user.qq)) {
            user.qq = "";
        }
        if (ValidUtils.isBlank(user.exattr)) {
            user.exattr = "";
        }

        return user;
    }




}
