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
public class CrmCompanySvr extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;
	private String crmServiceCode;
	private String applyGroup;
	private String signedType;
	private Date gmtPreStart;
	private Date gmtPreEnd;
	private Date gmtSigned;
	private Date gmtStart;
	private Date gmtEnd;
	private String applyStatus;
	private Integer category;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
	private String membershipCode;
	private Integer zstYear;
	
	public CrmCompanySvr() {
		super();
	}
	
	public CrmCompanySvr(Integer companyId, String crmServiceCode,
			String applyGroup, String signedType, Date gmtPreStart,
			Date gmtPreEnd, Date gmtSigned, Date gmtStart, Date gmtEnd,
			String applyStatus, Integer category, String remark,
			Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.crmServiceCode = crmServiceCode;
		this.applyGroup = applyGroup;
		this.signedType = signedType;
		this.gmtPreStart = gmtPreStart;
		this.gmtPreEnd = gmtPreEnd;
		this.gmtSigned = gmtSigned;
		this.gmtStart = gmtStart;
		this.gmtEnd = gmtEnd;
		this.applyStatus = applyStatus;
		this.category = category;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCrmServiceCode() {
		return crmServiceCode;
	}
	public void setCrmServiceCode(String crmServiceCode) {
		this.crmServiceCode = crmServiceCode;
	}
	public String getApplyGroup() {
		return applyGroup;
	}
	public void setApplyGroup(String applyGroup) {
		this.applyGroup = applyGroup;
	}
	public String getSignedType() {
		return signedType;
	}
	public void setSignedType(String signedType) {
		this.signedType = signedType;
	}
	public Date getGmtPreStart() {
		return gmtPreStart;
	}
	public void setGmtPreStart(Date gmtPreStart) {
		this.gmtPreStart = gmtPreStart;
	}
	public Date getGmtPreEnd() {
		return gmtPreEnd;
	}
	public void setGmtPreEnd(Date gmtPreEnd) {
		this.gmtPreEnd = gmtPreEnd;
	}
	public Date getGmtSigned() {
		return gmtSigned;
	}
	public void setGmtSigned(Date gmtSigned) {
		this.gmtSigned = gmtSigned;
	}
	public Date getGmtStart() {
		return gmtStart;
	}
	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}
	public Date getGmtEnd() {
		return gmtEnd;
	}
	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
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

	/**
	 * @return the membershipCode
	 */
	public String getMembershipCode() {
		return membershipCode;
	}

	/**
	 * @param membershipCode the membershipCode to set
	 */
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	/**
	 * @return the zstYear
	 */
	public Integer getZstYear() {
		return zstYear;
	}

	/**
	 * @param zstYear the zstYear to set
	 */
	public void setZstYear(Integer zstYear) {
		this.zstYear = zstYear;
	}
	
}
