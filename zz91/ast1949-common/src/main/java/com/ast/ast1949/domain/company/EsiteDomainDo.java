/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23 by liulei.
 */
package com.ast.ast1949.domain.company;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liulei
 *
 */
public class EsiteDomainDo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer companyId;				//公司ID
	private String account;					//公司账号名
	private String name;					//名称
	private String ip;						//IP
	private Date gmtCreated;         	 	//创建时间
	private Date gmtModified;        		//修改时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
