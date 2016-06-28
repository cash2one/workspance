/**
 * 
 */
package com.zz91.top.app.domain;

import java.util.Date;


/**
 * @author mays
 *
 */
public class TbShopAccess extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long sid;
	private String accessToken;
	private String tokenType;
	private Integer expiresIn;
	private String refreshToken;
	private Integer reExpiresIn;
	private Integer r1ExpiresIn;
	private Integer r2ExpiresIn;
	private Integer w1ExpiresIn;
	private Integer w2ExpiresIn;
	private String taobaoUserNick;
	private String taobaoUserId;
	private String subTaobaoUserId;
	private String subTaobaoUserNick;
	private Date gmtLastLogin;
	private Integer companyId;
	
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
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
	public String getSubTaobaoUserId() {
		return subTaobaoUserId;
	}
	public void setSubTaobaoUserId(String subTaobaoUserId) {
		this.subTaobaoUserId = subTaobaoUserId;
	}
	public String getSubTaobaoUserNick() {
		return subTaobaoUserNick;
	}
	public void setSubTaobaoUserNick(String subTaobaoUserNick) {
		this.subTaobaoUserNick = subTaobaoUserNick;
	}
	public Date getGmtLastLogin() {
		return gmtLastLogin;
	}
	public void setGmtLastLogin(Date gmtLastLogin) {
		this.gmtLastLogin = gmtLastLogin;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getReExpiresIn() {
		return reExpiresIn;
	}
	public void setReExpiresIn(Integer reExpiresIn) {
		this.reExpiresIn = reExpiresIn;
	}
	public Integer getR1ExpiresIn() {
		return r1ExpiresIn;
	}
	public void setR1ExpiresIn(Integer r1ExpiresIn) {
		this.r1ExpiresIn = r1ExpiresIn;
	}
	public Integer getR2ExpiresIn() {
		return r2ExpiresIn;
	}
	public void setR2ExpiresIn(Integer r2ExpiresIn) {
		this.r2ExpiresIn = r2ExpiresIn;
	}
	public Integer getW1ExpiresIn() {
		return w1ExpiresIn;
	}
	public void setW1ExpiresIn(Integer w1ExpiresIn) {
		this.w1ExpiresIn = w1ExpiresIn;
	}
	public Integer getW2ExpiresIn() {
		return w2ExpiresIn;
	}
	public void setW2ExpiresIn(Integer w2ExpiresIn) {
		this.w2ExpiresIn = w2ExpiresIn;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	
	
}
