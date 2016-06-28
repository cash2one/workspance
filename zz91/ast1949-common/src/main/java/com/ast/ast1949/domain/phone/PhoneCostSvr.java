package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PhoneCostSvr extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8083641499534597591L;
	
	private Integer id;
	private Integer companyId;// INT(11) NOT NULL COMMENT '公司id' ,
	private Integer crmCompanyServiceId; // INT(11) NOT NULL COMMENT '开通服务id' ,
	private Float fee;// FLOAT NOT NULL COMMENT '充值总额 消费金额' ,
	private Float lave;// FLOAT NOT NULL COMMENT '余额' ,
	private Float clickFee;// FLOAT NOT NULL DEFAULT 1 COMMENT '点击查看联系方式费用 每一家公司' ,
	private Float telFee;// FLOAT NOT NULL DEFAULT 2.8 COMMENT '通话费用 每分钟' ,
	private String isLack;// CHAR(1) NULL DEFAULT 0 COMMENT '欠费状态\n1为余额不足;0为未欠费' ,
	private Date gmtCreated;
	private Date gmtModified;
	private Date gmtZero;
	private Date gmtRenew;
	
	
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
	public Integer getCrmCompanyServiceId() {
		return crmCompanyServiceId;
	}
	public void setCrmCompanyServiceId(Integer crmCompanyServiceId) {
		this.crmCompanyServiceId = crmCompanyServiceId;
	}
	public Float getFee() {
		return fee;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public Float getLave() {
		return lave;
	}
	public void setLave(Float lave) {
		this.lave = lave;
	}
	public Float getClickFee() {
		return clickFee;
	}
	public void setClickFee(Float clickFee) {
		this.clickFee = clickFee;
	}
	public Float getTelFee() {
		return telFee;
	}
	public void setTelFee(Float telFee) {
		this.telFee = telFee;
	}
	public String getIsLack() {
		return isLack;
	}
	public void setIsLack(String isLack) {
		this.isLack = isLack;
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
	public Date getGmtZero() {
		return gmtZero;
	}
	public void setGmtZero(Date gmtZero) {
		this.gmtZero = gmtZero;
	}
	public Date getGmtRenew() {
		return gmtRenew;
	}
	public void setGmtRenew(Date gmtRenew) {
		this.gmtRenew = gmtRenew;
	}
}
