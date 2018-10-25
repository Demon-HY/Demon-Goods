package org.demon.web.exception;

import org.demon.starter.exception.LogicalException;
import org.demon.starter.utils.ClientResult;
import org.demon.starter.utils.RetCodeEnum;
import org.demon.starter.common.logger.AbstractLogClass;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理,在这里捕获的是将要抛给 JVM 的异常，这些异常会返回给调用者，而我们不希望调用者看到这些数据，在这里屏蔽
 * 这里没有对异常做打印记录，异常的打印交给拦截器 WebLoggerAspect 处理，这样方便日志的查看
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends AbstractLogClass {

    @ExceptionHandler(Exception.class)
    public ClientResult handleException(Exception e) {
        logger.error("{}", RetCodeEnum.ERR_SERVER_EXCEPTION.message, e);
        return ClientResult.error(RetCodeEnum.ERR_SERVER_EXCEPTION);
    }

    @ExceptionHandler(LogicalException.class)
    public ClientResult handleLogicalException(LogicalException e) {
        // TODO 这里可以配置邮件或者短信提醒

        logger.error(e.getMessage(), e);
        return ClientResult.error(e.errMsg, e.stat);
    }

    /**
     * 处理实体字段校验不通过异常
     *
     * @param e MethodArgumentNotValidException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ClientResult handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();

        logger.error(error.getDefaultMessage(), e);
        return ClientResult.error(error.getDefaultMessage(), RetCodeEnum.ERR_BAD_PARAMS.retCode);
    }
}
