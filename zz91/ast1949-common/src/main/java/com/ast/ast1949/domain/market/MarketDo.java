package com.ast.ast1949.domain.market;

public class MarketDo {
	private String address;
	private double lng;
	private double lat;
	private Integer marketNum;
	private Integer companyNum;
	private Integer productNum;
	private String type;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public Integer getMarketNum() {
		return marketNum;
	}

	public void setMarketNum(Integer marketNum) {
		this.marketNum = marketNum;
	}

	public Integer getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
