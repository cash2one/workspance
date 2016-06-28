package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CompanyLottery extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8595589756795445957L;
	
	private Integer id;
	private Integer companyId;  //'抽奖公司id' ,
	private String status; //'抽奖状态\\n0：关闭\\n1：激活 2：已抽奖' ,
	private String lottery; //  抽到的奖品
	private String lotteryCode; //'奖品编号以\\\",\\\"分割' ,
	private String companyAccount; // '公司帐号' ,
	private String csAccount;  //'cs开通人员帐号' ,
	private Date  gmtCreated;
	private Date  gmtModified;
	public Integer getId() {
		return id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public String getStatus() {
		return status;
	}
	public String getLottery() {
		return lottery;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	public String getCompanyAccount() {
		return companyAccount;
	}
	public String getCsAccount() {
		return csAccount;
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
	public void setStatus(String status) {
		this.status = status;
	}
	public void setLottery(String lottery) {
		this.lottery = lottery;
	}
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}
