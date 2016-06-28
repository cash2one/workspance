/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-13.
 */
package com.ast.ast1949.dto.company;

import com.ast.ast1949.dto.PageDto;

/**
 * 报价订阅（列表）
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class SubscribeForMyrcDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private Integer companyId;
	private Integer subscribeType;
	
	private Integer priceTypeId;
	private Integer priceAssistTypeId;
	
	private String firstTypeName;
	private String secondTypeName;
	private String thirdTypeName;
	private String priceAssistIdName;
	
	private Integer priceId;
	private String priceTitle;
	private String priceContent;
	/**
	 * @return the page
	 */
	public PageDto getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(PageDto page) {
		this.page = page;
	}
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the subscribeType
	 */
	public Integer getSubscribeType() {
		return subscribeType;
	}
	/**
	 * @param subscribeType the subscribeType to set
	 */
	public void setSubscribeType(Integer subscribeType) {
		this.subscribeType = subscribeType;
	}
	/**
	 * @return the priceTypeId
	 */
	public Integer getPriceTypeId() {
		return priceTypeId;
	}
	/**
	 * @param priceTypeId the priceTypeId to set
	 */
	public void setPriceTypeId(Integer priceTypeId) {
		this.priceTypeId = priceTypeId;
	}
	/**
	 * @return the priceAssistTypeId
	 */
	public Integer getPriceAssistTypeId() {
		return priceAssistTypeId;
	}
	/**
	 * @param priceAssistTypeId the priceAssistTypeId to set
	 */
	public void setPriceAssistTypeId(Integer priceAssistTypeId) {
		this.priceAssistTypeId = priceAssistTypeId;
	}
	/**
	 * @return the firstTypeName
	 */
	public String getFirstTypeName() {
		return firstTypeName;
	}
	/**
	 * @param firstTypeName the firstTypeName to set
	 */
	public void setFirstTypeName(String firstTypeName) {
		this.firstTypeName = firstTypeName;
	}
	/**
	 * @return the secondTypeName
	 */
	public String getSecondTypeName() {
		return secondTypeName;
	}
	/**
	 * @param secondTypeName the secondTypeName to set
	 */
	public void setSecondTypeName(String secondTypeName) {
		this.secondTypeName = secondTypeName;
	}
	/**
	 * @return the thirdTypeName
	 */
	public String getThirdTypeName() {
		return thirdTypeName;
	}
	/**
	 * @param thirdTypeName the thirdTypeName to set
	 */
	public void setThirdTypeName(String thirdTypeName) {
		this.thirdTypeName = thirdTypeName;
	}
	/**
	 * @return the priceAssistIdName
	 */
	public String getPriceAssistIdName() {
		return priceAssistIdName;
	}
	/**
	 * @param priceAssistIdName the priceAssistIdName to set
	 */
	public void setPriceAssistIdName(String priceAssistIdName) {
		this.priceAssistIdName = priceAssistIdName;
	}
	/**
	 * @return the priceId
	 */
	public Integer getPriceId() {
		return priceId;
	}
	/**
	 * @param priceId the priceId to set
	 */
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	/**
	 * @return the priceTitle
	 */
	public String getPriceTitle() {
		return priceTitle;
	}
	/**
	 * @param priceTitle the priceTitle to set
	 */
	public void setPriceTitle(String priceTitle) {
		this.priceTitle = priceTitle;
	}
	/**
	 * @return the priceContent
	 */
	public String getPriceContent() {
		return priceContent;
	}
	/**
	 * @param priceContent the priceContent to set
	 */
	public void setPriceContent(String priceContent) {
		this.priceContent = priceContent;
	}
}
