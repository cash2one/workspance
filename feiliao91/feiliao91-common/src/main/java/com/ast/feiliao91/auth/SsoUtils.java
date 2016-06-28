/**
 * @author shiqp
 * @date 2016-01-13
 */
package com.ast.feiliao91.auth;
import javax.servlet.http.HttpServletRequest;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

public class SsoUtils extends SessionUtils{
private static SsoUtils _instance=null;
	
	private SsoUtils(){
		
	}
	
	synchronized public static SsoUtils getInstance(){
		if(_instance==null){
			_instance = new SsoUtils();
		}
		return _instance;
	}
	
	public SsoUser getSessionUser(HttpServletRequest request, String sessionid){
		String tickkey=HttpUtils.getInstance().getCookie(request, SsoConst.TICKET_KEY, SsoConst.SSO_DOMAIN);
		if(StringUtils.isEmpty(tickkey)){
			clearnSessionUser(request, sessionid);
			return null;
		}
		SsoUser ssoUser = null;
		if(sessionid==null){
			// TODO 使用session实现
			ssoUser = (SsoUser) request.getSession().getAttribute(SsoUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
		if (ssoUser==null) {
			ssoUser = (SsoUser) MemcachedUtils.getInstance().getClient().get(tickkey);
		}
		return ssoUser;
	}
	
	public void clearnSessionUser(HttpServletRequest request, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().removeAttribute(SsoUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
	}
	
	public void setSessionUser(HttpServletRequest request, SsoUser ssoUser, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().setAttribute(SsoUser.SESSION_KEY, ssoUser);
		}else{
			// TODO 使用memcached实现
		}
	}

}
