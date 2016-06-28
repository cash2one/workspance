package com.ast.ast1949.exception;

@Deprecated
public class AuthorizeException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -4184884450072605567L;

	public AuthorizeException(String message, Throwable cause){
		super(message, cause);
	}

	public AuthorizeException(String message){
		super(message);
	}
}
