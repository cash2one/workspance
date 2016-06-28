package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-7-13
 */
public class PhoneLog extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7671512371281240494L;

	private String callerId; // 主叫号码
	private String tel; // VARCHAR(100) NULL COMMENT '被叫400号码' ,
	private String callFee; // VARCHAR(100) NULL COMMENT '花费' ,
	private Date startTime; // DATETIME NULL COMMENT '开始时间' ,
	private Date endTime; // DATETIME NULL COMMENT '结束时间' ,
	private Date gmtCreated; // DATETIME NULL ,
	private Date gmtModified; // DATETIME NULL ,
	private String callSn;
	private String state; //已接未接的状态
	private Integer companyId;//400号码公司id
	private String address;//未接电话的地区
	private String province;
	private String city;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCallFee() {
		return callFee;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setCallFee(String callFee) {
		this.callFee = callFee;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getCallSn() {
		return callSn;
	}

	public void setCallSn(String callSn) {
		this.callSn = callSn;
	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
