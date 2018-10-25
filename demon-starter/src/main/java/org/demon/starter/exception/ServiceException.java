package org.demon.starter.exception;

/**
 * 系统错误异常
 * <p>
 * Created by Demon-HY on 2018/3/19 0019.
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    public String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }
}
