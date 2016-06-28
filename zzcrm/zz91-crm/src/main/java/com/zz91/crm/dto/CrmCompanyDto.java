/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-1 上午11:45:15
 */
package com.zz91.crm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出数据类
 */
public class CrmCompanyDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer cid;
	private String account;//账户
    private String cname;//公司名称
    private String industryName;//行业名称
    private String name;//联系人
    private String phone;//电话
    private String mobile;//手机
    private String fax;//传真
    private String address;//地址
    private Integer loginCount;//登录次数
    private Date gmtLogin;//最后登录时间
    private Date gmtRegister;//注册时间
    private Short star;//星级
    private Short ctype;//所在库
    private String saleName;//销售归属
    
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Date getGmtLogin() {
		return gmtLogin;
	}
	public void setGmtLogin(Date gmtLogin) {
		this.gmtLogin = gmtLogin;
	}
	public Date getGmtRegister() {
		return gmtRegister;
	}
	public void setGmtRegister(Date gmtRegister) {
		this.gmtRegister = gmtRegister;
	}
	public Short getStar() {
		return star;
	}
	public void setStar(Short star) {
		this.star = star;
	}
	public Short getCtype() {
		return ctype;
	}
	public void setCtype(Short ctype) {
		this.ctype = ctype;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	
}
