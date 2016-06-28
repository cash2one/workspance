/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.domain.score;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-1
 */
public class ScoreGoodsDo extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer category;
	private Date gmtStart;
	private Date gmtEnd;
	private Integer priceBuy;
	private Integer priceDay;
	private Integer scoreBuy;
	private Integer scoreDay;
	private String freight;
	private String remark;
	private String keywords;
	private String detailUrl;
	private String isHot;
	private String isHome;
	private String details;
	private String showPicture;
	private Integer numConversion;
	private Date gmtCreated;
	private Date gmtModified;

	/**
	 * 
	 */
	public ScoreGoodsDo() {
	}

	/**
	 * @param name
	 * @param category
	 * @param gmtStart
	 * @param gmtEnd
	 * @param priceBuy
	 * @param priceDay
	 * @param scoreBuy
	 * @param scoreDay
	 * @param freight
	 * @param remark
	 * @param keywords
	 * @param detailUrl
	 * @param isHot
	 * @param isHome
	 * @param details
	 * @param showPicture
	 * @param numConversion
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public ScoreGoodsDo(String name, Integer category, Date gmtStart,
			Date gmtEnd, Integer priceBuy, Integer priceDay, Integer scoreBuy,
			Integer scoreDay, String freight, String remark, String keywords,
			String detailUrl, String isHot, String isHome, String details,
			String showPicture, Integer numConversion, Date gmtCreated,
			Date gmtModified) {
		super();
		this.name = name;
		this.category = category;
		this.gmtStart = gmtStart;
		this.gmtEnd = gmtEnd;
		this.priceBuy = priceBuy;
		this.priceDay = priceDay;
		this.scoreBuy = scoreBuy;
		this.scoreDay = scoreDay;
		this.freight = freight;
		this.remark = remark;
		this.keywords = keywords;
		this.detailUrl = detailUrl;
		this.isHot = isHot;
		this.isHome = isHome;
		this.details = details;
		this.showPicture = showPicture;
		this.numConversion = numConversion;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gmtStart
	 */
	public Date getGmtStart() {
		return gmtStart;
	}

	/**
	 * @param gmtStart
	 *            the gmtStart to set
	 */
	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}

	/**
	 * @return the gmtEnd
	 */
	public Date getGmtEnd() {
		return gmtEnd;
	}

	/**
	 * @param gmtEnd
	 *            the gmtEnd to set
	 */
	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

	/**
	 * @return the priceBuy
	 */
	public Integer getPriceBuy() {
		return priceBuy;
	}

	/**
	 * @param priceBuy
	 *            the priceBuy to set
	 */
	public void setPriceBuy(Integer priceBuy) {
		this.priceBuy = priceBuy;
	}

	/**
	 * @return the priceDay
	 */
	public Integer getPriceDay() {
		return priceDay;
	}

	/**
	 * @param priceDay
	 *            the priceDay to set
	 */
	public void setPriceDay(Integer priceDay) {
		this.priceDay = priceDay;
	}

	/**
	 * @return the scoreBuy
	 */
	public Integer getScoreBuy() {
		return scoreBuy;
	}

	/**
	 * @param scoreBuy
	 *            the scoreBuy to set
	 */
	public void setScoreBuy(Integer scoreBuy) {
		this.scoreBuy = scoreBuy;
	}

	/**
	 * @return the scoreDay
	 */
	public Integer getScoreDay() {
		return scoreDay;
	}

	/**
	 * @param scoreDay
	 *            the scoreDay to set
	 */
	public void setScoreDay(Integer scoreDay) {
		this.scoreDay = scoreDay;
	}

	/**
	 * @return the freight
	 */
	public String getFreight() {
		return freight;
	}

	/**
	 * @param freight
	 *            the freight to set
	 */
	public void setFreight(String freight) {
		this.freight = freight;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the detailUrl
	 */
	public String getDetailUrl() {
		return detailUrl;
	}

	/**
	 * @param detailUrl
	 *            the detailUrl to set
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	/**
	 * @return the isHot
	 */
	public String getIsHot() {
		return isHot;
	}

	/**
	 * @param isHot
	 *            the isHot to set
	 */
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	/**
	 * @return the isHome
	 */
	public String getIsHome() {
		return isHome;
	}

	/**
	 * @param isHome
	 *            the isHome to set
	 */
	public void setIsHome(String isHome) {
		this.isHome = isHome;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the showPicture
	 */
	public String getShowPicture() {
		return showPicture;
	}

	/**
	 * @param showPicture
	 *            the showPicture to set
	 */
	public void setShowPicture(String showPicture) {
		this.showPicture = showPicture;
	}

	/**
	 * @return the numConversion
	 */
	public Integer getNumConversion() {
		return numConversion;
	}

	/**
	 * @param numConversion
	 *            the numConversion to set
	 */
	public void setNumConversion(Integer numConversion) {
		this.numConversion = numConversion;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated
	 *            the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

}
