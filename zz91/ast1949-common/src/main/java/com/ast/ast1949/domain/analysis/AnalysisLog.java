package com.ast.ast1949.domain.analysis;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.util.DateUtil;

public class AnalysisLog  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248022133737820810L;
	
	private Integer id;
	private String operator;
	private String operation;
	private Integer num;
	private Date gmtTarget;//统计日期
	private Date gmtCreated;
	private Date gmtModified;
	private String targetDate;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the gmtTarget
	 */
	public Date getGmtTarget() {
		return gmtTarget;
	}
	/**
	 * @param gmtTarget the gmtTarget to set
	 */
	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getTargetDate() {
		if(gmtTarget!=null){
			return DateUtil.toString(gmtTarget, "yyyy-MM-dd");
		}
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
}
