package com.ast.ast1949.front.controller.fragment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.InquiryService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.velocity.CacheFragmentDirective;

@Controller
public class CommonController extends BaseController{
	@Resource
	private InquiryService inquiryService;
	@RequestMapping
	public ModelAndView resetCache(HttpServletRequest request, Map<String, Object> out, String v) 
		throws IOException{
		CacheFragmentDirective.CACHE_VERSION=v;
		return printJson("{success:1}", out);
	}
	
	@RequestMapping
	public ModelAndView header(HttpServletRequest request, Map<String, Object> out, String ik){
		if(StringUtils.isEmpty("ik")){
			ik = "index";
		}
		out.put("ik", ik);
		
		List<InquiryDto> list = inquiryService.queryScrollInquiry();
		out.put("list", list);
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView topbar(HttpServletRequest request, Map<String, Object> out){
		//取公告
		return null;
	}
	
	@RequestMapping
	public ModelAndView footer(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView tradeHeader(HttpServletRequest request, Map<String, Object> out, String ik){
		if(StringUtils.isEmpty("ik")){
			ik = "index";
		}
		out.put("ik", ik);
		
		List<InquiryDto> list = inquiryService.queryScrollInquiry();
		out.put("list", list);
		
		return null;
	}
}
