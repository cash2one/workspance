/**
 * 
 */
package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.AreaDto;

/**
 * @author mays (mays@asto.com.cn)
 *
 * Created at 2012-11-27
 */
public class TradeBuyNormDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TradeBuy buy;
	private AreaDto area;
	private Integer hasEnable;
	public Integer getHasEnable() {
		return hasEnable;
	}
	public void setHasEnable(Integer hasEnable) {
		this.hasEnable = hasEnable;
	}
	public TradeBuy getBuy() {
		return buy;
	}
	public void setBuy(TradeBuy buy) {
		this.buy = buy;
	}
	public AreaDto getArea() {
		return area;
	}
	public void setArea(AreaDto area) {
		this.area = area;
	}
	
	
}
