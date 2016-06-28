package com.zz91.ads.count.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.ads.count.domain.Ad;
import com.zz91.ads.count.domain.AdLog;
import com.zz91.ads.count.service.AdsService;
import com.zz91.ads.count.thread.AdLogThread;
import com.zz91.ads.count.thread.ControlThread;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * 广告点击servlet
 */
public class ZZ91adhit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ZZ91adhit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * <br />提交的参数： 
	 * <br />a：表示广告ID
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 实现广告点击收集行为操作
		//step1 获取广告ID
		//step2 获取cookie,判断广告点击类型
		//step3 log广告行为
		//step4 获取广告target url，并redirect到target url
		
		Integer adid = Integer.valueOf(request.getParameter("a"));
		
		Ad ad=AdsService.getInstance().queryTargetUrl(adid);
		if(ad!=null){
			
			AdLog log=new AdLog();
			log.setAdId(adid);
			log.setPositionId(ad.getPositionId());
			log.setHitType(1);
			log.setIp(HttpUtils.getInstance().getIpAddr(request));
			log.setTime(System.currentTimeMillis());
			
			String flag=HttpUtils.getInstance().getCookie(request, "a_"+adid, "zz91.com");
			if(StringUtils.isNotEmpty(flag)){
				log.setClickType(1);
			}else{
				log.setClickType(0);
				HttpUtils.getInstance().setCookie(response, "a_"+adid, "1", "zz91.com", 86400);
			}
			ControlThread.excute(new AdLogThread(log));
			
			response.sendRedirect(ad.getAdTargetUrl());
		}else{
			response.setCharacterEncoding("utf-8");
			PrintWriter pw=response.getWriter();
			pw.print("对不起，您查看的广告可能不存在，或者已经过期了");
			pw.close();
		}
		
	}

}
