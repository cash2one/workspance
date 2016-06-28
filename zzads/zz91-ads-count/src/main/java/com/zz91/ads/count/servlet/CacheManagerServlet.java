/**
 * 
 */
package com.zz91.ads.count.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.ads.count.service.AdsService;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.http.IP2Long;
import com.zz91.util.lang.StringUtils;

/**
 * 用于管理页面缓存功能的状态
 * 
 * 配置：
 * <servlet>
 * 	<servlet-name>cacheFragmentServlet</servlet-name>
 * 	<servlet-class>com.zz91.util.velocity.CacheFragmentManagerServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 * 	<servlet-name>cacheFragmentServlet</servlet-name>
 * 	<url-pattern>/fragment/manager.servlet</url-pattern>
 * </servlet-mapping>
 * 
 * @author root
 *
 */
public class CacheManagerServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	static Set<Long> excludeIP=new HashSet<Long>(); 
	
	public void init(ServletConfig config) throws ServletException {
		String whiteIP = config.getInitParameter("excludeIP");
		if(whiteIP!=null){
			String[] ipArr=whiteIP.split(",");
			for(String ip:ipArr){
				if(ip.contains("-")){
					String[] ipRang=ip.split("-");
					Long rangFrom=IP2Long.ipToLong(ipRang[0]);
					Long rangTo = IP2Long.ipToLong(ipRang[1]);
					for(long i=rangFrom;i<=rangTo;i++){
						excludeIP.add(rangFrom+i);
					}
				}else{
					excludeIP.add(IP2Long.ipToLong(ip));
				}
			}
		}
	}
	
	public CacheManagerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ip is not allow :; long type: ");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest rq,
			HttpServletResponse rp) throws ServletException, IOException {
		
		HttpServletRequest request= (HttpServletRequest) rq;

		String ip=HttpUtils.getInstance().getIpAddr(request);
		Long ipl=IP2Long.ipToLong(ip);
//		if(!excludeIP.contains(ipl)){
//			System.out.println("ip is not allow :" +ip+"; long type: "+ipl);
//			return ;
//		}
		
		HttpServletResponse response = (HttpServletResponse) rp;
		
		response.setContentType("text/html;charset=utf-8");

		String v=request.getParameter("v");
		String vm="";
		if(StringUtils.isNotEmpty(v) && StringUtils.isNumber(v) && !AdsService.CACHE_VERSION.equals(v)){
			AdsService.CACHE_VERSION=v;
			vm="version is reseted";
		}
//		String d=request.getParameter("d");
//		String dm="";
//		if(StringUtils.isNotEmpty(v)){
//			if("on".equals(d) && !CacheFragmentDirective.DEBUG){
//				CacheFragmentDirective.DEBUG=true;
//				dm="Debug is on";
//			}
//			if("off".equals(d) && CacheFragmentDirective.DEBUG){
//				CacheFragmentDirective.DEBUG=false;
//				dm="Debug is off";
//			}
//		}

		StringBuffer sb=new StringBuffer();
//		sb.append("<html lang=\"zh-CN\"><head></head><body>");
		sb.append("<form action='' method='post'>");
		sb.append("Cache version: <input name='v' style='width:20px;' type='text' value='").append(AdsService.CACHE_VERSION).append("' />");
		sb.append("<span style='color:red' >").append(vm).append("</span><br/>");
//		sb.append("debug model: ");
//		if(AdsService.DEBUG){
//			sb.append("<input type='radio' name='d' value='on' checked='true'/>on ");
//			sb.append("<input type='radio' name='d' value='off' />off");
//		}else{
//			sb.append("<input type='radio' name='d' value='on' />on ");
//			sb.append("<input type='radio' name='d' value='off' checked='true'/>off");
//		}
//		sb.append("<span style='color:red' >").append(dm).append("</span><br/>");
		sb.append("<input type='submit' value='保存' /></form>");
		
		sb.append("<br />");
		sb.append("README:<br />");
		sb.append("version: Change version will reset cache of all page fragment<br />");
//		sb.append("debug: Ignore cache if status is on<br />");
//		sb.append("</body></html>");

		PrintWriter pw = response.getWriter();

		pw.write(sb.toString());
		pw.close();
	}
}
