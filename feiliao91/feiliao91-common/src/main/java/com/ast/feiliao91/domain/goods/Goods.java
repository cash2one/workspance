/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.domain.goods;

import java.io.Serializable;
import java.util.Date;

public class Goods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2790313633972012447L;
	private Integer id;
	private Integer companyId;
	private String title;
	private String mainCategory;
	private Integer isImport;
	private String level;
	private String color;
	private String form;
	private String useLevel;
	private String useIntro;
	private String processLevel;
	private String processIntro;
	private String charLevel;
	private String charIntro;
	private String goodAttribute;
	private String unit;
	private String price;
	private Integer hasTax;
	private String quantity;
	private String number;
	private String provideCode;
	private String location;
	private String detail;
	private Date expireTime;
	private String fare;
	private Integer isDel;
	private Integer isSell;
	private Integer checkStatus;
	private Integer addressId;
	private String checkPerson;
	private Date checkTime;
	private Integer isGarbage;
	private Date refreshTime;
	private Date gmtCreated;
	private Date gmtModified;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}

	public Integer getIsImport() {
		return isImport;
	}

	public void setIsImport(Integer isImport) {
		this.isImport = isImport;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getUseLevel() {
		return useLevel;
	}

	public void setUseLevel(String useLevel) {
		this.useLevel = useLevel;
	}

	public String getUseIntro() {
		return useIntro;
	}

	public void setUseIntro(String useIntro) {
		this.useIntro = useIntro;
	}

	public String getProcessLevel() {
		return processLevel;
	}

	public void setProcessLevel(String processLevel) {
		this.processLevel = processLevel;
	}

	public String getProcessIntro() {
		return processIntro;
	}

	public void setProcessIntro(String processIntro) {
		this.processIntro = processIntro;
	}

	public String getCharLevel() {
		return charLevel;
	}

	public void setCharLevel(String charLevel) {
		this.charLevel = charLevel;
	}

	public String getCharIntro() {
		return charIntro;
	}

	public void setCharIntro(String charIntro) {
		this.charIntro = charIntro;
	}

	public String getGoodAttribute() {
		return goodAttribute;
	}

	public void setGoodAttribute(String goodAttribute) {
		this.goodAttribute = goodAttribute;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getHasTax() {
		return hasTax;
	}

	public void setHasTax(Integer hasTax) {
		this.hasTax = hasTax;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProvideCode() {
		return provideCode;
	}

	public void setProvideCode(String provideCode) {
		this.provideCode = provideCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsSell() {
		return isSell;
	}

	public void setIsSell(Integer isSell) {
		this.isSell = isSell;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public Integer getIsGarbage() {
		return isGarbage;
	}

	public void setIsGarbage(Integer isGarbage) {
		this.isGarbage = isGarbage;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
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
