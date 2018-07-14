package org.demon.sdk.exception;

import org.demon.sdk.utils.RetCodeEnum;

/**
 * 参数错误异常
 *
 * @author Demon
 */
public class ParamException extends LogicalException {

    private static final long serialVersionUID = -2434647353423193324L;

    public ParamException() {
        super(RetCodeEnum.ERR_BAD_PARAMS);
    }
}
