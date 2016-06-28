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
public class CrmCsLog extends DomainSupport{


	private static final long serialVersionUID = 1L;

	private String csAccount;
	private Integer companyId;
	private Date gmtNextVisitPhone;
	private Date gmtNextVisitEmail;
	private String isVisitPhone;
	private String isVisitEmail;
	private String isVisitSms;
	private Integer callType;
	private Integer situation;
	private Integer operation;
	private String operationDetails;
	private Integer transaction;
	private String transactionDetails;
	private String feedback;
	private String suggestion;
	private Integer issueStatus;
	private String issueDetails;
	private String visitTarget;
	private Integer star;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer pv;//流量
	private Integer loginCount;//登录次数
	private Date gmtLastLoginTime;//最近一次登录时间
	public CrmCsLog() {
		super();
	}
	public CrmCsLog(String csAccount, Integer companyId,
			Date gmtNextVisitPhone, Date gmtNextVisitEmail,
			String isVisitPhone, String isVisitEmail, String isVisitSms,
			Integer callType, Integer situation, Integer operation,
			String operationDetails, Integer transaction,
			String transactionDetails, String feedback, String suggestion,
			Integer issueStatus, String issueDetails, String visitTarget,
			Integer star, String remark, Date gmtCreated, Date gmtModified,Integer pv,Integer loginCount,Date gmtLastLoginTime) {
		super();
		this.csAccount = csAccount;
		this.companyId = companyId;
		this.gmtNextVisitPhone = gmtNextVisitPhone;
		this.gmtNextVisitEmail = gmtNextVisitEmail;
		this.isVisitPhone = isVisitPhone;
		this.isVisitEmail = isVisitEmail;
		this.isVisitSms = isVisitSms;
		this.callType = callType;
		this.situation = situation;
		this.operation = operation;
		this.operationDetails = operationDetails;
		this.transaction = transaction;
		this.transactionDetails = transactionDetails;
		this.feedback = feedback;
		this.suggestion = suggestion;
		this.issueStatus = issueStatus;
		this.issueDetails = issueDetails;
		this.visitTarget = visitTarget;
		this.star = star;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.pv = pv;
		this.loginCount = loginCount;
		this.gmtLastLoginTime = gmtLastLoginTime;
	}
	public String getCsAccount() {
		return csAccount;
	}
	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Date getGmtNextVisitPhone() {
		return gmtNextVisitPhone;
	}
	public void setGmtNextVisitPhone(Date gmtNextVisitPhone) {
		this.gmtNextVisitPhone = gmtNextVisitPhone;
	}
	public Date getGmtNextVisitEmail() {
		return gmtNextVisitEmail;
	}
	public void setGmtNextVisitEmail(Date gmtNextVisitEmail) {
		this.gmtNextVisitEmail = gmtNextVisitEmail;
	}
	public String getIsVisitPhone() {
		return isVisitPhone;
	}
	public void setIsVisitPhone(String isVisitPhone) {
		this.isVisitPhone = isVisitPhone;
	}
	public String getIsVisitEmail() {
		return isVisitEmail;
	}
	public void setIsVisitEmail(String isVisitEmail) {
		this.isVisitEmail = isVisitEmail;
	}
	public String getIsVisitSms() {
		return isVisitSms;
	}
	public void setIsVisitSms(String isVisitSms) {
		this.isVisitSms = isVisitSms;
	}
	public Integer getCallType() {
		return callType;
	}
	public void setCallType(Integer callType) {
		this.callType = callType;
	}
	public Integer getSituation() {
		return situation;
	}
	public void setSituation(Integer situation) {
		this.situation = situation;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	public String getOperationDetails() {
		return operationDetails;
	}
	public void setOperationDetails(String operationDetails) {
		this.operationDetails = operationDetails;
	}
	public Integer getTransaction() {
		return transaction;
	}
	public void setTransaction(Integer transaction) {
		this.transaction = transaction;
	}
	public String getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public Integer getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}
	public String getIssueDetails() {
		return issueDetails;
	}
	public void setIssueDetails(String issueDetails) {
		this.issueDetails = issueDetails;
	}
	public String getVisitTarget() {
		return visitTarget;
	}
	public void setVisitTarget(String visitTarget) {
		this.visitTarget = visitTarget;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
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
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Date getgmtLastLoginTime() {
		return gmtLastLoginTime;
	}
	public void setgmtLastLoginTime(Date gmtLastLoginTime) {
		this.gmtLastLoginTime = gmtLastLoginTime;
	}
}
