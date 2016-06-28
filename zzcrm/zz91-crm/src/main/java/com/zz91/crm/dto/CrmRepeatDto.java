package com.zz91.crm.dto;

import java.io.Serializable;
import com.zz91.crm.domain.CrmRepeat;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-15 
 */
public class CrmRepeatDto extends CrmRepeat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String oldName;//原销售人
	private String cname;//公司名称
	private String name;//联系人
	private String mobile;//手机
	private String phone;//电话
	private String address;//地址
	private String email;//邮箱
	private String contact;//其他联系方式
	
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getOldName() {
		return oldName;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCname() {
		return cname;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobile() {
		return mobile;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContact() {
		return contact;
	}
}
