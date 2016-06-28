package com.zz91.crm.domain;

import java.io.Serializable;
import java.util.Date;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-15 
 */
public class CrmRepeat implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer cid;
	private Integer orderId;
	private Short checkStatus;
	private String saleName;
	private String saleAccount;
	private String saleDept;
	private Date gmtCreated;
	private Date gmtModified;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Short getCheckStatus() {
		return checkStatus;
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
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleName() {
		return saleName;
	}
}
