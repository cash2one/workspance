package com.ast.feiliao91.domain.goods;

import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;

public class GoodsDto {
	private Goods goods;// 产品信息
	private CompanyInfo companyInfo;// 公司信息
	private CompanyAccount account;// 帐号信息
	private Integer tradeNum;// 成交数
	private Integer judgeNum;// 评价数
	private String area; // 货物所在地
	private String location;// 公司所在地区
	private Integer successNum; // 成交数
	private Integer bzjFlag;// 保证金服务
	private Integer sevenDayFlag; // 7天无理由退货服务
	private String picAddress;
	private String detail; // 详细
	private String useLabel; // 用途级别
	private String processLabel;// 加工级别
	private String charaLabel;// 特性级别
	private String goodsCategoryLabel;// 产品类别label
	private String cnDate; // 例如： 7天前，1周前

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public CompanyAccount getAccount() {
		return account;
	}

	public void setAccount(CompanyAccount account) {
		this.account = account;
	}

	public Integer getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(Integer tradeNum) {
		this.tradeNum = tradeNum;
	}

	public Integer getJudgeNum() {
		return judgeNum;
	}

	public void setJudgeNum(Integer judgeNum) {
		this.judgeNum = judgeNum;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
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

	public String getPicAddress() {
		return picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUseLabel() {
		return useLabel;
	}

	public void setUseLabel(String useLabel) {
		this.useLabel = useLabel;
	}

	public String getProcessLabel() {
		return processLabel;
	}

	public void setProcessLabel(String processLabel) {
		this.processLabel = processLabel;
	}

	public String getCharaLabel() {
		return charaLabel;
	}

	public void setCharaLabel(String charaLabel) {
		this.charaLabel = charaLabel;
	}

	public String getGoodsCategoryLabel() {
		return goodsCategoryLabel;
	}

	public void setGoodsCategoryLabel(String goodsCategoryLabel) {
		this.goodsCategoryLabel = goodsCategoryLabel;
	}

	public String getCnDate() {
		return cnDate;
	}

	public void setCnDate(String cnDate) {
		this.cnDate = cnDate;
	}

}
