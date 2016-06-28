/**
 * 
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays
 *
 */
public class CompanyValidate extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;
	private String account;
	private String vcode;
	private String vcodeKey;
	private Integer actived;
	private Integer activedType;
	private String registerIp;
	private String registerIpNum;
	private String referer;
	private String refurl;
	private Date gmtActive;
	private Date gmtRegister;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public String getVcodeKey() {
		return vcodeKey;
	}
	public void setVcodeKey(String vcodeKey) {
		this.vcodeKey = vcodeKey;
	}
	public Integer getActived() {
		return actived;
	}
	public void setActived(Integer actived) {
		this.actived = actived;
	}
	public Integer getActivedType() {
		return activedType;
	}
	public void setActivedType(Integer activedType) {
		this.activedType = activedType;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public String getRegisterIpNum() {
		return registerIpNum;
	}
	public void setRegisterIpNum(String registerIpNum) {
		this.registerIpNum = registerIpNum;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getRefurl() {
		return refurl;
	}
	public void setRefurl(String refurl) {
		this.refurl = refurl;
	}
	public Date getGmtActive() {
		return gmtActive;
	}
	public void setGmtActive(Date gmtActive) {
		this.gmtActive = gmtActive;
	}
	public Date getGmtRegister() {
		return gmtRegister;
	}
	public void setGmtRegister(Date gmtRegister) {
		this.gmtRegister = gmtRegister;
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
	
	
}
