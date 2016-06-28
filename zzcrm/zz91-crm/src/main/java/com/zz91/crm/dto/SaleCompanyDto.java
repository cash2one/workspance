/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author totly created on 2011-12-10
 */
public class SaleCompanyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;//公司表主键
    private Integer cid;//公司ID
    private Integer uid;//用户ID
    private Integer saleCompId;//关系ID
    private String account;//帐号
    private String email;//邮箱
    private Short disableStatus;//是否放入废品池(0:否,1:是)
    private Short successStatus;//销售状态(0:未成功,1:成功(审核待开通),2:成功(已开通))
    private String registerName;//创建公司类型
    private Short star;//客户星级
    private String name;//联系人名字
    private Short sex;//性别
    private Short companyType;//客户类型(0:普通客户,1:重点客户)
    private String cname;//公司名称
    private Integer contactCount;//总联系次数
    private Integer contactAbleCount;//有效联系次数
    private Integer contactDisableCount;//无效联系次数
    private String mobile;//手机号码
    private String phoneCountry;//座机国家号码
    private String phoneArea;//座机地区号码
    private String phone;//座机号码
    private Date gmtContact;//最近联系时间
    private Date gmtNextContact;//下次联系时间
    private String saleDept;//销售部门
    private String saleAccount;//销售帐号
    private String saleName;//销售名字
    private Integer loginCount;//登录次数
    private Date gmtLogin;//最近登录时间
    private Date gmtRegister;//注册时间
    private Date gmtCreated;//挑入时间
    private Short sysStar;//系统星级
    private Short disableContact;//联系方式是否有效 0:有效 1:无效
    private String faxCountry;//传真国家
    private String faxArea;//传真地区
    private String fax;//传真号码
    private String address;//地址
    private String addressZip;//邮编
    private String industryName;//所属行业
    private String provinceCode;//省份code
    private String areaCode;//地区code
    private String businessName;//业务类型
    private String provinceName;//省份
    private String areaName;//地区
    private Integer dragOrderCount;//拖单次数
    private Integer destroyOrderCount;//毁单次数
    private Integer day;//剩余掉公海的时间天数
    private Short registStatus;//审核状态
    private Date gmtBlock;//自动公海时间
    private Short repeatId;//重复公司ID
    private Short color;//最近三天登录标识,用于颜色显示
    private Short match; //是否匹配:1是,0否
    private Date sendTime;//最后发送询盘时间
    private Date receiveTime;//最后接收询盘时间
    private Short ctype; //公司所在库
    private String memberCode;//会员编号
    private String inputAccount;//信息导入者

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Short getStar() {
        return star;
    }
    public void setStar(Short star) {
        this.star = star;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Short getSex() {
        return sex;
    }
    public void setSex(Short sex) {
        this.sex = sex;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public Integer getContactCount() {
        return contactCount;
    }
    public void setContactCount(Integer contactCount) {
        this.contactCount = contactCount;
    }
    public Integer getContactAbleCount() {
        return contactAbleCount;
    }
    public void setContactAbleCount(Integer contactAbleCount) {
        this.contactAbleCount = contactAbleCount;
    }
    public Integer getContactDisableCount() {
        return contactDisableCount;
    }
    public void setContactDisableCount(Integer contactDisableCount) {
        this.contactDisableCount = contactDisableCount;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getGmtContact() {
        return gmtContact;
    }
    public void setGmtContact(Date gmtContact) {
        this.gmtContact = gmtContact;
    }
    public Date getGmtNextContact() {
        return gmtNextContact;
    }
    public void setGmtNextContact(Date gmtNextContact) {
        this.gmtNextContact = gmtNextContact;
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
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setCompanyType(Short companyType) {
		this.companyType = companyType;
	}
	public Short getCompanyType() {
		return companyType;
	}
	public void setSaleDept(String saleDept) {
		this.saleDept = saleDept;
	}
	public String getSaleDept() {
		return saleDept;
	}
	public void setSaleAccount(String saleAccount) {
		this.saleAccount = saleAccount;
	}
	public String getSaleAccount() {
		return saleAccount;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleCompId(Integer saleCompId) {
		this.saleCompId = saleCompId;
	}
	public Integer getSaleCompId() {
		return saleCompId;
	}
	public void setDisableStatus(Short disableStatus) {
		this.disableStatus = disableStatus;
	}
	public Short getDisableStatus() {
		return disableStatus;
	}
	public void setSuccessStatus(Short successStatus) {
		this.successStatus = successStatus;
	}
	public Short getSuccessStatus() {
		return successStatus;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setSysStar(Short sysStar) {
		this.sysStar = sysStar;
	}
	public Short getSysStar() {
		return sysStar;
	}
	public void setDisableContact(Short disableContact) {
		this.disableContact = disableContact;
	}
	public Short getDisableContact() {
		return disableContact;
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
	public String getAddressZip() {
		return addressZip;
	}
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}
	public void setDragOrderCount(Integer dragOrderCount) {
		this.dragOrderCount = dragOrderCount;
	}
	public Integer getDragOrderCount() {
		return dragOrderCount;
	}
	public void setDestroyOrderCount(Integer destroyOrderCount) {
		this.destroyOrderCount = destroyOrderCount;
	}
	public Integer getDestroyOrderCount() {
		return destroyOrderCount;
	}
	
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Short getRegistStatus() {
		return registStatus;
	}
	public void setRegistStatus(Short registStatus) {
		this.registStatus = registStatus;
	}
	public void setGmtBlock(Date gmtBlock) {
		this.gmtBlock = gmtBlock;
	}
	public Date getGmtBlock() {
		return gmtBlock;
	}
	public void setRepeatId(Short repeatId) {
		this.repeatId = repeatId;
	}
	public Short getRepeatId() {
		return repeatId;
	}
	public void setColor(Short color) {
		this.color = color;
	}
	public Short getColor() {
		return color;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setMatch(Short match) {
		this.match = match;
	}
	public Short getMatch() {
		return match;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public Short getCtype() {
		return ctype;
	}
	public void setCtype(Short ctype) {
		this.ctype = ctype;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getInputAccount() {
		return inputAccount;
	}
	public void setInputAccount(String inputAccount) {
		this.inputAccount = inputAccount;
	}
}