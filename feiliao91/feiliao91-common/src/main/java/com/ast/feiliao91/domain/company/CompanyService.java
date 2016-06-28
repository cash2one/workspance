/**
 * @author zhujq
 * @date 2016-01-26
 * @info 客户服务关系表
 */
package com.ast.feiliao91.domain.company;

import java.util.Date;

public class CompanyService {
	private Integer id;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer companyId; //公司id
	private Integer serviceScore;//服务积分
	private String serviceName;//服务名字
	private String serviceCode;//服务编号
	private Integer isDel;// 删除状态：未删除 0 ;删除 1
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public Integer getServiceScore(){
		return serviceScore;
	}
	
	public void setServiceScore(Integer serviceScore) {
		this.serviceScore = serviceScore;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
}
