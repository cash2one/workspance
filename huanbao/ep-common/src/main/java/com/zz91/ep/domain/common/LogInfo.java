package com.zz91.ep.domain.common;

import java.io.Serializable;
import java.util.Date;

public class LogInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String appCode;
	private String operator;
	private String operation;
	private Date time;
	private String data;
	
	public LogInfo(){
		
	}
	
	public LogInfo(String operator,String operation,Date time){
		super();
		this.operator = operator;
		this.operation = operation;
		this.time = time;
	}
	
	public LogInfo(Integer id, String appCode, String operator,
			String operation, Date time, String data) {
		super();
		this.id = id;
		this.appCode = appCode;
		this.operator = operator;
		this.operation = operation;
		this.time = time;
		this.data = data;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
