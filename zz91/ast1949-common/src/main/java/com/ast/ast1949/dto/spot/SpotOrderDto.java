package com.ast.ast1949.dto.spot;

import com.ast.ast1949.domain.spot.SpotOrder;

/**
 *	author:kongsj
 *	date:2013-3-25
 */
public class SpotOrderDto {
	private SpotOrder spotOrder;
	private String mobile;
	private String orderMobile;
	private String productsTypeCode;
	private String productsCategory;

	public SpotOrder getSpotOrder() {
		return spotOrder;
	}

	public void setSpotOrder(SpotOrder spotOrder) {
		this.spotOrder = spotOrder;
	}

	public String getMobile() {
		return mobile;
	}

	public String getOrderMobile() {
		return orderMobile;
	}

	public String getProductsTypeCode() {
		return productsTypeCode;
	}

	public String getProductsCategory() {
		return productsCategory;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setOrderMobile(String orderMobile) {
		this.orderMobile = orderMobile;
	}

	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}

	public void setProductsCategory(String productsCategory) {
		this.productsCategory = productsCategory;
	}
	
}
