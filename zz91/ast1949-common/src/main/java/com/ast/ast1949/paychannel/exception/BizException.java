package com.ast.ast1949.paychannel.exception;

import org.apache.log4j.Logger;

/**
 * 业务异常
 */
public class BizException extends Exception {
	private final static Logger logger = Logger.getLogger(BizException.class);
	private static final long serialVersionUID = 1;
	private String _errorCode = "9900";
	private String _moduleCode = "";
	private String _subsystem = "";
	private Exception _exception; // 底层异常

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String msg) {
		super(msg);
		logger.debug(msg);
	}

	public BizException(String errorCode, String msg) {
		super(msg);
		_errorCode = errorCode;
		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public BizException(String errorCode, String msg, String moduleCode, String subsystem) {
		super(msg);
		_errorCode = errorCode;
		_moduleCode = moduleCode;
		_subsystem = subsystem;

		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public BizException(Exception exception, String msg) {
		super(msg);
		_exception = exception;
		logger.debug(msg);
	}

	public BizException(Exception exception, String errorCode, String msg) {
		super(msg);
		_errorCode = errorCode;
		_exception = exception;
		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public BizException(Exception exception, String errorCode, String msg, String moduleCode, String subsystem) {
		super(msg);
		_errorCode = errorCode;
		_exception = exception;
		_moduleCode = moduleCode;
		_subsystem = subsystem;
		logger.debug("错误代码{" + errorCode + "},错误描述{" + msg + "}");
	}

	public String getErrorCode() {
		return _errorCode;
	}

	public BizException() {
	}

	public Exception getRootCause() {
		if (_exception instanceof BizException) {
			return ((BizException) _exception).getRootCause();
		}
		return _exception == null ? this : _exception;
	}

	public String toString() {
		String desc = "错误码为：" + _errorCode + "，错误描述为：" + this.getMessage();
		if (_exception != null) {
			desc = desc + "，底层异常为：" + _exception;
		}
		return desc;
	}

	public String get_moduleCode() {
		return _moduleCode;
	}

	public String get_subsystem() {
		return _subsystem;
	}

}
