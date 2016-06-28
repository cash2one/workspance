/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-29
 */
package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.company.CompanyCustomersDO;

/**
 * @author yuyonghui
 * 
 */
public class CompanyCustomersDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String provinceName;
	private String cityName;
	private String groupName;
	private Boolean isDefinition;// 标示是否为其他自定义分组

	private CompanyCustomersDO companyCustomersDO;

	public Boolean getIsDefinition() {
		return isDefinition;
	}

	public void setIsDefinition(Boolean isDefinition) {
		this.isDefinition = isDefinition;
	}

	public CompanyCustomersDO getCompanyCustomersDO() {
		return companyCustomersDO;
	}

	public void setCompanyCustomersDO(CompanyCustomersDO companyCustomersDO) {
		this.companyCustomersDO = companyCustomersDO;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
