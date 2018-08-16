package org.demon.sdk.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "客户端获取数据")
public class ClientResult<T> implements Serializable {

    private static final long serialVersionUID = 3593561370510117488L;

    @ApiModelProperty(value = "错误消息")
    private String message = RetCodeEnum.OK.message;

    @ApiModelProperty(value = "返回数据")
    private T result;

    @ApiModelProperty(value = "是否成功")
    private boolean success = true;

    // 接口返回码定义
    @ApiModelProperty(value = "错误码")
    private Integer retCode = RetCodeEnum.OK.retCode;


    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return !isSuccess();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private ClientResult() {
    }

    private ClientResult(RetCodeEnum retCodeEnum) {
        this.retCode = retCodeEnum.retCode;
        this.message = retCodeEnum.message;
        this.success = false;
    }

    private ClientResult(RetCodeEnum retCodeEnum, String message) {
        this.retCode = retCodeEnum.retCode;
        this.message = message;
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    private ClientResult<T> setMessage(String message) {
        this.message = message;
        this.retCode = RetCodeEnum.ERR_SERVER_EXCEPTION.retCode;
        this.success = false;
        return this;
    }

    public T getResult() {
        return result;
    }

    private ClientResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    private Integer getRetCode() {
        return retCode;
    }

    private ClientResult<T> setRetCode(RetCodeEnum retCodeEnum) {
        this.retCode = retCodeEnum.retCode;
        this.message = retCodeEnum.message;
        this.success = false;
        return this;
    }

    /**
     * 错误
     *
     * @param retCodeEnum 错误码
     * @return ClientResult
     */
    public static ClientResult error(RetCodeEnum retCodeEnum) {
        return new ClientResult(retCodeEnum);
    }

    /**
     * 错误: 不支持该操作
     *
     * @param message 错误描述
     * @return ClientResult
     */
    public static ClientResult errorMsg(String message) {
        return new ClientResult(RetCodeEnum.ERR_OPERATION_NOT_SUPPORTED, message);
    }

    /**
     * 成功
     *
     * @param result 返回数据
     * @return ClientResult
     */
    public static <T> ClientResult<T> success(T result) {
        ClientResult<T> cr = new ClientResult<>();
        return cr.setResult(result);
    }

    /**
     * 成功
     *
     * @return ClientResult
     */
    public static ClientResult success() {
        return new ClientResult<>();
    }
}
