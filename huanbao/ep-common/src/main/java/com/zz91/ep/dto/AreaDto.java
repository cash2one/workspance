/**
 * 
 */
package com.zz91.ep.dto;

import java.io.Serializable;

/**
 * @author mays (mays@asto.com.cn)
 *
 * Created at 2012-11-23
 */
public class AreaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2975368642462582512L;

	private String areaCode;
	
	private String country;
	private String province;
	private String city;
	private String area;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}
