package com.ast.ast1949.dto.spot;

import com.ast.ast1949.domain.spot.SpotTrust;

/**
 * author:kongsj date:2013-5-20
 */
public class SpotTrustDto {
	private SpotTrust spotTrust;
	private String mobile;
	private String isChecked;

	public SpotTrust getSpotTrust() {
		return spotTrust;
	}


	public void setSpotTrust(SpotTrust spotTrust) {
		this.spotTrust = spotTrust;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getIsChecked() {
		return isChecked;
	}


	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	

}
