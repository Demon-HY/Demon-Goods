package org.demon.web.common.exception;

import org.demon.web.utils.RetCodeEnum;

import java.io.Serializable;

/**
 * 逻辑错误异常
 *
 * Created by Demon-HY on 2018/3/19 0019.
 */
public class LogicalException extends Exception implements Serializable {


    private static final long serialVersionUID = 1515073568935089979L;
    public String stat;
    public String errMsg;

    public LogicalException(RetCodeEnum retCodeEnum) {
        super(retCodeEnum.getRetCode() + "\t" + retCodeEnum.getMessage());
        this.stat = retCodeEnum.getRetCode().toString();
        this.errMsg = retCodeEnum.getMessage();
    }

    public LogicalException(String stat, Exception e) {
        super(stat, e);
        this.errMsg = stat;
    }
}
