/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dto;

import java.util.Date;

/**
 * @author totly created on 2011-12-10
 */
public class CommCompanyDto extends SaleCompanyDto {

    private static final long serialVersionUID = 1L;

    private String details;//简介
    private Short mainBuy;//求购商(0:不是,1:是)
    private String mainProductBuy;//主营产品
    private Short mainSupply;//供应商(0:不是,1:是)
    private String mainProductSupply;//主营产品
    private Date gmtInput;//数据更新时间(与外网同步时间)

	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	public Date getGmtInput() {
		return gmtInput;
	}
	public void setGmtInput(Date gmtInput) {
		this.gmtInput = gmtInput;
	}
}