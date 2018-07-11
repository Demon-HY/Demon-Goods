package org.demon.starter.utils;

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
    ERR_ACCOUNT_NOT_FOUND("资金账户不存在", 300003),

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
        for(RetCodeEnum RetCodeEnum : RetCodeEnum.values()){
            if(RetCodeEnum.retCode.equals(value)){
                return RetCodeEnum.message;
            }
        }

        return null;
    }
}
