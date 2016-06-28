/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-12 by Ryan.
 */
package com.ast.ast1949.esite.filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.util.lang.StringUtils;
import com.zz91.util.velocity.AddressTool;

public class EsiteFilter implements Filter {

	private Set<String> exclude= new HashSet<String>();
	final static String BASE_DOMAIN=".huanbao.com";

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		if(!forbidenDomain(request.getServerName()) && !requestCid(request)){
			response.sendRedirect(AddressTool.getAddress("front"));
			return ;
		}
		request.setAttribute("domain", getDomain(request.getServerName()));
		chain.doFilter(req, res);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig cfg) throws ServletException {
		String excludeDomain= cfg.getInitParameter("exclude");
		if(StringUtils.isNotEmpty(excludeDomain)){
			String[] ed=excludeDomain.split(",");
			for(String domain:ed){
				exclude.add(domain);
			}
		}
	}
	
	public boolean forbidenDomain(String serverName){
		boolean b=false;
		do{
			if(!serverName.endsWith(BASE_DOMAIN)){
				break;
			}
			String tmp=getDomain(serverName);
			
			if(StringUtils.isEmpty(tmp)){
				break;
			}
			
			b=true;
			for(String s:exclude){
				if(s.equals(tmp)){
					b=false;
					break;
				}
			}
		}while(false);
		return b;
	}
	
	private String getDomain(String serverName){
		return serverName.substring(0, serverName.lastIndexOf(BASE_DOMAIN));
	}
	
	private boolean requestCid(HttpServletRequest request){
		String cid=request.getParameter("cid");
		if(StringUtils.isEmpty(cid)){
			return false;
		}
		if(StringUtils.isNumber(cid)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		EsiteFilter filter=new EsiteFilter();
		filter.exclude.add("www");
		filter.exclude.add("esite");
		filter.exclude.add("myrc");
		filter.exclude.add("news");
		System.out.println(filter.forbidenDomain("abc.huanbao.com") );
	}

}
