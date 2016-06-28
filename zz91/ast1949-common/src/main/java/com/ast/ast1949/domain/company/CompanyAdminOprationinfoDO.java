/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-3
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class CompanyAdminOprationinfoDO implements java.io.Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;						//编号
	private Integer companyId;				//公司ID
	private String exhibitCode;			//展商客户类型
	private String exhibitRemark;			//展商客户备注
	private String highCompanyCode;		//高质客户归类（默认为0，表示不是高质量客户）
	private String hignCompanyRemark;		//高质客户备注
	private String relationCode;			//与本站关系归类
	private String businessSizeCode;		//客户生意规模情况
	private Date gmtCreated;				//创建时间
	private Date gmtModified;				//修改时间
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the exhibitCode
	 */
	public String getExhibitCode() {
		return exhibitCode;
	}
	/**
	 * @param exhibitCode the exhibitCode to set
	 */
	public void setExhibitCode(String exhibitCode) {
		this.exhibitCode = exhibitCode;
	}
	/**
	 * @return the exhibitRemark
	 */
	public String getExhibitRemark() {
		return exhibitRemark;
	}
	/**
	 * @param exhibitRemark the exhibitRemark to set
	 */
	public void setExhibitRemark(String exhibitRemark) {
		this.exhibitRemark = exhibitRemark;
	}
	/**
	 * @return the highCompanyCode
	 */
	public String getHighCompanyCode() {
		return highCompanyCode;
	}
	/**
	 * @param highCompanyCode the highCompanyCode to set
	 */
	public void setHighCompanyCode(String highCompanyCode) {
		this.highCompanyCode = highCompanyCode;
	}
	/**
	 * @return the hignCompanyRemark
	 */
	public String getHignCompanyRemark() {
		return hignCompanyRemark;
	}
	/**
	 * @param hignCompanyRemark the hignCompanyRemark to set
	 */
	public void setHignCompanyRemark(String hignCompanyRemark) {
		this.hignCompanyRemark = hignCompanyRemark;
	}
	/**
	 * @return the relationCode
	 */
	public String getRelationCode() {
		return relationCode;
	}
	/**
	 * @param relationCode the relationCode to set
	 */
	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}
	/**
	 * @return the businessSizeCode
	 */
	public String getBusinessSizeCode() {
		return businessSizeCode;
	}
	/**
	 * @param businessSizeCode the businessSizeCode to set
	 */
	public void setBusinessSizeCode(String businessSizeCode) {
		this.businessSizeCode = businessSizeCode;
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
}
