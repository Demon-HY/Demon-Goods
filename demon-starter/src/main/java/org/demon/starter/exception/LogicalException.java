package org.demon.starter.exception;


import org.demon.starter.common.retCode.BaseRetCode;

/**
 * 逻辑错误异常
 * <p>
 * Created by Demon-HY on 2018/3/19 0019.
 */
public class LogicalException extends RuntimeException {

    private static final long serialVersionUID = -1L;
    public Integer stat;
    public String errMsg;

    public LogicalException(BaseRetCode.RetCode retCode) {

        super(retCode.retCode + "\t" + retCode.message);
        this.stat = retCode.retCode;
        this.errMsg = retCode.message;
    }

    public LogicalException(BaseRetCode.RetCode retCode, Exception e) {
        super(retCode.message, e);
        this.stat = retCode.retCode;
        this.errMsg = retCode.message;
    }

    @Override
    public String toString() {
        return "LogicalException{" +
                "stat=" + stat +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
