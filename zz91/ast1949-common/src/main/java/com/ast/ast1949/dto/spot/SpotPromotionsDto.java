package com.ast.ast1949.dto.spot;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.domain.spot.SpotPromotions;

/**
 * author:kongsj date:2013-3-9
 */
public class SpotPromotionsDto {
	private SpotPromotions spotPromotions;
	private ProductsSpot productsSpot;
	private ProductsDO products;
	private SpotInfo spotInfo; 

	public SpotPromotions getSpotPromotions() {
		return spotPromotions;
	}

	public void setSpotPromotions(SpotPromotions spotPromotions) {
		this.spotPromotions = spotPromotions;
	}

	public ProductsSpot getProductsSpot() {
		return productsSpot;
	}

	public void setProductsSpot(ProductsSpot productsSpot) {
		this.productsSpot = productsSpot;
	}

	public ProductsDO getProducts() {
		return products;
	}

	public void setProducts(ProductsDO products) {
		this.products = products;
	}

	public SpotInfo getSpotInfo() {
		return spotInfo;
	}

	public void setSpotInfo(SpotInfo spotInfo) {
		this.spotInfo = spotInfo;
	}

}
