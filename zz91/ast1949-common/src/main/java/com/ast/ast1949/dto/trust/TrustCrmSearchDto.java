package com.ast.ast1949.dto.trust;

import java.util.Date;

public class TrustCrmSearchDto {
	private Integer isToday;
	private Integer isNew;
	private Integer isFirst;
	private Integer isLost;
	private String crmAccount;
	private Integer isPublic;
	private Integer isRubbish;
	private Integer isDeafult;

	private String name;
	private String contact;
	private String mobile;
	private String areaCode;

	private Integer star;

	private Integer isHaveSell;
	private Integer isHaveBuy;
	private Date gmtNextVisit;

	private String nextVisitPhoneToStr;
	private String nextVisitPhoneFromStr;

	public Integer getIsToday() {
		return isToday;
	}

	public void setIsToday(Integer isToday) {
		this.isToday = isToday;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public Integer getIsLost() {
		return isLost;
	}

	public void setIsLost(Integer isLost) {
		this.isLost = isLost;
	}

	public String getCrmAccount() {
		return crmAccount;
	}

	public void setCrmAccount(String crmAccount) {
		this.crmAccount = crmAccount;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getIsRubbish() {
		return isRubbish;
	}

	public void setIsRubbish(Integer isRubbish) {
		this.isRubbish = isRubbish;
	}

	public Integer getIsDeafult() {
		return isDeafult;
	}

	public void setIsDeafult(Integer isDeafult) {
		this.isDeafult = isDeafult;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getIsHaveSell() {
		return isHaveSell;
	}

	public void setIsHaveSell(Integer isHaveSell) {
		this.isHaveSell = isHaveSell;
	}

	public Date getGmtNextVisit() {
		return gmtNextVisit;
	}

	public void setGmtNextVisit(Date gmtNextVisit) {
		this.gmtNextVisit = gmtNextVisit;
	}

	public Integer getIsHaveBuy() {
		return isHaveBuy;
	}

	public void setIsHaveBuy(Integer isHaveBuy) {
		this.isHaveBuy = isHaveBuy;
	}

	public String getNextVisitPhoneToStr() {
		return nextVisitPhoneToStr;
	}

	public void setNextVisitPhoneToStr(String nextVisitPhoneToStr) {
		this.nextVisitPhoneToStr = nextVisitPhoneToStr;
	}

	public String getNextVisitPhoneFromStr() {
		return nextVisitPhoneFromStr;
	}

	public void setNextVisitPhoneFromStr(String nextVisitPhoneFromStr) {
		this.nextVisitPhoneFromStr = nextVisitPhoneFromStr;
	}

}
