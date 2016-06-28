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
public class CrmCompanyProfile extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;
	private String saleDetails;
	private String buyDetails;
	private String introduction;
	private String operatorName;
	private String operatorPhone;
	private String operatorTel;
	private String address;
	private String creditCard;
	private String creditLicense;
	private String creditTax;
	private String tag1;
	private String tag2;
	private String tag3;
	private String tag4;
	private String tag5;
	private String tag6;
	private String tag7;
	private String tag8;
	private String tag9;
	private String tag10;
	private Date gmtCreated;
	private Date gmtModified;
	
	public CrmCompanyProfile() {
		super();
	}

	public CrmCompanyProfile(Integer companyId, String saleDetails,
			String buyDetails, String introduction, String operatorName,
			String operatorPhone, String operatorTel, String address,
			String creditCard, String creditLicense, String creditTax,
			String tag1, String tag2, String tag3, String tag4, String tag5,
			String tag6, String tag7, String tag8, String tag9, String tag10,
			Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.saleDetails = saleDetails;
		this.buyDetails = buyDetails;
		this.introduction = introduction;
		this.operatorName = operatorName;
		this.operatorPhone = operatorPhone;
		this.operatorTel = operatorTel;
		this.address = address;
		this.creditCard = creditCard;
		this.creditLicense = creditLicense;
		this.creditTax = creditTax;
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
		this.tag4 = tag4;
		this.tag5 = tag5;
		this.tag6 = tag6;
		this.tag7 = tag7;
		this.tag8 = tag8;
		this.tag9 = tag9;
		this.tag10 = tag10;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getSaleDetails() {
		return saleDetails;
	}
	public void setSaleDetails(String saleDetails) {
		this.saleDetails = saleDetails;
	}
	public String getBuyDetails() {
		return buyDetails;
	}
	public void setBuyDetails(String buyDetails) {
		this.buyDetails = buyDetails;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorPhone() {
		return operatorPhone;
	}
	public void setOperatorPhone(String operatorPhone) {
		this.operatorPhone = operatorPhone;
	}
	public String getOperatorTel() {
		return operatorTel;
	}
	public void setOperatorTel(String operatorTel) {
		this.operatorTel = operatorTel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getCreditLicense() {
		return creditLicense;
	}
	public void setCreditLicense(String creditLicense) {
		this.creditLicense = creditLicense;
	}
	public String getCreditTax() {
		return creditTax;
	}
	public void setCreditTax(String creditTax) {
		this.creditTax = creditTax;
	}
	public String getTag1() {
		return tag1;
	}
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	public String getTag3() {
		return tag3;
	}
	public void setTag3(String tag3) {
		this.tag3 = tag3;
	}
	public String getTag4() {
		return tag4;
	}
	public void setTag4(String tag4) {
		this.tag4 = tag4;
	}
	public String getTag5() {
		return tag5;
	}
	public void setTag5(String tag5) {
		this.tag5 = tag5;
	}
	public String getTag6() {
		return tag6;
	}
	public void setTag6(String tag6) {
		this.tag6 = tag6;
	}
	public String getTag7() {
		return tag7;
	}
	public void setTag7(String tag7) {
		this.tag7 = tag7;
	}
	public String getTag8() {
		return tag8;
	}
	public void setTag8(String tag8) {
		this.tag8 = tag8;
	}
	public String getTag9() {
		return tag9;
	}
	public void setTag9(String tag9) {
		this.tag9 = tag9;
	}
	public String getTag10() {
		return tag10;
	}
	public void setTag10(String tag10) {
		this.tag10 = tag10;
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
