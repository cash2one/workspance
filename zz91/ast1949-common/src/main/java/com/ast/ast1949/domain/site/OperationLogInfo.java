package com.ast.ast1949.domain.site;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class OperationLogInfo extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String level;// level char(6) '日志级别' ,
	private String ip;// ip VARCHAR(16) '操作IP' ,
	private Integer companyId;// company_id INT(20) 公司ID
	private String account;//account varchar(50) 操作帐户
	private String categoryCode;// category_code VARCHAR(20) '操作的动作类别code' ,
	private String information;// information text '操作信息内容',
	private Date gmtOperationTime;// gmt_operation_time DATETIME '操作时间' ,
	private String description;// description VARCHAR(100) '操作说明',
	private Date gmtCreated;// gmt_created DATETIME '创建时间' ,

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Date getGmtOperationTime() {
		return gmtOperationTime;
	}

	public void setGmtOperationTime(Date gmtOperationTime) {
		this.gmtOperationTime = gmtOperationTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
}
