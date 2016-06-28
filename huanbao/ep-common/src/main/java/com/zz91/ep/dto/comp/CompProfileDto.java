/*
 * 文件名称：CompProfileDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.comp;

import java.io.Serializable;

import com.zz91.ep.domain.common.LogInfo;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：公司信息扩展结果。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class CompProfileDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private CompProfile compProfile;
	
	private CompAccount compAccount;

	private String mobile; //手机
	
	private String phoneCountry;
	
	private String phoneArea;
	
	private String phone;//固定电话
	
	private String faxCountry;
	
	private String faxArea;
	
	private String fax;//传真

	private String contacts;//联系人
	
	private String provinceName;//省份
	
	private String areaName;//地区
	
	private String memberName;//会员名称
	
	private String registerSource;//客户来源
	
	private String industryName;//行业
	
	private String memberYear;// 会员几年

	private String cssStyle;// css样式
	
	private LogInfo logInfo;//记录日志
	
	private String qq;
	
	private Short serviceType;   //服务类型
	
		
	public CompProfileDto(){
		
	}

	public CompProfile getCompProfile() {
		return compProfile;
	}

	public void setCompProfile(CompProfile compProfile) {
		this.compProfile = compProfile;
	}

	public CompAccount getCompAccount() {
		return compAccount;
	}

	public void setCompAccount(CompAccount compAccount) {
		this.compAccount = compAccount;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRegisterSource() {
		return registerSource;
	}

	public void setRegisterSource(String registerSource) {
		this.registerSource = registerSource;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getMemberYear() {
		return memberYear;
	}

	public void setMemberYear(String memberYear) {
		this.memberYear = memberYear;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getPhoneCountry() {
		return phoneCountry;
	}

	public void setPhoneCountry(String phoneCountry) {
		this.phoneCountry = phoneCountry;
	}

	public String getPhoneArea() {
		return phoneArea;
	}

	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}

	public String getFaxCountry() {
		return faxCountry;
	}

	public void setFaxCountry(String faxCountry) {
		this.faxCountry = faxCountry;
	}

	public String getFaxArea() {
		return faxArea;
	}

	public void setFaxArea(String faxArea) {
		this.faxArea = faxArea;
	}

	public LogInfo getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(LogInfo logInfo) {
		this.logInfo = logInfo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

    public Short getServiceType() {
        return serviceType;
    }

    public void setServiceType(Short serviceType) {
        this.serviceType = serviceType;
    }

}