package com.ast.ast1949.domain.sample;

import java.math.BigDecimal;
import java.util.Date;

public class Refund {
	private Integer id;

	private Integer companyId;

	private Integer orderbillId;

	private String orderId;

	private String state;

	private BigDecimal refundAmount;

	private String refundType;

	private String isflag;

	private String isAgree;

	private String refundReson;

	private String refundDes;

	private Integer refundNum;

	private Integer refundAddrId;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getOrderbillId() {
		return orderbillId;
	}

	public void setOrderbillId(Integer orderbillId) {
		this.orderbillId = orderbillId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType == null ? null : refundType.trim();
	}

	public String getIsflag() {
		return isflag;
	}

	public void setIsflag(String isflag) {
		this.isflag = isflag == null ? null : isflag.trim();
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree == null ? null : isAgree.trim();
	}

	public String getRefundReson() {
		return refundReson;
	}

	public void setRefundReson(String refundReson) {
		this.refundReson = refundReson == null ? null : refundReson.trim();
	}

	public String getRefundDes() {
		return refundDes;
	}

	public void setRefundDes(String refundDes) {
		this.refundDes = refundDes == null ? null : refundDes.trim();
	}

	public Integer getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}

	public Integer getRefundAddrId() {
		return refundAddrId;
	}

	public void setRefundAddrId(Integer refundAddrId) {
		this.refundAddrId = refundAddrId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}