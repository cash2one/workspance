package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.DomainSupport;

public class CompanyAccountSearchDto extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8004647402179412419L;
	
	private String isPerson;
	private String regFrom;
	private String regTo;
	// TODO 命名需要重新调整  to 罗刚
	//自定义的变量
    private String numLoginFrom;//登录次数始
    private String numLoginTo;//登录次数终
    private String companyInfo;//公司信息完整度标志  1:完整（全） 0:不全
    private String yangPublish;//样品发布  1:有样品发布  0:无样品发布
    private String order;//样品订单  1:有样品成交  0:无样品成交
    private String zhifubao;//支付宝 1:有支付宝绑定  0:无支付宝绑定
    private String weixin;//微信		1:有微信绑定  0:无微信绑定
    //crmcode 会员服务类型 0:过期高会 1:银牌品牌通 2:再生通 3:简版再生通 4:来电宝 5:百度优化 6:普通会员 7:金牌品牌通 8:钻石品牌通
    private String crmCode;
    //publish 发布情况 1:有发布供求 2:无发布供求 3:有发布报价  4:有发布贴子 5:有回贴 6:有发布询价 7:无发布询价 8:无发布报价
    private String publish; 
    private String receive;//接收情况 1:有收到回贴
    private String crmServiceCode;//会员服务类型编码
    private String inquriyCountFlag;//标志是否需要用到inquiryCount表
    private String bbsreplyFlag;//标志是否需要用到bbspostreply表
    private String companypriceFlag;//标志是否需要用到companyprice表
    private String bbspostFlag;//标志是否需要用到bbspost表
    private String productsFlag;//标志是否需要用到products表
    private String loginFrom; //登录时间始
	private String loginTo; //登录时间终
	private String keyword;
	private String categoryGarden;
	public String getCategoryGarden() {
		return categoryGarden;
	}

	public void setCategoryGarden(String categoryGarden) {
		this.categoryGarden = categoryGarden;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLoginFrom() {
		return loginFrom;
	}

	public void setLoginFrom(String loginFrom) {
		this.loginFrom = loginFrom;
	}

	public String getLoginTo() {
		return loginTo;
	}

	public void setLoginTo(String loginTo) {
		this.loginTo = loginTo;
	}

	public String getInquriyCountFlag() {
		return inquriyCountFlag;
	}

	public void setInquriyCountFlag(String inquriyCountFlag) {
		this.inquriyCountFlag = inquriyCountFlag;
	}

	public String getBbsreplyFlag() {
		return bbsreplyFlag;
	}

	public void setBbsreplyFlag(String bbsreplyFlag) {
		this.bbsreplyFlag = bbsreplyFlag;
	}

	public String getCompanypriceFlag() {
		return companypriceFlag;
	}

	public void setCompanypriceFlag(String companypriceFlag) {
		this.companypriceFlag = companypriceFlag;
	}

	public String getBbspostFlag() {
		return bbspostFlag;
	}

	public void setBbspostFlag(String bbspostFlag) {
		this.bbspostFlag = bbspostFlag;
	}

	public String getProductsFlag() {
		return productsFlag;
	}

	public void setProductsFlag(String productsFlag) {
		this.productsFlag = productsFlag;
	}

	public String getNumLoginFrom() {
		return numLoginFrom;
	}

	public void setNumLoginFrom(String numLoginFrom) {
		this.numLoginFrom = numLoginFrom;
	}

	public String getNumLoginTo() {
		return numLoginTo;
	}

	public void setNumLoginTo(String numLoginTo) {
		this.numLoginTo = numLoginTo;
	}

	public String getCrmServiceCode() {
		return crmServiceCode;
	}

	public void setCrmServiceCode(String crmServiceCode) {
		this.crmServiceCode = crmServiceCode;
	}
	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getYangPublish() {
		return yangPublish;
	}

	public void setYangPublish(String yangPublish) {
		this.yangPublish = yangPublish;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getZhifubao() {
		return zhifubao;
	}

	public void setZhifubao(String zhifubao) {
		this.zhifubao = zhifubao;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getCrmCode() {
		return crmCode;
	}

	public void setCrmCode(String crmCode) {
		this.crmCode = crmCode;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getIsPerson() {
		return isPerson;
	}

	public void setIsPerson(String isPerson) {
		this.isPerson = isPerson;
	}

	public String getRegFrom() {
		return regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	public String getRegTo() {
		return regTo;
	}

	public void setRegTo(String regTo) {
		this.regTo = regTo;
	}

}
