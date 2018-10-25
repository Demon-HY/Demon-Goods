package org.demon.starter.exception;


import org.demon.starter.utils.RetCodeEnum;

/**
 * 参数错误异常
 *
 * @author Demon
 */
public class ParamException extends LogicalException {

    private static final long serialVersionUID = -1L;

    public ParamException() {
        super(RetCodeEnum.ERR_BAD_PARAMS);
    }
}
