/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-14
 */
package com.zz91.ads.count.dto;

import java.io.Serializable;

import com.zz91.ads.count.domain.AdPosition;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-14
 */
public class AdPositionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AdPosition position;
	private String style;
	/**
	 * @return the position
	 */
	public AdPosition getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(AdPosition position) {
		this.position = position;
	}
	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	
	
}
