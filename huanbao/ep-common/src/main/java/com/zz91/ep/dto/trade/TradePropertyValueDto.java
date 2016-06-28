/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.domain.trade.TradeSupply;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public class TradePropertyValueDto implements Serializable{

	private static final long serialVersionUID = 1L;

	// 专业属性编号
    private Integer propertyId;

    // 专业属性名称
    private String propertyName;

    // 专业属性值
    private String propertyValue;
    
    private TradeProperty tradeProperty;
    
    private TradeSupply supply;
    
    private Integer vId;
    
    private Integer supplyId;

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }
    public Integer getPropertyId() {
        return propertyId;
    }
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    public String getPropertyValue() {
        return propertyValue;
    }
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
	public TradeProperty getTradeProperty() {
		return tradeProperty;
	}
	public void setTradeProperty(TradeProperty tradeProperty) {
		this.tradeProperty = tradeProperty;
	}
	public TradeSupply getSupply() {
		return supply;
	}
	public void setSupply(TradeSupply supply) {
		this.supply = supply;
	}
	public Integer getvId() {
		return vId;
	}
	public void setvId(Integer vId) {
		this.vId = vId;
	}
	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}
	public Integer getSupplyId() {
		return supplyId;
	}

}