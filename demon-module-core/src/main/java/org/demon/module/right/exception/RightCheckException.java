package org.demon.module.right.exception;

import org.demon.starter.exception.LogicalException;
import org.demon.sdk.retCode.BizRetCode;

/**
 * 权限验证异常
 */
public class RightCheckException extends LogicalException {

    private static final long serialVersionUID = -1L;

    public RightCheckException(BizRetCode.RetCode bizRetCode) {
        super(bizRetCode);
    }
}
