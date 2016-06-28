package com.zz91.ep.dto.crm;

import java.io.Serializable;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.crm.CrmCompSvr;


/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-5-19
 */
public class CrmCompSvrDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CompProfile compProfile;
	private CrmCompSvr crmCompSvr;
	private CompAccount compAccount;
	
	private String svrName;
	private Integer svrPrice;
	private String svrRemark;
	private String saleStaff;
	
	public CompProfile getCompProfile() {
		return compProfile;
	}
	public void setCompProfile(CompProfile compProfile) {
		this.compProfile = compProfile;
	}
	public CrmCompSvr getCrmCompSvr() {
		return crmCompSvr;
	}
	public void setCrmCompSvr(CrmCompSvr crmCompSvr) {
		this.crmCompSvr = crmCompSvr;
	}
	public CompAccount getCompAccount() {
		return compAccount;
	}
	public void setCompAccount(CompAccount compAccount) {
		this.compAccount = compAccount;
	}
	public String getSvrName() {
		return svrName;
	}
	public void setSvrName(String svrName) {
		this.svrName = svrName;
	}
	public Integer getSvrPrice() {
		return svrPrice;
	}
	public void setSvrPrice(Integer svrPrice) {
		this.svrPrice = svrPrice;
	}
	public String getSvrRemark() {
		return svrRemark;
	}
	public void setSvrRemark(String svrRemark) {
		this.svrRemark = svrRemark;
	}
	public void setSaleStaff(String saleStaff) {
		this.saleStaff = saleStaff;
	}
	public String getSaleStaff() {
		return saleStaff;
	}
	
}
