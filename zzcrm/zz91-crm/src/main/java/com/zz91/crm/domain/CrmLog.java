/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.domain;

import java.util.Date;

/**
 * @author totly created on 2011-12-10
 */
public class CrmLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Short callType;
    private String saleDept;
    private String saleAccount;
    private String saleName;
    private Integer cid;
    private Short starOld;
    private Short star;
    private Date gmtNextContact;
    private Short nextContact;
    private Short situation;
    private Short operation;
    private String operationDetails;
    private Short transaction;
    private String transactionDetails;
    private String feedback;
    private String suggestion;
    private Short issueStatus;
    private String issueDetails;
    private String remark;
    private Date gmtCreated;
    private Date gmtModified;
    private Short flag;
    private String gmtNextContactStr;
    private Short source;
    private Short maturity;
    private Short promote;
    private Short kp;
    private Short contactType;
   
	public CrmLog() {
		super();
	}

	public CrmLog(Integer id, Short callType, String saleDept,
			String saleAccount, String saleName, Integer cid, Short starOld,
			Short star, Date gmtNextContact, Short nextContact,
			Short situation, Short operation, String operationDetails,
			Short transaction, String transactionDetails, String feedback,
			String suggestion, Short issueStatus, String issueDetails,
			String remark, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.callType = callType;
		this.saleDept = saleDept;
		this.saleAccount = saleAccount;
		this.saleName = saleName;
		this.cid = cid;
		this.starOld = starOld;
		this.star = star;
		this.gmtNextContact = gmtNextContact;
		this.nextContact = nextContact;
		this.situation = situation;
		this.operation = operation;
		this.operationDetails = operationDetails;
		this.transaction = transaction;
		this.transactionDetails = transactionDetails;
		this.feedback = feedback;
		this.suggestion = suggestion;
		this.issueStatus = issueStatus;
		this.issueDetails = issueDetails;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
    
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Short getCallType() {
        return callType;
    }
    public void setCallType(Short callType) {
    	this.callType = callType;
    }
    public String getSaleDept() {
        return saleDept;
    }
    public void setSaleDept(String saleDept) {
    	this.saleDept = saleDept;
    }
    public String getSaleAccount() {
        return saleAccount;
    }
    public void setSaleAccount(String saleAccount) {
    	this.saleAccount = saleAccount;
    }
    public String getSaleName() {
        return saleName;
    }
    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }
    public Integer getCid() {
        return cid;
    }
    public void setCid(Integer cid) {
        this.cid = cid;
    }
    public Short getStarOld() {
        return starOld;
    }
    public void setStarOld(Short starOld) {
    	this.starOld = starOld;
    }
    public Short getStar() {
        return star;
    }
    public void setStar(Short star) {
        this.star = star;
    }
    public Date getGmtNextContact() {
        return gmtNextContact;
    }
    public void setGmtNextContact(Date gmtNextContact) {
        this.gmtNextContact = gmtNextContact;
    }
    public Short getNextContact() {
        return nextContact;
    }
    public void setNextContact(Short nextContact) {
    	this.nextContact = nextContact;
    }
    public Short getSituation() {
        return situation;
    }
    public void setSituation(Short situation) {
        this.situation = situation;
    }
    public Short getOperation() {
        return operation;
    }
    public void setOperation(Short operation) {
        this.operation = operation;
    }
    public String getOperationDetails() {
        return operationDetails;
    }
    public void setOperationDetails(String operationDetails) {
    	this.operationDetails = operationDetails;
    }
    public Short getTransaction() {
        return transaction;
    }
    public void setTransaction(Short transaction) {
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
    public Short getIssueStatus() {
        return issueStatus;
    }
    public void setIssueStatus(Short issueStatus) {
    	this.issueStatus = issueStatus;
    }
    public String getIssueDetails() {
        return issueDetails;
    }
    public void setIssueDetails(String issueDetails) {
    	this.issueDetails = issueDetails;
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
	public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public String getGmtNextContactStr() {
		return gmtNextContactStr;
	}

	public void setGmtNextContactStr(String gmtNextContactStr) {
		this.gmtNextContactStr = gmtNextContactStr;
	}

	public void setSource(Short source) {
		this.source = source;
	}

	public Short getSource() {
		return source;
	}

	public void setMaturity(Short maturity) {
		this.maturity = maturity;
	}

	public Short getMaturity() {
		return maturity;
	}

	public void setPromote(Short promote) {
		this.promote = promote;
	}

	public Short getPromote() {
		return promote;
	}
	
	public Short getKp() {
		return kp;
	}

	public void setKp(Short kp) {
		this.kp = kp;
	}

	public Short getContactType() {
		return contactType;
	}

	public void setContactType(Short contactType) {
		this.contactType = contactType;
	}
    
}