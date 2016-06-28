/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class MemberApplyDO implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//Fields
	private Integer id;					//编号
	private String username;			//用户名
	private String membershipCode;		//申请类型
	private String tel;					//固定电话
	private String mobile;				//移动电话
	private String email;				//邮箱地址
	private String processStatus;		//处理状态
	private String processPerson;		//处理人
	private Date gmtCreated;			//申请时间
	private Date gmtModified;			//处理时间
	private String remark;				//备注
	private Integer companyContactsId;

	// Constructors

	/** default constructor */
	public MemberApplyDO() {
	}

	public MemberApplyDO(Integer id, String username, String membershipCode, String tel,
			String mobile, String email, String processStatus, String processPerson,
			Date gmtCreated, Date gmtModified, String remark,Integer companyContactsId){
		this.id = id;
		this.username = username;
		this.membershipCode = membershipCode;
		this.tel = tel;
		this.mobile = mobile;
		this.email = email;
		this.processPerson = processPerson;
		this.processStatus = processStatus;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.remark = remark;
		this.companyContactsId = companyContactsId;
	}



	// Property accessors

	/**
	 * @return the companyContactsId
	 */
	public Integer getCompanyContactsId() {
		return companyContactsId;
	}

	/**
	 * @param companyContactsId the companyContactsId to set
	 */
	public void setCompanyContactsId(Integer companyContactsId) {
		this.companyContactsId = companyContactsId;
	}

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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the processStatus
	 */
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return the processPerson
	 */
	public String getProcessPerson() {
		return processPerson;
	}

	/**
	 * @param processPerson the processPerson to set
	 */
	public void setProcessPerson(String processPerson) {
		this.processPerson = processPerson;
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

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
