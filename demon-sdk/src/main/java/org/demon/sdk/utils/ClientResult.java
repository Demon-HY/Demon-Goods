package org.demon.sdk.utils;

import java.io.Serializable;

public class ClientResult<T> implements Serializable {

    private static final long serialVersionUID = -5552820761684566855L;
    /**
     * o-失败,1-成功
     */
    private Integer code = 1;

    private String message = RetCodeEnum.OK.getMessage();

    private T result;

    private boolean success = true;

    // 接口返回码定义
    private Integer retCode = RetCodeEnum.OK.getRetCode();

    public Integer getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return !isSuccess();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ClientResult() {}

    public ClientResult(RetCodeEnum retCodeEnum) {
        this.retCode = retCodeEnum.getRetCode();
        this.message = retCodeEnum.getMessage();
        this.code = 0;
        this.success = false;
    }

    public ClientResult(RetCodeEnum retCodeEnum, String message) {
        this.retCode = retCodeEnum.getRetCode();
        this.message = message;
        this.code = 0;
        this.success = false;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public ClientResult setMessage(String message) {
        this.message = message;
        this.retCode = RetCodeEnum.ERR_SERVER_EXCEPTION.getRetCode();
        this.code = 0;
        this.success = false;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ClientResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public ClientResult<T> setRetCode(RetCodeEnum retCodeEnum) {
        this.retCode = retCodeEnum.getRetCode();
        this.message = retCodeEnum.getMessage();
        this.code = 0;
        this.success = false;
        return this;
    }

    /**
     * 错误
     * @param retCodeEnum 错误码
     * @return ClientResult
     */
    public static ClientResult error(RetCodeEnum retCodeEnum) {
        return new ClientResult(retCodeEnum);
    }

    /**
     * 错误
     * @param message 错误描述
     * @return ClientResult
     */
    public static ClientResult errorMsg(String message) {
        return new ClientResult(RetCodeEnum.ERR_OPERATION_NOT_SUPPORTED, message);
    }

    /**
     * 成功
     * @param result 返回数据
     * @return ClientResult
     */
    public static<T> ClientResult success(T result) {
        ClientResult<T> cr = new ClientResult<>();
        return cr.setResult(result);
    }
}
