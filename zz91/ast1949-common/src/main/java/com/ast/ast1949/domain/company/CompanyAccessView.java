package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 
 * @author kongsj '公司帐号微信绑定公司联系方式察看日志表'
 */
public class CompanyAccessView extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8819021848571315626L;
	private Integer id;
	private Integer companyId;
	private Integer targetId; // '查看联系方式的公司id' ,
	private String companyAccount;
	private Date gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;

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

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public Date getGmtTarget() {
		return gmtTarget;
	}

	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
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