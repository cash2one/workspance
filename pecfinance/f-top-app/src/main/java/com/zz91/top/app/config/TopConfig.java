/**
 * 
 */
package com.zz91.top.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author mays
 *
 */
@Component("propConfig")
public class TopConfig {

	@Value("${appKey}")
	private String appKey;
	@Value("${appSecret}")
	private String appSecret;
	@Value("${url}")
	private String url;
	@Value("${callback}")
	private String callback;
	@Value("${authUrl}")
	private String authUrl;
	@Value("${tokenUrl}")
	private String tokenUrl;
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	public String getTokenUrl() {
		return tokenUrl;
	}
	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}
	
}
