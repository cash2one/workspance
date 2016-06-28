/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-27
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * 运营人员个人客户库
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class CompanyManagerUserDO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;					//编号
	private Integer companyId;			//公司ID
	private Integer adminUserId;		//内容部人员ID
	private Date gmtCreated;			//创建时间
	private Date gmtModified;			//修改时间
	
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
	 * @return the adminUserId
	 */
	public Integer getAdminUserId() {
		return adminUserId;
	}
	/**
	 * @param adminUserId the adminUserId to set
	 */
	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
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
