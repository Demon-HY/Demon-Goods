package org.demon.sdk.utils;

/**
 * 返回给客户端的错误码 [3位的业务类型,3位的具体错误码]
 * 200 - 操作成功
 * 100XXX - 系统级错误
 * 300XXX - 用户
 * 301XXX - 鉴权
 */
public enum RetCodeEnum {

    OK("操作成功", 200),

    // 系统级错误
    ERR_SERVER_EXCEPTION("系统繁忙，请稍后重试", 100000),
    ERR_BAD_PARAMS("参数错误", 100001),
    ERR_FORBIDDEN("无访问权限", 100002),
    ERR_INVALID_JSON("非法JSON串", 100003),
    ERR_NOT_FOUND("资源不存在", 100004),
    ERR_OPERATION_NOT_SUPPORTED("不支持该操作", 100005),

    // 用户
    ERR_USER_NOT_FOUND("用户不存在", 300001),
    ERR_REVISE_ACCOUNT_FORBIDDEN("禁止修改账号信息", 300002),
    ERR_CREATE_USER_FAILED("创建用户失败", 300003),
    ERR_SET_USER_ATTR_FAILED("设置用户属性失败", 300004),
    ERR_DELETE_USER_FAILED("删除用户失败", 300005),
    ERR_ACCOUNT_EXIST("该账号已被使用", 301006),
    ERR_PHONE_USED("手机已被使用", 301007),
    ERR_EMAIL_USED("邮箱已被使用", 301008),
    ERR_ILLEGAL_PHONE("无效电话号码", 301009),
    ERR_ILLEGAL_PASSWORD("无效密码", 301010),
    ERR_USER_INFO_BROKEN("用户信息损坏", 301011),
    ERR_WRONG_PASSWORD("密码错误", 301012),
    ERR_RESET_PASSWORD_FAILED("设置密码失败", 301013),
    ERR_DELETE_SELF_FORBITEN("不能删除自己", 301014),
    ERR_USER_LOCKED("用户已被锁定", 301015),
    ERR_USER_ALREADY_USED("用户已登录使用", 301016),
    ERR_REGISTER_CONFIRM_EXPIRED("验证码已过期,请重新获取", 301017),
    ERR_RETRIEVE_EMAIL_EXPIRED("找回密码邮件过期", 301018),
    ERR_ILLEGAL_USER_NAME("无效的用户名", 301019),
    ERR_INIT_SERVERADMIN_USER("不能删除系统初始创建的serverAdmin用户", 301020),
    ERR_USER_DELETE("用户已被删除", 301021),

    // 鉴权,
    ERR_TOKEN("无效令牌", 301001),
    ERR_PHONE_ALREADY_BOUND("手机号已经被绑定", 301002),
    ERR_SEND_CODE_FREQUENTLY("请不要重复发送验证码", 301003),
    ERR_VALIDATE_CODE_EXPIRED("验证码过期", 301004),
    ERR_INVALID_VALIDATE_CODE("验证码错误", 301005),
    ERR_USER_NOT_LOGIN("请登录", 301006),
    ERR_TOKEN_NOT_FOUND("请登录", 301007),
    ERR_TOKEN_EXPIRED("请重新登录", 301008),
    ERR_CLEAN_ACCOUNT_FAILED("清除用户数据失败", 301009),
    ERR_ADD_LOGIN_ID_FAILED("注册登录信息失败", 301010),
    ERR_NO_SUCH_ACCOUNT("用户不存在", 301011),
    ERR_INVALID_PASSWORD("密码错误", 301012),
    ERR_ILLEGAL_ACCOUNT_TYPE("非法账号类型", 301013),
    ERR_ILLEGAL_EMAIL_ACCOUNT("非法邮箱", 301014),
    ERR_ILLEGAL_PHONE_ACCOUNT("非法手机号", 301015),
    ERR_TOKEN_UID_MISMATCHING("登录的用户与新建token用户不为一个用户", 301016),

    ;

    public String message;
    public Integer retCode;

    RetCodeEnum(String message, Integer retCode) {
        this.message = message;
        this.retCode = retCode;
    }

//    public String getMessage() {
//        return message;
//    }
//
//    public Integer getRetCode() {
//        return retCode;
//    }

    public static String findNameByValue(Integer value){
        if (value == null) {
            return null;
        }
        for(org.demon.sdk.utils.RetCodeEnum RetCodeEnum : RetCodeEnum.values()){
            if(RetCodeEnum.retCode.equals(value)){
                return RetCodeEnum.message;
            }
        }

        return null;
    }
}
