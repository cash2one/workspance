/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-1
 */
package com.ast.ast1949.dto.information;

import java.io.Serializable;

import com.ast.ast1949.domain.information.Periodical;

/**
 * @author yuyonghui
 *
 */
public class PeriodicalDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
      
	private Periodical periodical;
	private String imageName;
	private String previewUrl;
	
	
	public Periodical getPeriodical() {
		return periodical;
	}
	public void setPeriodical(Periodical periodical) {
		this.periodical = periodical;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}
