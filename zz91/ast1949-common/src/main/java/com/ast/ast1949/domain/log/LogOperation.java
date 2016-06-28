package com.ast.ast1949.domain.log;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class LogOperation extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5848694845942241736L;

	private Integer targetId;
	private String operator;
	private String operation;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
