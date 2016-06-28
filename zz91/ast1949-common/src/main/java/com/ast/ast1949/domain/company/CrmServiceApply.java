/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
public class CrmServiceApply extends DomainSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyGroup;
	private String orderNo;
	private Date gmtIncome;
	private String email;
	private Integer amount;
	private String amountDetails;
	private String saleStaff;
	private String remark;
	private String membershipCode;
	private Date gmtCreated;
	private Date gmtModified;
	
	public CrmServiceApply() {
		super();
	}

	public CrmServiceApply(String applyGroup, String orderNo, Date gmtIncome,
			String email, Integer amount, String amountDetails,
			String saleStaff, String remark, String membershipCode, Date gmtCreated, Date gmtModified) {
		super();
		this.applyGroup = applyGroup;
		this.orderNo = orderNo;
		this.gmtIncome = gmtIncome;
		this.email = email;
		this.amount = amount;
		this.amountDetails = amountDetails;
		this.saleStaff = saleStaff;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.membershipCode=membershipCode;
	}
	
	public String getApplyGroup() {
		return applyGroup;
	}
	public void setApplyGroup(String applyGroup) {
		this.applyGroup = applyGroup;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getGmtIncome() {
		return gmtIncome;
	}
	public void setGmtIncome(Date gmtIncome) {
		this.gmtIncome = gmtIncome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getAmountDetails() {
		return amountDetails;
	}
	public void setAmountDetails(String amountDetails) {
		this.amountDetails = amountDetails;
	}
	public String getSaleStaff() {
		return saleStaff;
	}
	public void setSaleStaff(String saleStaff) {
		this.saleStaff = saleStaff;
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

	/**
	 * @return the membershipCode
	 */
	public String getMembershipCode() {
		return membershipCode;
	}

	/**
	 * @param membershipCode the membershipCode to set
	 */
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}
	
}
