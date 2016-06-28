package com.ast.ast1949.domain.trust;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TrustCompanyLog extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2545675009136252820L;
	private Integer id; // ` INT NOT NULL AUTO_INCREMENT ,
	private String content; // ` VARCHAR(500) NULL COMMENT '小计内容' ,
	private Integer companyId; // ` VARCHAR(45) NULL COMMENT '公司id' ,
	private Date gmtCreated; // ` VARCHAR(45) NULL ,
	private Date gmtModified; // ` VARCHAR(45) NULL ,
	private Integer star; // ` INT NULL COMMENT '星级' ,
	private Date gmtNextVisit; // ` DATETIME NULL ,
	private Integer situation; // ` INT NULL COMMENT '是否有效:0无效;1有效' ,
	private Integer trustType; // ` INT NULL COMMENT '公司类型:0贸易商;1生产商;2供应商' ,
	private String trustAccount;

	private String companyName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Date getGmtNextVisit() {
		return gmtNextVisit;
	}

	public void setGmtNextVisit(Date gmtNextVisit) {
		this.gmtNextVisit = gmtNextVisit;
	}

	public Integer getSituation() {
		return situation;
	}

	public void setSituation(Integer situation) {
		this.situation = situation;
	}

	public Integer getTrustType() {
		return trustType;
	}

	public void setTrustType(Integer trustType) {
		this.trustType = trustType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTrustAccount() {
		return trustAccount;
	}

	public void setTrustAccount(String trustAccount) {
		this.trustAccount = trustAccount;
	}

}
