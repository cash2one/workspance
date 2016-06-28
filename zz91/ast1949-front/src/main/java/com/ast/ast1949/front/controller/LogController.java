/**
 * 
 */
package com.ast.ast1949.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 * @author root
 *
 */
@Controller
public class LogController extends BaseController {

	@RequestMapping
	public ModelAndView esite(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String cid){
		
		if(cid!=null && StringUtils.isNumber(cid)){
			LogUtil.getInstance().log(cid, "esite_visit");
		}
		
		PageCacheUtil.setNoCDNCache(response);
		
		return new ModelAndView("json");
	}
	
	@RequestMapping
	public ModelAndView myrc(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> out, Integer v){
		if(v!=null){
			if(v==0){
				LogUtil.getInstance().log("zz91_myrc", "visit_myrc");
			}
			if(v==1){
				LogUtil.getInstance().log("zz91_myrc", "visit_myrc_new");
			}
		}
		
		PageCacheUtil.setNoCDNCache(response);
		return new ModelAndView("json");
	}
	
	@RequestMapping
	public ModelAndView kwhit(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> out, String k){
		
		try {
			k=StringUtils.decryptUrlParameter(k);
		} catch (UnsupportedEncodingException e) {
		}
		
		LogUtil.getInstance().log("zz91_trade", "search", HttpUtils.getInstance().getIpAddr(request) ,k);
		
		PageCacheUtil.setNoCDNCache(response);
		return new ModelAndView("json");
	}
	
	/**
	 * 统计最终页数据点击次数
	 * @param request
	 * @param response
	 * @param out
	 * @param from 例如：现货商城 zz91_spot
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView detailsHit(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, String from,
			Integer id) {
		if (id != null && StringUtils.isNotEmpty(from)) {
			LogUtil.getInstance().log(from, "spot_hit",
					HttpUtils.getInstance().getIpAddr(request),
					String.valueOf(id));
		}
		PageCacheUtil.setNoCDNCache(response);
		return new ModelAndView("json");
	}
	
}
