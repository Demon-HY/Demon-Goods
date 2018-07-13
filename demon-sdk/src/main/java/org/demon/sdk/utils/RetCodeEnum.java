package org.demon.sdk.utils;

/**
 * 返回给客户端的错误码 [3位的业务类型,3位的具体错误码]
 * 200 - 操作成功
 * 100XXX - 系统级错误
 * 300XXX - 用户
 */
public enum RetCodeEnum {

    OK("操作成功", 200),

    ERR_SERVER_EXCEPTION("系统繁忙，请稍后重试", 100000),
    ERR_BAD_PARAMS("参数错误", 100001),
    ERR_FORBIDDEN("无访问权限", 100002),
    ERR_INVALID_JSON("非法JSON串", 100003),
    ERR_NOT_FOUND("资源不存在", 100004),
    ERR_OPERATION_NOT_SUPPORTED("不支持该操作", 100005),

    ERR_USER_NOT_FOUND("获取用户信息出错", 300001),
    ERR_USER_NOT_LOGIN("请登录", 300002),
    ERR_ACCOUNT_EXIST("该账号已被使用", 300003),
    ERR_PHONE_ALREADY_BOUND("手机号已经被绑定", 300003),
    ERR_SEND_CODE_FREQUENTLY("请不要重复发送验证码", 300003),
    ERR_CLEAN_ACCOUNT_FAILED("清除用户数据失败", 300003),
    ERR_ADD_LOGIN_ID_FAILED("注册登录信息失败", 300003),
    ERR_NO_SUCH_ACCOUNT("用户不存在", 300003),
    ERR_USER_INFO_BROKEN("用户信息损坏", 300003),
    ERR_INVALID_PASSWORD("密码错误", 300003),
    ERR_ILLEGAL_ACCOUNT_TYPE("非法账号类型", 300003),
    ERR_ILLEGAL_EMAIL_ACCOUNT("非法邮箱", 300003),
    ERR_ILLEGAL_PHONE_ACCOUNT("非法手机号", 300003),
    ERR_ILLEGAL_PASSWORD("非法密码", 300003),
    ERR_TOKEN_NOT_FOUND("请登录", 300003),
    ERR_TOKEN_EXPIRED("请重新登录", 300003),
    ERR_VALIDATE_CODE_EXPIRED("验证码过期", 300003),
    ERR_INVALID_VALIDATE_CODE("验证码错误", 300003),
    ERR_TOKEN_UID_MISMATCHING("登录的用户与新建token用户不为一个用户", 300003),
    ERR_USER_LOCKED("用户已被锁定", 300003),

    ;

    private String message;
    private Integer retCode;

    RetCodeEnum(String message, Integer retCode) {
        this.message = message;
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRetCode() {
        return retCode;
    }

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
