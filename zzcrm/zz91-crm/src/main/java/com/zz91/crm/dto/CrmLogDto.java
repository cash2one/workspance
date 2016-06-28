package com.zz91.crm.dto;

import java.io.Serializable;
import java.util.Date;

import com.zz91.crm.domain.CrmLog;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-3 
 */
public class CrmLogDto extends CrmLog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer compId;
	private String cname;
	private String email;
	private Date gmtLogin;
	private Integer loginCount;
	private Date gmtRegister;
	
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCname() {
		return cname;
	}
	public Integer getCompId() {
		return compId;
	}
	public void setCompId(Integer compId) {
		this.compId = compId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getGmtLogin() {
		return gmtLogin;
	}
	public void setGmtLogin(Date gmtLogin) {
		this.gmtLogin = gmtLogin;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Date getGmtRegister() {
		return gmtRegister;
	}
	public void setGmtRegister(Date gmtRegister) {
		this.gmtRegister = gmtRegister;
	}
	
}
