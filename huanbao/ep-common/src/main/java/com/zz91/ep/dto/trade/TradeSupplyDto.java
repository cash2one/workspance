package com.zz91.ep.dto.trade;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.zz91.ep.domain.common.LogInfo;
import com.zz91.ep.domain.trade.TradeSupply;

public class TradeSupplyDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TradeSupply supply;
	private String compName;// 公司名称
	private Integer isDel;// 公司是否删除
	private String memberCodeBlock;// 非正常用户的member_code
	private String memberCode;//会员Code
	private String businessCode;// 业务类型
	private String businessName;// 业务名称
	private String provinceName;// 省份
	private String areaName;// 地区
	private String registerCode;//信息来源类型
	private String registerSource;//信息来源
	private Integer loginCount;//总登录次数
	private Date sendTime;//发送寻盘
	private Date receiveTime;//接受寻盘
	private String categoryName;
	private String categoryCode;//类别
	private Integer rid;
	private LogInfo logInfo;
	private Map<Integer, Object> propertyValue;// 专业属性<名称，值>
    private String checkAllRefuse;
    
	
	public String getCheckAllRefuse() {
		return checkAllRefuse;
	}

	public void setCheckAllRefuse(String checkAllRefuse) {
		this.checkAllRefuse = checkAllRefuse;
	}

	public TradeSupplyDto(){
		
	}
	
	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public TradeSupply getSupply() {
		return supply;
	}

	public void setSupply(TradeSupply supply) {
		this.supply = supply;
	}

    public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Map<Integer, Object> getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Map<Integer, Object> propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getMemberCodeBlock() {
        return memberCodeBlock;
    }

    public void setMemberCodeBlock(String memberCodeBlock) {
        this.memberCodeBlock = memberCodeBlock;
    }

    public LogInfo getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(LogInfo logInfo) {
		this.logInfo = logInfo;
	}

	public String getRegisterSource() {
		return registerSource;
	}

	public void setRegisterSource(String registerSource) {
		this.registerSource = registerSource;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
}