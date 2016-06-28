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
public class CrmContact implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private String email;
    private String name;
    private Short sex;
    private String mobile;
    private String phoneCountry;
    private String phoneArea;
    private String phone;
    private String faxCountry;
    private String faxArea;
    private String fax;
    private String contact;
    private String position;
    private Short isKey;
    private String remark;
    private String saleAccount;
    private String saleName;
    private String saleDept;
    private Date gmtCreated;
    private Date gmtModified;

    
    public CrmContact() {
		super();
	}
    
	public CrmContact(Integer id, Integer cid, String email, String name,
			Short sex, String mobile, String phoneCountry, String phoneArea,
			String phone, String faxCountry, String faxArea, String fax,
			String contact, String position, Short isKey, String remark,
			String saleAccount, String saleName, String saleDept,
			Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.email = email;
		this.name = name;
		this.sex = sex;
		this.mobile = mobile;
		this.phoneCountry = phoneCountry;
		this.phoneArea = phoneArea;
		this.phone = phone;
		this.faxCountry = faxCountry;
		this.faxArea = faxArea;
		this.fax = fax;
		this.contact = contact;
		this.position = position;
		this.isKey = isKey;
		this.remark = remark;
		this.saleAccount = saleAccount;
		this.saleName = saleName;
		this.saleDept = saleDept;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Short getSex() {
        return sex;
    }
    public void setSex(Short sex) {
        this.sex = sex;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPhoneCountry() {
        return phoneCountry;
    }
    public void setPhoneCountry(String phoneCountry) {
    	this.phoneCountry = phoneCountry;
    }
    public String getPhoneArea() {
        return phoneArea;
    }
    public void setPhoneArea(String phoneArea) {
    	this.phoneArea = phoneArea;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getFaxCountry() {
        return faxCountry;
    }
    public void setFaxCountry(String faxCountry) {
    	this.faxCountry = faxCountry;
    }
    public String getFaxArea() {
        return faxArea;
    }
    public void setFaxArea(String faxArea) {
    	this.faxArea = faxArea;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public Short getIsKey() {
        return isKey;
    }
    public void setIsKey(Short isKey) {
    	this.isKey = isKey;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
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
    public String getSaleDept() {
        return saleDept;
    }
    public void setSaleDept(String saleDept) {
    	this.saleDept = saleDept;
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
}