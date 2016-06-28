/**
 * 
 */
package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.AreaDto;

/**
 * @author mays 
 * 
 * created at 2012-11-23
 *
 */
public class TradeSupplyNormDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1322850520098750953L;
	
	private TradeSupply supply;
	private CompProfile comp;
	private CompAccount account;
	
	private AreaDto area;
	
	private String categoryName;
	
	public TradeSupply getSupply() {
		return supply;
	}
	public void setSupply(TradeSupply supply) {
		this.supply = supply;
	}
	public CompProfile getComp() {
		return comp;
	}
	public void setComp(CompProfile comp) {
		this.comp = comp;
	}
	public CompAccount getAccount() {
		return account;
	}
	public void setAccount(CompAccount account) {
		this.account = account;
	}
	public AreaDto getArea() {
		return area;
	}
	public void setArea(AreaDto area) {
		this.area = area;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
