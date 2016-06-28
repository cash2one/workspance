/**
 * 
 */
package com.zz91.top.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mays
 *
 */
public class SessionUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer shopId;
	private String taobaoUserNick;
	private String taobaoUserId;
	private String subTaobaoUserNick;
	private String subTaobaoUserId;
	private String accessToken;
	private Date gmtLastLogin;
	private String authFlag;
	
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getTaobaoUserNick() {
		return taobaoUserNick;
	}
	public void setTaobaoUserNick(String taobaoUserNick) {
		this.taobaoUserNick = taobaoUserNick;
	}
	public String getTaobaoUserId() {
		return taobaoUserId;
	}
	public void setTaobaoUserId(String taobaoUserId) {
		this.taobaoUserId = taobaoUserId;
	}
	public String getSubTaobaoUserNick() {
		return subTaobaoUserNick;
	}
	public void setSubTaobaoUserNick(String subTaobaoUserNick) {
		this.subTaobaoUserNick = subTaobaoUserNick;
	}
	public String getSubTaobaoUserId() {
		return subTaobaoUserId;
	}
	public void setSubTaobaoUserId(String subTaobaoUserId) {
		this.subTaobaoUserId = subTaobaoUserId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getGmtLastLogin() {
		return gmtLastLogin;
	}
	public void setGmtLastLogin(Date gmtLastLogin) {
		this.gmtLastLogin = gmtLastLogin;
	}
	public String getAuthFlag() {
		return authFlag;
	}
	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}
	
	
}
