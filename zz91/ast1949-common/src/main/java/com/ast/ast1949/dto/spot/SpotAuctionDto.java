package com.ast.ast1949.dto.spot;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.spot.SpotAuction;

/**
 * author:kongsj date:2013-3-12
 */
public class SpotAuctionDto {
	private SpotAuction spotAuction;
	private ProductsDO products;

	private String productsTypeCode; // 供求
	private String productsCategory; // 产品类目
	private Integer logCount; // 竞拍次数

	public SpotAuction getSpotAuction() {
		return spotAuction;
	}

	public void setSpotAuction(SpotAuction spotAuction) {
		this.spotAuction = spotAuction;
	}

	public String getProductsTypeCode() {
		return productsTypeCode;
	}

	public Integer getLogCount() {
		return logCount;
	}

	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}

	public void setLogCount(Integer logCount) {
		this.logCount = logCount;
	}

	public String getProductsCategory() {
		return productsCategory;
	}

	public void setProductsCategory(String productsCategory) {
		this.productsCategory = productsCategory;
	}

	public ProductsDO getProducts() {
		return products;
	}

	public void setProducts(ProductsDO products) {
		this.products = products;
	}

}
