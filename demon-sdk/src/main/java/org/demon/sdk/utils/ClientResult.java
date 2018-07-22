package org.demon.sdk.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "客户端获取数据")
public class ClientResult<T> implements Serializable {

    private static final long serialVersionUID = -5552820761684566855L;
    /**
     * o-失败,1-成功
     */
    @ApiModelProperty(value = "o-失败,1-成功")
    private Integer code = 1;

    @ApiModelProperty(value = "错误消息")
    private String message = RetCodeEnum.OK.message;

    @ApiModelProperty(value = "Object")
    private T result;

    @ApiModelProperty(value = "是否成功")
    private boolean success = true;

    // 接口返回码定义
    @ApiModelProperty(value = "错误码")
    private Integer retCode = RetCodeEnum.OK.retCode;

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
        this.retCode = retCodeEnum.retCode;
        this.message = retCodeEnum.message;
        this.code = 0;
        this.success = false;
    }

    public ClientResult(RetCodeEnum retCodeEnum, String message) {
        this.retCode = retCodeEnum.retCode;
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
        this.retCode = RetCodeEnum.ERR_SERVER_EXCEPTION.retCode;
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
        this.retCode = retCodeEnum.retCode;
        this.message = retCodeEnum.message;
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

    /**
     * 成功
     * @return ClientResult
     */
    public static<T> ClientResult success() {
        return new ClientResult<>();
    }
}
