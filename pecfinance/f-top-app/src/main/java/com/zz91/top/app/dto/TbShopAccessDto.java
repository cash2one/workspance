/**
 * 
 */
package com.zz91.top.app.dto;

import java.io.Serializable;

import com.zz91.top.app.domain.TbShopAccess;

import net.sf.json.JSONObject;

/**
 * @author mays
 *
 */
public class TbShopAccessDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TbShopAccess shopAccess;
	private JSONObject tbResponse;
	
	public JSONObject getTbResponse() {
		return tbResponse;
	}
	public void setTbResponse(JSONObject tbResponse) {
		this.tbResponse = tbResponse;
	}
	public TbShopAccess getShopAccess() {
		return shopAccess;
	}
	public void setShopAccess(TbShopAccess shopAccess) {
		this.shopAccess = shopAccess;
	}
	
	
}
