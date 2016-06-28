/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-2 上午11:31:59
 */
package com.ast.ast1949.exception;

/**
 * 注册信息不合法时抛出的异常
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class RegisterException extends Exception {

	private static final long serialVersionUID = -1316056886920759783L;

	public RegisterException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisterException(String message) {
		super(message);
	}
}
