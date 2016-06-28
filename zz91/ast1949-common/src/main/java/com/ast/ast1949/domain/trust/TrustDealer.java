/**
 * @author kongsj
 * @date 2015年5月9日
 * 
 */
package com.ast.ast1949.domain.trust;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TrustDealer extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 532421488878656083L;
	private Integer id;
	private String name;
	private String tel;
	private String qq;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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
