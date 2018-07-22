package org.demon.web.exception;

import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.utils.ClientResult;
import org.demon.sdk.utils.RetCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理,在这里捕获的是将要抛给 JVM 的异常，这些异常会返回给调用者，而我们不希望调用者看到这些数据，在这里屏蔽
 * 这里没有对异常做打印记录，异常的打印交给拦截器 WebLoggerAspect 处理，这样方便日志的查看
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ClientResult handleException(Exception e) {
        // TODO 这里可以配置邮件或者短信提醒

        if (e instanceof LogicalException) {
            LogicalException  logicalException = (LogicalException) e;
            logger.error(logicalException.getMessage(), e);
            return ClientResult.error(RetCodeEnum.getRetCodeEnum(logicalException.stat));
        }

        logger.error(RetCodeEnum.ERR_SERVER_EXCEPTION.message, e);
        return ClientResult.error(RetCodeEnum.ERR_SERVER_EXCEPTION);
    }
}
