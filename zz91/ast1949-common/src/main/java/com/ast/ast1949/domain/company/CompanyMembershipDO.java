/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class CompanyMembershipDO extends DomainSupport implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;//公司ID
	private String membershipTypeCode;//会员类型
	private Date dateFrom;//开始时间
	private Date dateEnd;//结束时间
	private String checking;//审核
	private Date gmtCreated;//创建时间
	private Date gmtModified;//修改时间
	private String years;//年限（1 一年，2 两年）
	private String contractType;//签约类型
	private String serviceStatus;//服务状态
	private String url;//（二级）域名
	private Integer totalYear;//开通的总的再生通服务年限
	private Date gmtLastEnd;
	private String domain;
	
	public CompanyMembershipDO() {
		
	}
	
	/**
	 * @param companyId
	 * @param membershipTypeCode
	 * @param dateFrom
	 * @param dateEnd
	 * @param checking
	 * @param gmtCreated
	 * @param gmtModified
	 * @param years
	 * @param contractType
	 * @param serviceStatus
	 * @param url
	 * @param totalYear
	 * @param gmtLastEnd
	 * @param domain
	 */
	public CompanyMembershipDO(Integer companyId, String membershipTypeCode,
			Date dateFrom, Date dateEnd, String checking, Date gmtCreated,
			Date gmtModified, String years, String contractType,
			String serviceStatus, String url, Integer totalYear,
			Date gmtLastEnd, String domain) {
		super();
		this.companyId = companyId;
		this.membershipTypeCode = membershipTypeCode;
		this.dateFrom = dateFrom;
		this.dateEnd = dateEnd;
		this.checking = checking;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.years = years;
		this.contractType = contractType;
		this.serviceStatus = serviceStatus;
		this.url = url;
		this.totalYear = totalYear;
		this.gmtLastEnd = gmtLastEnd;
		this.domain = domain;
	}



	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the membershipTypeCode
	 */
	public String getMembershipTypeCode() {
		return membershipTypeCode;
	}
	/**
	 * @param membershipTypeCode the membershipTypeCode to set
	 */
	public void setMembershipTypeCode(String membershipTypeCode) {
		this.membershipTypeCode = membershipTypeCode;
	}
	/**
	 * @return the years
	 */
	public String getYears() {
		return years;
	}
	/**
	 * @param years the years to set
	 */
	public void setYears(String years) {
		this.years = years;
	}
	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}
	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}
	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}
	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	/**
	 * @return the checking
	 */
	public String getChecking() {
		return checking;
	}
	/**
	 * @param checking the checking to set
	 */
	public void setChecking(String checking) {
		this.checking = checking;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getTotalYear() {
		return totalYear;
	}
	public void setTotalYear(Integer totalYear) {
		this.totalYear = totalYear;
	}

	/**
	 * @return the gmtLastEnd
	 */
	public Date getGmtLastEnd() {
		return gmtLastEnd;
	}

	/**
	 * @param gmtLastEnd the gmtLastEnd to set
	 */
	public void setGmtLastEnd(Date gmtLastEnd) {
		this.gmtLastEnd = gmtLastEnd;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}
