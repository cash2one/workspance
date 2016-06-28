package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.trade.TradeProperty;

public class TradePropertyDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String categoryName;
	
	private TradeProperty tradeProperty;
	
	private Integer propertyId;
	
	private String propertyValue;
	
	private Integer supplyId;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public TradeProperty getTradeProperty() {
		return tradeProperty;
	}

	public void setTradeProperty(TradeProperty tradeProperty) {
		this.tradeProperty = tradeProperty;
	}

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Integer getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}
	
	
}
