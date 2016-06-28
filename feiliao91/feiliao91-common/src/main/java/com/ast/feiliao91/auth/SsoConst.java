/**
 * @author shiqp
 * @date 2016-01-13
 */
package com.ast.feiliao91.auth;

public class SsoConst {
	public static String API_HOST="http://passport.zz91.com/api";
	public static String SSO_DOMAIN="taozaisheng.com";
	public final static String TICKET_KEY="feiliao91ssotoken";
	public final static String SESSION_KEY="FLSID";
	
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
