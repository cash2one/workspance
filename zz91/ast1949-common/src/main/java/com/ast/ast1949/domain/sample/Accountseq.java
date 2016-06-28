package com.ast.ast1949.domain.sample;

import java.math.BigDecimal;
import java.util.Date;

public class Accountseq {
	private Integer id;

	private Integer accountId;

	private String seqflag;

	private String changeType;

	private BigDecimal preamount;

	private BigDecimal amount;

	private BigDecimal changeAmount;

	private Integer refsn;

	private Date createTime;

	private Date gmtModified;

	private String note;

	private String orderid;

	private String companyName;
	private Integer companyId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getSeqflag() {
		return seqflag;
	}

	public void setSeqflag(String seqflag) {
		this.seqflag = seqflag == null ? null : seqflag.trim();
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType == null ? null : changeType.trim();
	}

	public BigDecimal getPreamount() {
		return preamount;
	}

	public void setPreamount(BigDecimal preamount) {
		this.preamount = preamount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	public Integer getRefsn() {
		return refsn;
	}

	public void setRefsn(Integer refsn) {
		this.refsn = refsn;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid == null ? null : orderid.trim();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}