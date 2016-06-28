package com.zz91.crm.domain;

import java.io.Serializable;
import java.util.Date;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-6 
 */
public class SysLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String operation;
	private Integer targetId;
	private String saleAccount;
	private String saleDept;
	private String saleName;
	private String saleIp;
	private String details;
	private Date gmtCreated;
	private Date gmtModified;
	private String cname;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperation() {
		return operation;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setSaleAccount(String saleAccount) {
		this.saleAccount = saleAccount;
	}
	public String getSaleAccount() {
		return saleAccount;
	}
	public void setSaleDept(String saleDept) {
		this.saleDept = saleDept;
	}
	public String getSaleDept() {
		return saleDept;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleIp(String saleIp) {
		this.saleIp = saleIp;
	}
	public String getSaleIp() {
		return saleIp;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDetails() {
		return details;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCname() {
		return cname;
	}
}
