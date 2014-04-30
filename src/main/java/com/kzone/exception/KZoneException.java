package com.kzone.exception;

/**
 * This exception is defined on a top level of the HuaCloud system.
 * 
 * @author Eric Feng
 */
@SuppressWarnings("serial")
public class KZoneException extends RuntimeException {
	private String errorCode = null;

	public KZoneException(String errorCode, String message, Throwable cause) {
		super(message, cause);

		this.errorCode = errorCode;
	}

	public KZoneException(String errorCode, String message) {
		this(errorCode, message, null);
	}

	public String getErrorCode() {
		return errorCode;
	}
}
