package com.zz91.crm.exception;

import org.springframework.dao.DataAccessException;

public class LogicException extends DataAccessException {

	private static final long serialVersionUID = 1L;
	
	public LogicException(String msg) {
		super(msg);
	}

}
