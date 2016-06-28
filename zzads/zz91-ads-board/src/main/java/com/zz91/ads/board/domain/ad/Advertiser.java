/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-6.
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class Advertiser implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;//广告主名称，可以是公司名称也可以是个人名称\n广告主可以是自己（阿思拓），也可以是再生能客户，或者其他网站或个人
	private String contact;//联系人
	private String phone;//联系电话，可以是多个
	private String email;//联系email，可以是多个
	private String remark;//备注
	private Integer category;//广告主类别 0：阿思拓；1：再生通用户；2：其他
	private String deleted;//标记删除(Y/N) N：正常（默认）；Y：已删除
	private Date gmtCreated;
	private Date gmtModified;
	
	public Advertiser() {
		super();
	}
	
	public Advertiser(Integer id, String name, String contact, String phone, String email,
			String remark, Integer category, String deleted, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.phone = phone;
		this.email = email;
		this.remark = remark;
		this.category = category;
		this.deleted = deleted;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
	/**
	 * @return the deleted
	 */
	public String getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
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
