package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CompanyAttest extends DomainSupport{

    /**
     * author:zhouzk
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Integer companyId;
    private String attestType;                  //认证类型  0：个人  1：工商
    private String companyName;                 // 认证公司名称
    private String registerCode;                //注册号码
    private String showCode;                    //是否显示注册号码
    private String registerAddr;                //注册地址
    private String registerCapital;             //注册资金
    private String showCapital;                 //是否显示注册资金
    private String legal;                       //法定代表人
    private String showLegal;                   //是否显示法定代表人
    private String serviceType;                 //公司类型/服务类型
    private String industry;                    //主营行业
    private String business;                    //经营范围
    private String organization;                //登记机关
    private String showOrg;                     //是否显示登记机关
    private String applicant;                   //申请人
    private String companyAddr;                 //公司地址
    private String idNumber;                    //身份证号码
    private String showIdNumber;                //是否显示身份证号码
    private String contact;                     //联系人
    private String sex;                         //性别 0：男 1：女
    private String tel;                         //座机
    private String telCountryCode;              // 电话国家代码
    private String telAreaCode;                 // 电话区号
    private String telNum;                      // 电话号码
    private String mobile;                      //手机号码
    private String showStatus;                  //0：表示不显示   1：表示显示     000000表示6个不同内容显示状态 详见service
    private String checkStatus;                 //审核状态
    private Date checkTime;                   //审核认证时间
    private String checkPerson;                 //审核人
    private Date establishTime;                 //成立时间
    private String establishTimeStr;            //成立时间 用于前台提交
    private Date startTime;                     //营业开始时间 
    private String startTimeStr;                //营业开始时间 用于前台提交
    private Date endTime;                       //营业结束时间
    private String endTimeStr;                  //营业结束时间 用于前台提交
    private Date inspectionTime;                //年检时间
    private String inspectionTimeStr;             //年检时间 用于前台提交
    private String showInspection;              //是否显示年检时间
    private Date gmtCreated;
    private Date gmtModified;
    private String picAddress;                  //认证信息图片
    
    private Integer isVip;
    private Integer isNoVip;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    public String getAttestType() {
        return attestType;
    }
    public void setAttestType(String attestType) {
        this.attestType = attestType;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getRegisterCode() {
        return registerCode;
    }
    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }
    public String getRegisterAddr() {
        return registerAddr;
    }
    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }
    public String getRegisterCapital() {
        return registerCapital;
    }
    public void setRegisterCapital(String registerCapital) {
        this.registerCapital = registerCapital;
    }
    public String getLegal() {
        return legal;
    }
    public void setLegal(String legal) {
        this.legal = legal;
    }
    public String getEstablishTimeStr() {
        return establishTimeStr;
    }
    public void setEstablishTimeStr(String establishTimeStr) {
        this.establishTimeStr = establishTimeStr;
    }
    public String getStartTimeStr() {
        return startTimeStr;
    }
    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }
    public String getEndTimeStr() {
        return endTimeStr;
    }
    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
    public String getInspectionTimeStr() {
        return inspectionTimeStr;
    }
    public void setInspectionTimeStr(String inspectionTimeStr) {
        this.inspectionTimeStr = inspectionTimeStr;
    }
    public String getServiceType() {
        return serviceType;
    }
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getTelCountryCode() {
        return telCountryCode;
    }
    public void setTelCountryCode(String telCountryCode) {
        this.telCountryCode = telCountryCode;
    }
    public String getTelAreaCode() {
        return telAreaCode;
    }
    public void setTelAreaCode(String telAreaCode) {
        this.telAreaCode = telAreaCode;
    }
    public String getTelNum() {
        return telNum;
    }
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }
    public String getBusiness() {
        return business;
    }
    public void setBusiness(String business) {
        this.business = business;
    }
    public String getOrganization() {
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getApplicant() {
        return applicant;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    public String getCompanyAddr() {
        return companyAddr;
    }
    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getShowStatus() {
        return showStatus;
    }
    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }
    public String getCheckStatus() {
        return checkStatus;
    }
    public Date getCheckTime() {
        return checkTime;
    }
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
    public String getCheckPerson() {
        return checkPerson;
    }
    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }
    public String getShowCode() {
        return showCode;
    }
    public void setShowCode(String showCode) {
        this.showCode = showCode;
    }
    public String getShowCapital() {
        return showCapital;
    }
    public void setShowCapital(String showCapital) {
        this.showCapital = showCapital;
    }
    public String getShowLegal() {
        return showLegal;
    }
    public void setShowLegal(String showLegal) {
        this.showLegal = showLegal;
    }
    public String getShowOrg() {
        return showOrg;
    }
    public void setShowOrg(String showOrg) {
        this.showOrg = showOrg;
    }
    public String getShowIdNumber() {
        return showIdNumber;
    }
    public void setShowIdNumber(String showIdNumber) {
        this.showIdNumber = showIdNumber;
    }
    public String getShowInspection() {
        return showInspection;
    }
    public void setShowInspection(String showInspection) {
        this.showInspection = showInspection;
    }
    public Date getEstablishTime() {
        return establishTime;
    }
    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Date getInspectionTime() {
        return inspectionTime;
    }
    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
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
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public Integer getIsNoVip() {
		return isNoVip;
	}
	public void setIsNoVip(Integer isNoVip) {
		this.isNoVip = isNoVip;
	}
	public String getPicAddress() {
		return picAddress;
	}
	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

}
