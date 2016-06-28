/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.kl91.domain.auth;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-13
 */
public class Kl91SsoConst {
	public static String API_HOST="http://www.kl91.com";
	public static String SSO_DOMAIN="kl91.com";
	public final static String TICKET_KEY="kl91ssotoken";
	
	private String api;
	private String domain;
	
	public void startup(){
		API_HOST = api;
		SSO_DOMAIN = domain;
	}

	/**
	 * @return the api
	 */
	public String getApi() {
		return api;
	}

	/**
	 * @param api the api to set
	 */
	public void setApi(String api) {
		this.api = api;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
}
