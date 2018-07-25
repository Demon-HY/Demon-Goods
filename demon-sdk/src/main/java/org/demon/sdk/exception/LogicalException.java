package org.demon.sdk.exception;

import org.demon.sdk.utils.RetCodeEnum;

import java.io.Serializable;

/**
 * 逻辑错误异常
 * <p>
 * Created by Demon-HY on 2018/3/19 0019.
 */
public class LogicalException extends Exception implements Serializable {

    private static final long serialVersionUID = 1515073568935089979L;
    public Integer stat;
    public String errMsg;

    public LogicalException(RetCodeEnum retCodeEnum) {
        super(retCodeEnum.retCode + "\t" + retCodeEnum.message);
        this.stat = retCodeEnum.retCode;
        this.errMsg = retCodeEnum.message;
    }

    public LogicalException(RetCodeEnum retCodeEnum, Exception e) {
        super(retCodeEnum.message, e);
        this.stat = retCodeEnum.retCode;
        this.errMsg = retCodeEnum.message;
    }

    @Override
    public String toString() {
        return "LogicalException{" +
                "stat=" + stat +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
