package org.demon.starter.common.retCode;

import lombok.Data;

/**
 * 系统错误码 [3位的业务类型,3位的具体错误码]
 * 200 - 操作成功
 * 100XXX - 系统级错误
 */
@Data
public class BaseRetCode {

    @Data
    public static class RetCode {
        public String message;
        public Integer retCode;

        private RetCode(String message, Integer retCode) {
            this.message = message;
            this.retCode = retCode;
        }
        
        public static RetCode newInstance(String message, Integer retCode) {
            return new RetCode(message, retCode);
        }
    }

    /** 操作成功 */
    public static RetCode OK = RetCode.newInstance("操作成功", 200);
    /** 系统繁忙，请稍后重试 */
    public static RetCode ERR_SERVER_EXCEPTION = RetCode.newInstance("系统繁忙，请稍后重试", 100000);
    /** 参数错误 */
    public static RetCode ERR_BAD_PARAMS = RetCode.newInstance("参数错误", 100001);
    /** 无访问权限 */
    public static RetCode ERR_FORBIDDEN = RetCode.newInstance("无访问权限", 100002);
    /** 非法JSON串 */
    public static RetCode ERR_INVALID_JSON = RetCode.newInstance("非法JSON串", 100003);
    /** 资源不存在 */
    public static RetCode ERR_NOT_FOUND = RetCode.newInstance("资源不存在", 100004);
    /** 不支持该操作 */
    public static RetCode ERR_OPERATION_NOT_SUPPORTED = RetCode.newInstance("不支持该操作", 100005);
}
