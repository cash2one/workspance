package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-5-16
 */
public class CrmOutLog extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2314321325397183823L;
	private Integer id;
	private Integer companyId; // INT(11) NOT NULL COMMENT '公司id' ,
	private String operator; // VARCHAR(200) NOT NULL COMMENT '操作人帐号\\n0：表示系统' ,
	private String status; // CHAR(1) NULL DEFAULT '0' COMMENT '0:掉公海\\n1:捞公海' ,
	private Date gmtCreated; // DATETIME NULL ,
	private Date gmtModified; // DATETIME NULL ,
	private String oldCsAccount;//该客户上一个客户;

	public String getOldCsAccount() {
		return oldCsAccount;
	}

	public void setOldCsAccount(String oldCsAccount) {
		this.oldCsAccount = oldCsAccount;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public String getOperator() {
		return operator;
	}

	public String getStatus() {
		return status;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
