/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010 上午11:01:27
 */
package com.ast.ast1949.dto.company;

/**
 * 后台用户列表里用
 * @author Ryan
 *
 */
public class CompanyListInAdminDTO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;						//主键id
	private String name;				//客户名称
	private String account;				//登录帐号ID
	private String membershipCode;		//客户会员类型编号
	private String tel;					//电话
	private String mobile;				//手机	
	private String regtime;				//注册时间
	private String areaCode;			//地区编号
	private String state;				//状态
	private Integer numLogin;			//用户登入次数
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @param membershipCode the membershipCode to set
	 */
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}
	/**
	 * @return the membershipCode
	 */
	public String getMembershipCode() {
		return membershipCode;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getNumLogin() {
		return numLogin;
	}
	public void setNumLogin(Integer numLogin) {
		this.numLogin = numLogin;
	}
}
