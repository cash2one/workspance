/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-12-21 上午09:50:19
 */
package com.zz91.crm.dto.data;

import java.io.Serializable;
/**
 * 只针对婚礼妈妈 数据导入
 */
public class CrmCompanyMakeMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer cname;//公司名称
	private String memberCode;//会员编号
	private Integer name;// 联系人
	private Integer sex;//性别 :0男 1女
	private String industryCode;//行业编号
	private String bussinessCode;//业务编号
	private Integer mobile;//手机
	private Integer phone;//电话
	private Integer fax;//婚妈这里存放qq
	private Integer addressZip;//婚妈这里放用户名
	private Integer provinceName;//省份
	private Integer areaName;//地区
	private Integer address;//地址
	private Integer details;//公司简介
	private Integer useType;//用户类型
	
	public Integer getCname() {
		return cname;
	}
	public void setCname(Integer cname) {
		this.cname = cname;
	}
	public Integer getName() {
		return name;
	}
	public void setName(Integer name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getBussinessCode() {
		return bussinessCode;
	}
	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}
	public Integer getMobile() {
		return mobile;
	}
	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public Integer getFax() {
		return fax;
	}
	public void setFax(Integer fax) {
		this.fax = fax;
	}
	public Integer getAddressZip() {
		return addressZip;
	}
	public void setAddressZip(Integer addressZip) {
		this.addressZip = addressZip;
	}
	public Integer getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(Integer provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getAreaName() {
		return areaName;
	}
	public void setAreaName(Integer areaName) {
		this.areaName = areaName;
	}
	public Integer getAddress() {
		return address;
	}
	public void setAddress(Integer address) {
		this.address = address;
	}
	public Integer getDetails() {
		return details;
	}
	public void setDetails(Integer details) {
		this.details = details;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
}
