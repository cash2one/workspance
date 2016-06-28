/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.dto.ad;

import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdRenew;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class AdDto implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Ad ad;
	private String positionName;
	private String advertiserName;
	private Integer hasExactAd;
	
	private Integer height;
	private Integer width;
	private String requestUrl;
	private AdRenew renew;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AdRenew getRenew() {
		return renew;
	}

	public void setRenew(AdRenew renew) {
		this.renew = renew;
	}

	/**
	 * @return the ad
	 */
	public Ad getAd() {
		return ad;
	}

	/**
	 * @param ad the ad to set
	 */
	public void setAd(Ad ad) {
		this.ad = ad;
	}

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * @return the advertiserName
	 */
	public String getAdvertiserName() {
		return advertiserName;
	}

	/**
	 * @param advertiserName the advertiserName to set
	 */
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	/**
	 * @return the hasExactAd
	 */
	public Integer getHasExactAd() {
		return hasExactAd;
	}

	/**
	 * @param hasExactAd the hasExactAd to set
	 */
	public void setHasExactAd(Integer hasExactAd) {
		this.hasExactAd = hasExactAd;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	
}
