/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-22 by mays@caiban.net.
 */
package com.ast.ast1949.bbs.filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ast.ast1949.util.BbsConst;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.http.HttpUtils;

/**
 * 用于过滤用户权限
 *
 * @author mays@caiban.net
 *
 */
public class AuthorizeFilter implements Filter {

	private String deniedURL = "";
	private String loginURL = "";

	private Set<String> needLoginPage;
	private Set<String> noAuthPage;

//	private static Logger LOG = Logger.getLogger(AuthorizeFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest rq, ServletResponse rp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request= (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;
		
//		// TODO 设置页面上的
//		Map<String, String> siteinfo=ParamFacade.getInstance().getParamByType("site_info_front");
//		for(String k: siteinfo.keySet()){
//			request.setAttribute(k, siteinfo.get(k));
//		}

		String uri = request.getRequestURI();
		String queryString=request.getQueryString();
		if(!StringUtils.isEmpty(queryString)){
			queryString="?"+queryString;
		}
		else{
			queryString="";
		}
		String path = request.getContextPath();
		path= path==null?"":path;
		String url=loginURL;

		SessionUser loginUser = (SessionUser) getCachedSession(request, BbsConst.SESSION_USER);
		if(loginUser!=null){
			request.setAttribute("mycompany", loginUser);
		}
		do{
			if(!canFilter(needLoginPage, path, uri)){  //不需要登录即可访问的页面
				chain.doFilter(request, response);
				return ;
			}
			else{
				if(loginUser!=null){
					chain.doFilter(request, response);
					return ;
				}
			}

			if(loginUser == null){  //用户未登录
				url=loginURL;
				HttpUtils.getInstance().setCookie(response, BbsConst.MYSESSIONID, null,
						String.valueOf(MemcachedUtils.getInstance().getClient().get("baseConfig.domain")), 0);
				break;
			}



			if(canFilter(noAuthPage, path, uri)){
				chain.doFilter(request, response);
				return ;
			}

			if(canFilter(request, response, path, uri)){
				chain.doFilter(request, response);
				return ;
			}else{
				url=deniedURL;
				break;
			}
		}while(false);

		//AJAX请求权限过滤
		String redirectUrl=path+url+"?url="+uri+queryString;
		if(request.getHeader("x-requested-with")!=null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
			response.setHeader("sessionstatus","timeout");
			response.setHeader("redirectUrl",redirectUrl);
			return ;
		}

		response.sendRedirect(redirectUrl);
//		chain.doFilter(request, response);
		return ;
	}

	public void init(FilterConfig config) throws ServletException {
		deniedURL = config.getInitParameter("deniedURL");
		loginURL = config.getInitParameter("loginURL");

//		debug = config.getInitParameter("debug");

		String tmp[] = null;
		needLoginPage = new HashSet<String>();
		String e1=config.getInitParameter("needLoginPage");
		if(StringUtils.isNotEmpty(e1)){
			tmp=e1.split("\\|");
			for(String ex:tmp){
				needLoginPage.add(ex);
			}
		}

		noAuthPage = new HashSet<String>();
		String e2=config.getInitParameter("noAuthPage");
		if(StringUtils.isNotEmpty(e2)){
			tmp=e2.split("\\|");
			for(String ex:tmp){
				noAuthPage.add(ex);
			}
		}

	}

	/**
	 *
	 * @param exclude
	 * @param path
	 * @param uri
	 * @return
	 */
	public boolean canFilter(Set<String> exclude, String path,String uri){
		for(String regex:exclude){
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(uri);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean canFilter(HttpServletRequest request, HttpServletResponse response, String path, String uri){

		List<String> rightList=(List<String>) getCachedSession(request, BbsConst.SESSION_AUTH);
		if(rightList==null){
			return false;
		}

		String query = request.getQueryString();
		String contextPath = request.getContextPath();
		if(uri.startsWith(contextPath)){
			uri=uri.substring(contextPath.length(),uri.length());
		}
		String url = uri+(query == null ? "" : "?"+query);

		boolean ispass=false;
		for(String s:rightList){
			if(url.matches(s)){
				ispass=true;
				break;
			}
		}

		return ispass;

	}

	@SuppressWarnings("unchecked")
	private Object getCachedSession(HttpServletRequest request, String key){
		//从memcache获取SESSION值
		//重新设置memcached的值
		String sessionId=HttpUtils.getInstance().getCookie(request,
				BbsConst.MYSESSIONID, String.valueOf(MemcachedUtils.getInstance().getClient().get("baseConfig.domain")));

		if(sessionId==null){
			return null;
		}

		Map<String, Object> mysessionMap=(Map<String, Object>) MemcachedUtils.getInstance().getClient().get(sessionId);
		if(mysessionMap!=null){
			MemcachedUtils.getInstance().getClient().replace(sessionId, BbsConst.SESSION_TIME_OUT, mysessionMap);
			return mysessionMap.get(key);
		}

		return null;
	}

}
