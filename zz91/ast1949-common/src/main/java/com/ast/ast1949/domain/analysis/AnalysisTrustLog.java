package com.ast.ast1949.domain.analysis;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class AnalysisTrustLog extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2932513482727230917L;

	private Integer id; // int(11) NOT NULL AUTO_INCREMENT,
	private String account; // varchar(45) DEFAULT NULL COMMENT '服务人员帐号',
	private Date gmtTarget; // datetime DEFAULT NULL,
	private Date gmtCreated; // datetime DEFAULT NULL,
	private Date gmtModified; // datetime DEFAULT NULL,
	private Integer star1B; // int(11) DEFAULT NULL COMMENT '1星采购小计数',
	private Integer star1C; // int(11) DEFAULT NULL COMMENT '1星公司小计数',
	private Integer star2B; // int(11) DEFAULT NULL COMMENT '2星采购',
	private Integer star2C; // int(11) DEFAULT NULL COMMENT '2星公司',
	private Integer star3B; // int(11) DEFAULT NULL COMMENT '3星采购',
	private Integer star3C; // int(11) DEFAULT NULL COMMENT '3星公司',
	private Integer star4B; // int(11) DEFAULT NULL COMMENT '4星采购',
	private Integer star4C; // int(11) DEFAULT NULL COMMENT '4星公司',
	private Integer star5B; // int(11) DEFAULT NULL COMMENT '5星采购',
	private Integer star5C; // int(11) DEFAULT NULL COMMENT '5星公司',

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public Integer getStar1B() {
		return star1B;
	}

	public void setStar1B(Integer star1b) {
		star1B = star1b;
	}

	public Integer getStar1C() {
		return star1C;
	}

	public void setStar1C(Integer star1c) {
		star1C = star1c;
	}

	public Integer getStar2B() {
		return star2B;
	}

	public void setStar2B(Integer star2b) {
		star2B = star2b;
	}

	public Integer getStar2C() {
		return star2C;
	}

	public void setStar2C(Integer star2c) {
		star2C = star2c;
	}

	public Integer getStar3B() {
		return star3B;
	}

	public void setStar3B(Integer star3b) {
		star3B = star3b;
	}

	public Integer getStar3C() {
		return star3C;
	}

	public void setStar3C(Integer star3c) {
		star3C = star3c;
	}

	public Integer getStar4B() {
		return star4B;
	}

	public void setStar4B(Integer star4b) {
		star4B = star4b;
	}

	public Integer getStar4C() {
		return star4C;
	}

	public void setStar4C(Integer star4c) {
		star4C = star4c;
	}

	public Integer getStar5B() {
		return star5B;
	}

	public void setStar5B(Integer star5b) {
		star5B = star5b;
	}

	public Integer getStar5C() {
		return star5C;
	}

	public void setStar5C(Integer star5c) {
		star5C = star5c;
	}

}
