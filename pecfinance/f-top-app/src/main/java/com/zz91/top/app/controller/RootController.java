package com.zz91.top.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.top.app.config.TopConfig;
import com.zz91.top.app.domain.Test;
import com.zz91.top.app.service.TestService;

@Controller
public class RootController extends BaseController{
	
	@Resource
	TestService testService;
	@Resource
	TopConfig topConfig;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		Test test= new Test();
		test.setCol1("init test 中文试试");
		test.setCol2((int)(Math.random()*100));
		test.setCol3(new Date());
		
		Integer i=testService.insert(test);
		
		out.put("impact", i);
		
		List<Test> list=testService.queryAll();
		out.put("list", list);
		
		out.put("config", topConfig);
		return null;
	}
	
}
