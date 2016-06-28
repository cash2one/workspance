package com.ast.feiliao91.dto.company;

import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;

public class CompanyDto {
	private CompanyInfo companyInfo;
	private CompanyAccount companyAccount;
	private String address;
	private Integer tradeNum;
	private Integer totalSuccessNum; // 总成交数
	private String degreeSatisfaction; // 满意度
	private String logo;
	private Integer bzjFlag;// 保证金服务
	private Integer sevenDayFlag; // 7天无理由退货服务
	private String companyName;//认证公司名称
	private String oneName;//认证个体名称
	private String industry;//主营行业(后台默认设置为废塑料)
	private String areaLabel;
	
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public CompanyAccount getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(CompanyAccount companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(Integer tradeNum) {
		this.tradeNum = tradeNum;
	}

	public Integer getTotalSuccessNum() {
		return totalSuccessNum;
	}

	public void setTotalSuccessNum(Integer totalSuccessNum) {
		this.totalSuccessNum = totalSuccessNum;
	}

	public String getDegreeSatisfaction() {
		return degreeSatisfaction;
	}

	public void setDegreeSatisfaction(String degreeSatisfaction) {
		this.degreeSatisfaction = degreeSatisfaction;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getBzjFlag() {
		return bzjFlag;
	}

	public void setBzjFlag(Integer bzjFlag) {
		this.bzjFlag = bzjFlag;
	}

	public Integer getSevenDayFlag() {
		return sevenDayFlag;
	}

	public void setSevenDayFlag(Integer sevenDayFlag) {
		this.sevenDayFlag = sevenDayFlag;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOneName() {
		return oneName;
	}

	public void setOneName(String oneName) {
		this.oneName = oneName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getAreaLabel() {
		return areaLabel;
	}

	public void setAreaLabel(String areaLabel) {
		this.areaLabel = areaLabel;
	}
}
