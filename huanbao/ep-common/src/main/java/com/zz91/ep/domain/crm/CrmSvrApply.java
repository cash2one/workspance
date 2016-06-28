/*
 * 文件名称：CrmSvrApply.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.crm;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：会员申请服务表。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CrmSvrApply implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String applyGroup;
	private String targetEmail;
	private String targetAccount;
	private Integer targetCid;
	private Date gmtIncome;
	private Integer amount;
	private String amountDetails;
	private String saleStaff;
	private String saleStaffName;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApplyGroup() {
		return this.applyGroup;
	}

	public void setApplyGroup(String applyGroup) {
		this.applyGroup = applyGroup;
	}

	public String getTargetEmail() {
		return this.targetEmail;
	}

	public void setTargetEmail(String targetEmail) {
		this.targetEmail = targetEmail;
	}

	public String getTargetAccount() {
		return this.targetAccount;
	}

	public void setTargetAccount(String targetAccount) {
		this.targetAccount = targetAccount;
	}

	public Integer getTargetCid() {
		return this.targetCid;
	}

	public void setTargetCid(Integer targetCid) {
		this.targetCid = targetCid;
	}

	public Date getGmtIncome() {
		return this.gmtIncome;
	}

	public void setGmtIncome(Date gmtIncome) {
		this.gmtIncome = gmtIncome;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAmountDetails() {
		return this.amountDetails;
	}

	public void setAmountDetails(String amountDetails) {
		this.amountDetails = amountDetails;
	}

	public String getSaleStaff() {
		return this.saleStaff;
	}

	public void setSaleStaff(String saleStaff) {
		this.saleStaff = saleStaff;
	}

	public String getSaleStaffName() {
		return this.saleStaffName;
	}

	public void setSaleStaffName(String saleStaffName) {
		this.saleStaffName = saleStaffName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}