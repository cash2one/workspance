package com.ast.ast1949.service.facade;

import com.ast.ast1949.util.StringUtils;

public class ParseAreaCode {
	private String contry;
	private String contryCode;
	/** 地区编码 **/
	private String area = "";
	/** 地区编码 **/
	private String areaCode = "";
	/** 省份名称 **/
	private String province = "";
	/** 省份编码 **/
	private String provinceCode = "";
	/** 市名称 **/
	private String city = "";
	/** 市编码 **/
	private String cityCode = "";
	/** 县名称 **/
	private String county = "";
	/** 县编码 **/
	private String countyCode = "";

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void parseAreaCode(String areaCodeStr) {
		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		this.areaCode = areaCodeStr;
		if (StringUtils.isEmpty(areaCode))
			return;

		area = categoryFacade.getValue(areaCode);
		if (areaCode.length() >= 8) {
			contryCode = areaCode.substring(0, 8);
			contry = categoryFacade.getValue(contryCode);
		}
		if (areaCode.length() >= 12) {
			provinceCode = areaCode.substring(0, 12);
			province = categoryFacade.getValue(provinceCode);
		}
		if (areaCode.length() >= 16) {
			cityCode = areaCode.substring(0, 16);
			city = categoryFacade.getValue(cityCode);
		}
		if (areaCode.length() >= 20) {
			countyCode = areaCode.substring(0, 20);
			county = categoryFacade.getValue(countyCode);
		}
	}

	public String getArea() {
		validate();
		return area;
	}

	public String getProvince() {
		validate();
		return province;
	}

	public String getProvinceCode() {
		validate();
		return provinceCode;
	}

	public String getCity() {
		validate();
		return city;
	}

	public String getCityCode() {
		validate();
		return cityCode;
	}

	public String getCounty() {
		validate();
		return county;
	}

	public String getCountyCode() {
		validate();
		return countyCode;
	}

	public String getContry() {
		validate();
		return contry;
	}

	public String getContryCode() {
		validate();
		return contryCode;
	}

	private void validate() {
		if (StringUtils.isEmpty(area) && StringUtils.isNotEmpty(areaCode)) {
			parseAreaCode(areaCode);
		}
	}

}
