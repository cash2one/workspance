/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-3
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * 用户今日查看（联系信息）次数
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class UserTodayViewsDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;				//编号
	private String account;			//帐号
	private Integer times;			//次数
	private Date gmtCreated;		//查看时间
	
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
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the times
	 */
	public Integer getTimes() {
		return times;
	}
	/**
	 * @param times the times to set
	 */
	public void setTimes(Integer times) {
		this.times = times;
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
}
