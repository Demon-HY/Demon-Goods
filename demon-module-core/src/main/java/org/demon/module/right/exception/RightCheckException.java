package org.demon.module.right.exception;

import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.utils.RetCodeEnum;

/**
 * 权限验证异常
 */
public class RightCheckException extends LogicalException {

    public RightCheckException(RetCodeEnum retCodeEnum) {
        super(retCodeEnum);
    }
}
