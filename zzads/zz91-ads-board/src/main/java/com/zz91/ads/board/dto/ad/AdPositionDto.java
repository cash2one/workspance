/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.dto.ad;

import com.zz91.ads.board.domain.ad.AdPosition;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class AdPositionDto implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AdPosition adPosition;
	private String parentName;

	/**
	 * @return the adPosition
	 */
	public AdPosition getAdPosition() {
		return adPosition;
	}

	/**
	 * @param adPosition the adPosition to set
	 */
	public void setAdPosition(AdPosition adPosition) {
		this.adPosition = adPosition;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
