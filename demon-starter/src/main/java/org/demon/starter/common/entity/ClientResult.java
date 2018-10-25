package org.demon.starter.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.demon.starter.common.retCode.BaseRetCode;

import java.io.Serializable;

/**
 * 配合前端统一返回结果的格式工具类
 * @param <T>
 */
@Data
@ApiModel(value = "配合前端统一返回结果的格式工具类")
public class ClientResult<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "错误消息")
    private String message = BaseRetCode.OK.message;

    @ApiModelProperty(value = "返回数据")
    private T result;

    @ApiModelProperty(value = "是否成功")
    private Boolean success = true;

    // 接口返回码定义
    @ApiModelProperty(value = "错误码")
    private Integer retCode = BaseRetCode.OK.retCode;

    private ClientResult() {
    }

    private ClientResult(BaseRetCode.RetCode retCodeEnum) {
        this.retCode = retCodeEnum.retCode;
        this.message = retCodeEnum.message;
        this.success = false;
    }

    private ClientResult(BaseRetCode.RetCode retCodeEnum, String message) {
        this.retCode = retCodeEnum.retCode;
        this.message = message;
        this.success = false;
    }

    private ClientResult(Integer retCode, String message) {
        this.retCode = retCode;
        this.message = message;
        this.success = false;
    }

    private ClientResult<T> setMessage(String message) {
        this.message = message;
        this.retCode = BaseRetCode.ERR_SERVER_EXCEPTION.retCode;
        this.success = false;
        return this;
    }

    private ClientResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    private ClientResult<T> setRetCode(BaseRetCode.RetCode retCodeEnum) {
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
    public static ClientResult error(BaseRetCode.RetCode retCodeEnum) {
        return new ClientResult(retCodeEnum);
    }

    /**
     * 错误: 不支持该操作
     *
     * @param message 错误描述
     * @return ClientResult
     */
    public static ClientResult error(String message) {
        return new ClientResult(BaseRetCode.ERR_OPERATION_NOT_SUPPORTED, message);
    }

    /**
     * 错误
     *
     * @param message 错误描述
     * @return ClientResult
     */
    public static ClientResult error(String message, Integer statCode) {
        return new ClientResult(statCode, message);
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

    /**
     * 是否成功
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * 是否失败
     */
    public Boolean isFailed() {
        return !isSuccess();
    }
}
