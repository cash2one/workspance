/**
 * @author kongsj
 * @date 2016-03-01
 */
package com.ast.feiliao91.dto.goods;

import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.Shopping;

public class ShoppingBuyDto {
	private Goods goods;
	private Shopping shopping;
	private String picAddress;
	private Integer fare;
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Shopping getShopping() {
		return shopping;
	}

	public void setShopping(Shopping shopping) {
		this.shopping = shopping;
	}

	public String getPicAddress() {
		return picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public Integer getFare() {
		return fare;
	}

	public void setFare(Integer fare) {
		this.fare = fare;
	}

	
}
