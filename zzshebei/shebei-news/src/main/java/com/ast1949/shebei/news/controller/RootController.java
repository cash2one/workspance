package com.ast1949.shebei.news.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.seo.SeoUtil;

@Controller
public class RootController extends BaseController{
	
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request){
		SeoUtil.getInstance().buildSeo("news", null, null, null, out);
		return null;
	}
	
}
