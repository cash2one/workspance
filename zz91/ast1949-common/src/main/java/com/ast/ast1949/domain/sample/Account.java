package com.ast.ast1949.domain.sample;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
	private Integer id;

	private Integer companyId;

	private BigDecimal amount;

	private String aplipayAcc;

	private String tenpayAcc;

	private String state;

	private Date createTime;

	private Date lastupdateTime;

	private String checkValue;

	private String cancelTime;

	private String payPasswd;
	
	private String companyName;
	
	private String companyAccount;
	
	public Account() {
		super();
	}

	public Account(Integer companyId, BigDecimal amount, String state, Date createTime) {
		super();
		this.companyId = companyId;
		this.amount = amount;
		this.state = state;
		this.createTime = createTime;
	}
	
	public Account(Integer id, BigDecimal amount, String state, Date createTime, Date lastupdateTime) {
		super();
		this.id = id;
		this.amount = amount;
		this.state = state;
		this.createTime = createTime;
		this.lastupdateTime = lastupdateTime;
	}

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAplipayAcc() {
		return aplipayAcc;
	}

	public void setAplipayAcc(String aplipayAcc) {
		this.aplipayAcc = aplipayAcc == null ? null : aplipayAcc.trim();
	}

	public String getTenpayAcc() {
		return tenpayAcc;
	}

	public void setTenpayAcc(String tenpayAcc) {
		this.tenpayAcc = tenpayAcc == null ? null : tenpayAcc.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastupdateTime() {
		return lastupdateTime;
	}

	public void setLastupdateTime(Date lastupdateTime) {
		this.lastupdateTime = lastupdateTime;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue == null ? null : checkValue.trim();
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime == null ? null : cancelTime.trim();
	}

	public String getPayPasswd() {
		return payPasswd;
	}

	public void setPayPasswd(String payPasswd) {
		this.payPasswd = payPasswd == null ? null : payPasswd.trim();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	
}