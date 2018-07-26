package org.demon.sdk.exception;

import java.io.Serializable;

public class ServiceException extends Exception implements Serializable {
    private static final long serialVersionUID = 7687879655271362293L;

    public String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }
}
