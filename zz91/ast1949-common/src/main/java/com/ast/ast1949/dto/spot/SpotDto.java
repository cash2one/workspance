package com.ast.ast1949.dto.spot;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.spot.SpotAuction;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.domain.spot.SpotPromotions;

/**
 * author:kongsj date:2013-3-6
 */
public class SpotDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4515636792385608632L;
	private ProductsSpot productsSpot; // 现货 - 供求 关联信息
	private SpotInfo spotInfo; // 现货详细信息
	private SpotPromotions spotPromotions; // 促销信息
	private SpotAuction spotAuction; //竞拍信息
	private ProductsDO product;

	public ProductsSpot getProductsSpot() {
		return productsSpot;
	}

	public SpotInfo getSpotInfo() {
		return spotInfo;
	}

	public void setProductsSpot(ProductsSpot productsSpot) {
		this.productsSpot = productsSpot;
	}

	public void setSpotInfo(SpotInfo spotInfo) {
		this.spotInfo = spotInfo;
	}

	public SpotPromotions getSpotPromotions() {
		return spotPromotions;
	}

	public void setSpotPromotions(SpotPromotions spotPromotions) {
		this.spotPromotions = spotPromotions;
	}

	public SpotAuction getSpotAuction() {
		return spotAuction;
	}

	public void setSpotAuction(SpotAuction spotAuction) {
		this.spotAuction = spotAuction;
	}

	public ProductsDO getProduct() {
		return product;
	}

	public void setProduct(ProductsDO product) {
		this.product = product;
	}

}
