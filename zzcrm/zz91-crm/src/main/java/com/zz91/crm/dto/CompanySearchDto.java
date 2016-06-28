/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dto;

import java.io.Serializable;

/**
 * @author totly created on 2011-12-10
 */
public class CompanySearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cname;//公司名称
    private String industryCode;//行业code
    private String address;//地址
    private String name;//联系人
    private String contact;//联系方式(包括座机和手机)
    private String email;//邮箱
    private String businessCode;//业务类型
    private Short star;//客户星级
    private Short sysStar;//系统客户星级
    private String mainProduct;//主营业务
    private String provinceCode;//省份
    private String areaCode;//地区
    private String gmtNextContactStart;//下次联系时间(始)
    private String gmtNextContactEnd;//下次联系时间(末)
    private String gmtLastContactStart;//最后联系时间(始)
    private String gmtLastContactEnd;//最后联系时间(末)
    private String gmtRegisterStart;//注册时间(始)
    private String gmtRegisterEnd;//注册时间(末)
    private String gmtLoginStart;//最近登陆时间(始)
    private String gmtLoginEnd;//最近登陆时间(末)
    private Short contactFlag;//次数查询条件(0:等于=,1:大于>,2:小于<)
    private Integer contactCount;//联系次数
    private Short contactStatus;//查询条件(0不筛选联系次数,1:总联系次数,2:有效联系次数,3:无效联系次数)
    private String saleDept;//销售部门
    private String saleAccount;//销售帐号
    private String saleName;//销售人员
    private Short saleType;//销售类型(销售,客服等)
    private Short registerCode;//客户来源类型
    private Short status;// 关系状态(0:已过期,1:未过期)
    private Short ctype;//公司所在库(个人库,公海库,高级客户库等)
    private Short companyType;// (0:普通客户,1:重点客户)
    private Short disableStatus;//是否放入废品池(0:否,1:是)
    private Short successStatus;//销售成功标识(0:未成功,1:成功(待开通),2:成功(已开通))
    private Short registStatus;//手动数据是否审核(0:未审核 1:已审核)
    private Short disableContact;//是否是有效联系方式(0:有效,1:无效)
    private Short createAccount;//手动添加者
    private Integer repeatId;//重复公司ID(为0时,没有重复公司)
    private Short dragDestryStatus;//是否是拖单/毁单 0:拖单 1:毁单 2:拖单和毁单
    private String gmtCreated;//分配客户创建时间
    private Short autoBlock;//是否自动掉公海
    private Integer loginCount;//登录次数
    private Short blockCount;//公海次数 0:未掉入公海 大于0:公海次数
    private Short saleStatus;//销售状态 0:销售 1:非销售
    private Short source;//来源途径:网络/线下/二者都有
    private Short maturity;//网络成熟度:才开始/有近期推广计划的/已受过伤
    private Short promote;//网络推广时间：短期3个月内/长期（3个月以上or有计划未定时间）/无（直接拒绝）/有效果就合作
    private Short match;//是否匹配:0否 1是
    private Short mainBuy;//采购商:0否 1是
    private Short mainSupply;//供应商:0否 1是
    private Short afterLogin;//最近联系后有登录标识
    private Short messageTime;//询盘时间
    private Short matchDegree;//匹配程度
    private Short kp;//决策人 0:不是kp 1:kp 2:kp推动者
    private String clSaleName; //写过小记的人
    
	public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getIndustryCode() {
        return industryCode;
    }
    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBusinessCode() {
        return businessCode;
    }
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    public Short getStar() {
        return star;
    }
    public void setStar(Short star) {
        this.star = star;
    }
    public String getMainProduct() {
        return mainProduct;
    }
    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct;
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
    public String getGmtNextContactStart() {
        return gmtNextContactStart;
    }
    public void setGmtNextContactStart(String gmtNextContactStart) {
        this.gmtNextContactStart = gmtNextContactStart;
    }
    public String getGmtNextContactEnd() {
        return gmtNextContactEnd;
    }
    public void setGmtNextContactEnd(String gmtNextContactEnd) {
        this.gmtNextContactEnd = gmtNextContactEnd;
    }
    public String getGmtLastContactStart() {
        return gmtLastContactStart;
    }
    public void setGmtLastContactStart(String gmtLastContactStart) {
        this.gmtLastContactStart = gmtLastContactStart;
    }
    public String getGmtLastContactEnd() {
        return gmtLastContactEnd;
    }
    public void setGmtLastContactEnd(String gmtLastContactEnd) {
        this.gmtLastContactEnd = gmtLastContactEnd;
    }
    public String getGmtRegisterStart() {
        return gmtRegisterStart;
    }
    public void setGmtRegisterStart(String gmtRegisterStart) {
        this.gmtRegisterStart = gmtRegisterStart;
    }
    public String getGmtRegisterEnd() {
        return gmtRegisterEnd;
    }
    public void setGmtRegisterEnd(String gmtRegisterEnd) {
        this.gmtRegisterEnd = gmtRegisterEnd;
    }
    public String getGmtLoginStart() {
        return gmtLoginStart;
    }
    public void setGmtLoginStart(String gmtLoginStart) {
        this.gmtLoginStart = gmtLoginStart;
    }
    public String getGmtLoginEnd() {
        return gmtLoginEnd;
    }
    public void setGmtLoginEnd(String gmtLoginEnd) {
        this.gmtLoginEnd = gmtLoginEnd;
    }
    public Integer getContactCount() {
        return contactCount;
    }
    public void setContactCount(Integer contactCount) {
        this.contactCount = contactCount;
    }
    public String getSaleDept() {
        return saleDept;
    }
    public void setSaleDept(String saleDept) {
        this.saleDept = saleDept;
    }
    public String getSaleAccount() {
        return saleAccount;
    }
    public void setSaleAccount(String saleAccount) {
        this.saleAccount = saleAccount;
    }
    public void setSysStar(Short sysStar) {
        this.sysStar = sysStar;
    }
    public Short getSysStar() {
        return sysStar;
    }
	public void setSaleType(Short saleType) {
		this.saleType = saleType;
	}
	public Short getSaleType() {
		return saleType;
	}
	public void setContactFlag(Short contactFlag) {
		this.contactFlag = contactFlag;
	}
	public Short getContactFlag() {
		return contactFlag;
	}
	public void setRegisterCode(Short registerCode) {
		this.registerCode = registerCode;
	}
	public Short getRegisterCode() {
		return registerCode;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getStatus() {
		return status;
	}
	public void setCtype(Short ctype) {
		this.ctype = ctype;
	}
	public Short getCtype() {
		return ctype;
	}
	public void setCompanyType(Short companyType) {
		this.companyType = companyType;
	}
	public Short getCompanyType() {
		return companyType;
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
	public void setRegistStatus(Short registStatus) {
		this.registStatus = registStatus;
	}
	public Short getRegistStatus() {
		return registStatus;
	}
	public void setDisableContact(Short disableContact) {
		this.disableContact = disableContact;
	}
	public Short getDisableContact() {
		return disableContact;
	}
	public void setCreateAccount(Short createAccount) {
		this.createAccount = createAccount;
	}
	public Short getCreateAccount() {
		return createAccount;
	}
	public void setRepeatId(Integer repeatId) {
		this.repeatId = repeatId;
	}
	public Integer getRepeatId() {
		return repeatId;
	}
	public void setDragDestryStatus(Short dragDestryStatus) {
		this.dragDestryStatus = dragDestryStatus;
	}
	public Short getDragDestryStatus() {
		return dragDestryStatus;
	}
	public void setContactStatus(Short contactStatus) {
		this.contactStatus = contactStatus;
	}
	public Short getContactStatus() {
		return contactStatus;
	}
	public String getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(String gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Short getAutoBlock() {
		return autoBlock;
	}
	public void setAutoBlock(Short autoBlock) {
		this.autoBlock = autoBlock;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setBlockCount(Short blockCount) {
		this.blockCount = blockCount;
	}
	public Short getBlockCount() {
		return blockCount;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleStatus(Short saleStatus) {
		this.saleStatus = saleStatus;
	}
	public Short getSaleStatus() {
		return saleStatus;
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
	public void setMainBuy(Short mainBuy) {
		this.mainBuy = mainBuy;
	}
	public Short getMainBuy() {
		return mainBuy;
	}
	public void setMainSupply(Short mainSupply) {
		this.mainSupply = mainSupply;
	}
	public Short getMainSupply() {
		return mainSupply;
	}
	public void setAfterLogin(Short afterLogin) {
		this.afterLogin = afterLogin;
	}
	public Short getAfterLogin() {
		return afterLogin;
	}
	public Short getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Short messageTime) {
		this.messageTime = messageTime;
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
	public String getClSaleName() {
		return clSaleName;
	}
	public void setClSaleName(String clSaleName) {
		this.clSaleName = clSaleName;
	}
	
}