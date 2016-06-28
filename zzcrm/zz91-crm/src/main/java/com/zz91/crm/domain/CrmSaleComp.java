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
public class CrmSaleComp implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private Short status;
    private Short saleType;
    private String saleDept;
    private String saleAccount;
    private String saleName;
    private Short companyType;
    private Integer logId;
    private Short autoBlock;
    private Date gmtBlock;
    private Date gmtContact;
    private Date gmtNextContact;
    private Integer contactCount;
    private Integer contactAbleCount;
    private Integer contactDisableCount;
    private Short disableStatus;
    private Short successStatus;
    private Integer dragOrderCount;
    private Integer destroyOrderCount;
    private Date gmtCreated;
    private Date gmtModified;

    
    public CrmSaleComp() {
		super();
	}
    
	public CrmSaleComp(Integer id, Integer cid, Short status, Short saleType,
			String saleDept, String saleAccount, String saleName,
			Short companyType, Integer logId, Short autoBlock, Date gmtBlock,
			Date gmtContact, Date gmtNextContact, Integer contactCount,
			Integer contactAbleCount, Integer contactDisableCount,
			Short disableStatus, Short successStatus,Integer dragOrderCount,Integer destroyOrderCount,
			Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.status = status;
		this.saleType = saleType;
		this.saleDept = saleDept;
		this.saleAccount = saleAccount;
		this.saleName = saleName;
		this.companyType = companyType;
		this.logId = logId;
		this.autoBlock = autoBlock;
		this.gmtBlock = gmtBlock;
		this.gmtContact = gmtContact;
		this.gmtNextContact = gmtNextContact;
		this.contactCount = contactCount;
		this.contactAbleCount = contactAbleCount;
		this.contactDisableCount = contactDisableCount;
		this.disableStatus = disableStatus;
		this.successStatus = successStatus;
		this.dragOrderCount = dragOrderCount;
		this.destroyOrderCount = destroyOrderCount;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCid() {
        return cid;
    }
    public void setCid(Integer cid) {
        this.cid = cid;
    }
    public Short getStatus() {
        return status;
    }
    public void setStatus(Short status) {
        this.status = status;
    }
    public Short getSaleType() {
        return saleType;
    }
    public void setSaleType(Short saleType) {
    	this.saleType = saleType;
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
    public Short getCompanyType() {
        return companyType;
    }
    public void setCompanyType(Short companyType) {
    	this.companyType = companyType;
    }
    public Integer getLogId() {
        return logId;
    }
    public void setLogId(Integer logId) {
    	this.logId = logId;
    }
    public Short getAutoBlock() {
        return autoBlock;
    }
    public void setAutoBlock(Short autoBlock) {
    	this.autoBlock = autoBlock;
    }
    public Date getGmtBlock() {
        return gmtBlock;
    }
    public void setGmtBlock(Date gmtBlock) {
    	this.gmtBlock = gmtBlock;
    }
    public Date getGmtContact() {
        return gmtContact;
    }
    public void setGmtContact(Date gmtContact) {
    	this.gmtContact = gmtContact;
    }
    public Date getGmtNextContact() {
        return gmtNextContact;
    }
    public void setGmtNextContact(Date gmtNextContact) {
    	this.gmtNextContact = gmtNextContact;
    }
    public Integer getContactCount() {
        return contactCount;
    }
    public void setContactCount(Integer contactCount) {
    	this.contactCount = contactCount;
    }
    public Integer getContactAbleCount() {
        return contactAbleCount;
    }
    public void setContactAbleCount(Integer contactAbleCount) {
    	this.contactAbleCount = contactAbleCount;
    }
    public Integer getContactDisableCount() {
        return contactDisableCount;
    }
    public void setContactDisableCount(Integer contactDisableCount) {
    	this.contactDisableCount = contactDisableCount;
    }
    public Short getDisableStatus() {
        return disableStatus;
    }
    public void setDisableStatus(Short disableStatus) {
    	this.disableStatus = disableStatus;
    }
    public Short getSuccessStatus() {
        return successStatus;
    }
    public void setSuccessStatus(Short successStatus) {
    	this.successStatus = successStatus;
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

	public void setDestroyOrderCount(Integer destroyOrderCount) {
		this.destroyOrderCount = destroyOrderCount;
	}

	public Integer getDestroyOrderCount() {
		return destroyOrderCount;
	}

	public void setDragOrderCount(Integer dragOrderCount) {
		this.dragOrderCount = dragOrderCount;
	}

	public Integer getDragOrderCount() {
		return dragOrderCount;
	}
}