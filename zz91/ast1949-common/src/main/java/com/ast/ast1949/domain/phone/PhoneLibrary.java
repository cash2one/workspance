/**
 * @author kongsj
 * @date 2014年9月3日
 * 
 */
package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PhoneLibrary extends DomainSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3347118980170188515L;
	private Integer id;
	private String tel;
	private String called;
	private Integer status;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCalled() {
		return called;
	}
	public void setCalled(String called) {
		this.called = called;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
