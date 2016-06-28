package com.ast.ast1949.domain.sample;

import java.util.Date;

/**
 * 积分收入表
 * 
 * @author asto
 * 
 */
public class WeixinScore {
	private Integer id;

	private String account;

	private String rulesCode;

	private Integer score;

	private Date gmtCreated;

	private Date validity;

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
		this.account = account == null ? null : account.trim();
	}

	public String getRulesCode() {
		return rulesCode;
	}

	public void setRulesCode(String rulesCode) {
		this.rulesCode = rulesCode == null ? null : rulesCode.trim();
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}
}