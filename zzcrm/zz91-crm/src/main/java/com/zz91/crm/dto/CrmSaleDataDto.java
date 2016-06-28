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
public class CrmSaleDataDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String saleAccount;
    private String saleName;
    private String saleDept;
	private Integer star;//客户星级
    private Integer count;//客户数
    private Short ctype;//用户统计当天客户类型
    
    public String getSaleAccount() {
		return saleAccount;
	}
	public void setSaleAccount(String saleAccount) {
		this.saleAccount = saleAccount;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleDept() {
		return saleDept;
	}
	public void setSaleDept(String saleDept) {
		this.saleDept = saleDept;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setCtype(Short ctype) {
		this.ctype = ctype;
	}
	public Short getCtype() {
		return ctype;
	}
}