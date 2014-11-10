package com.kzone.bo;

import java.io.Serializable;

/**
 * Created by Jeffy on 2014/5/27 0027.
 */
public class ErrorMessage implements Serializable {
    private static final long serialVersionUID = 1028625349191933086L;
    private String errorCode;
    private String message;

    public ErrorMessage(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
