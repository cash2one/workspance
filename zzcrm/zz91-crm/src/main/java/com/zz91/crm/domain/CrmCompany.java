/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.domain;

import java.util.Date;

/**
 * @author totly created on 2011-12-10
 */
public class CrmCompany implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private Short ctype;
    private Short registStatus;
    private Integer saleCompId;
    private Integer repeatId;
    private String cname;
    private Integer uid;
    private String account;
    private String email;
    private String name;
    private Short sex;
    private String mobile;
    private String phoneCountry;
    private String phoneArea;
    private String phone;
    private String faxCountry;
    private String faxArea;
    private String fax;
    private String address;
    private String addressZip;
    private String details;
    private String industryCode;
    private String memberCode;
    private Short registerCode;
    private String businessCode;
    private String provinceCode;
    private String areaCode;
    private String areaName;//地区名称
    private Short mainBuy;
    private String mainProductBuy;
    private Short mainSupply;
    private String mainProductSupply;
    private Integer loginCount;
    private Date gmtLogin;
    private Date gmtRegister;
    private Date gmtInput;
    private Short sysStar;
    private Short disableContact;
    private String saleAccount;
    private String saleName;
    private Short saleStatus;
    private Date gmtCreated;
    private Date gmtModified;
    private String registerName;
    private String position;
    private String contact;
    private Short star;
    private Short source;
    private Short maturity;
    private Short promote;
    private Short match;
    private Short matchDegree;
    private Short kp;
    private Short serviceStar;
    private String useType;
    private String inputAccount;//录入者
   
	public CrmCompany() {
		super();
	}

	public CrmCompany(Integer id, Integer cid, Short ctype, Short registStatus,
			Integer saleCompId, Integer repeatId, String cname, Integer uid,
			String account, String email, String name, Short sex,
			String mobile, String phoneCountry, String phoneArea, String phone,
			String faxCountry, String faxArea, String fax, String address,
			String addressZip, String details, String industryCode,
			String memberCode, Short registerCode, String businessCode,
			String provinceCode, String areaCode, Short mainBuy,
			String mainProductBuy, Short mainSupply, String mainProductSupply,
			Integer loginCount, Date gmtLogin, Date gmtRegister, Date gmtInput,
			Short sysStar, Short disableContact, String saleAccount, String saleName,
			Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.ctype = ctype;
		this.registStatus = registStatus;
		this.saleCompId = saleCompId;
		this.repeatId = repeatId;
		this.cname = cname;
		this.uid = uid;
		this.account = account;
		this.email = email;
		this.name = name;
		this.sex = sex;
		this.mobile = mobile;
		this.phoneCountry = phoneCountry;
		this.phoneArea = phoneArea;
		this.phone = phone;
		this.faxCountry = faxCountry;
		this.faxArea = faxArea;
		this.fax = fax;
		this.address = address;
		this.addressZip = addressZip;
		this.details = details;
		this.industryCode = industryCode;
		this.memberCode = memberCode;
		this.registerCode = registerCode;
		this.businessCode = businessCode;
		this.provinceCode = provinceCode;
		this.areaCode = areaCode;
		this.mainBuy = mainBuy;
		this.mainProductBuy = mainProductBuy;
		this.mainSupply = mainSupply;
		this.mainProductSupply = mainProductSupply;
		this.loginCount = loginCount;
		this.gmtLogin = gmtLogin;
		this.gmtRegister = gmtRegister;
		this.gmtInput = gmtInput;
		this.sysStar = sysStar;
		this.disableContact = disableContact;
		this.saleAccount = saleAccount;
		this.saleName = saleName;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

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
    public Short getCtype() {
        return ctype;
    }
    public void setCtype(Short ctype) {
        this.ctype = ctype;
    }
    public Short getRegistStatus() {
        return registStatus;
    }
    public void setRegistStatus(Short registStatus) {
        this.registStatus = registStatus;
    }
    public Integer getSaleCompId() {
        return saleCompId;
    }
    public void setSaleCompId(Integer saleCompId) {
        this.saleCompId = saleCompId;
    }
    public Integer getRepeatId() {
        return repeatId;
    }
    public void setRepeatId(Integer repeatId) {
        this.repeatId = repeatId;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
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
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getIndustryCode() {
        return industryCode;
    }
    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }
    public String getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    public Short getRegisterCode() {
        return registerCode;
    }
    public void setRegisterCode(Short registerCode) {
        this.registerCode = registerCode;
    }
    public String getBusinessCode() {
        return businessCode;
    }
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    public String getProvinceCode() {
        return provinceCode;
    }
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public Short getMainBuy() {
        return mainBuy;
    }
    public void setMainBuy(Short mainBuy) {
        this.mainBuy = mainBuy;
    }
    public String getMainProductBuy() {
        return mainProductBuy;
    }
    public void setMainProductBuy(String mainProductBuy) {
        this.mainProductBuy = mainProductBuy;
    }
    public Short getMainSupply() {
        return mainSupply;
    }
    public void setMainSupply(Short mainSupply) {
        this.mainSupply = mainSupply;
    }
    public String getMainProductSupply() {
        return mainProductSupply;
    }
    public void setMainProductSupply(String mainProductSupply) {
        this.mainProductSupply = mainProductSupply;
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
    public Date getGmtInput() {
        return gmtInput;
    }
    public void setGmtInput(Date gmtInput) {
        this.gmtInput = gmtInput;
    }
    public Short getSysStar() {
        return sysStar;
    }
    public void setSysStar(Short sysStar) {
        this.sysStar = sysStar;
    }
    public Short getDisableContact() {
        return disableContact;
    }
    public void setDisableContact(Short disableContact) {
        this.disableContact = disableContact;
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

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPosition() {
		return position;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact() {
		return contact;
	}

	public void setStar(Short star) {
		this.star = star;
	}

	public Short getStar() {
		return star;
	}

	public void setSource(Short source) {
		this.source = source;
	}

	public Short getSource() {
		return source;
	}

	public void setMaturity(Short maturity) {
		this.maturity = maturity;
	}

	public Short getMaturity() {
		return maturity;
	}

	public void setPromote(Short promote) {
		this.promote = promote;
	}

	public Short getPromote() {
		return promote;
	}

	public void setMatch(Short match) {
		this.match = match;
	}

	public Short getMatch() {
		return match;
	}

	public Short getMatchDegree() {
		return matchDegree;
	}

	public void setMatchDegree(Short matchDegree) {
		this.matchDegree = matchDegree;
	}
	
	public Short getKp() {
		return kp;
	}

	public void setKp(Short kp) {
		this.kp = kp;
	}

	public Short getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Short saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Short getServiceStar() {
		return serviceStar;
	}

	public void setServiceStar(Short serviceStar) {
		this.serviceStar = serviceStar;
	}

	public String getInputAccount() {
		return inputAccount;
	}

	public void setInputAccount(String inputAccount) {
		this.inputAccount = inputAccount;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}
	
}