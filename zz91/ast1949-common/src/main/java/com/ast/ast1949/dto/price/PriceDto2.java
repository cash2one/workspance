/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-19
 */
package com.ast.ast1949.dto.price;

import java.io.Serializable;

import com.ast.ast1949.domain.price.PriceDO;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-7-19
 */
public class PriceDto2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PriceDO price;
	private String typeName;
	private String typeUrl;

	/**
	 * @return the price
	 */
	public PriceDO getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(PriceDO price) {
		this.price = price;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeUrl() {
		return typeUrl;
	}

	public void setTypeUrl(String typeUrl) {
		this.typeUrl = typeUrl;
	}

}
