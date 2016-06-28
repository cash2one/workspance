package com.zz91.ec.finance.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ec.finance.config.PropConfig;
import com.zz91.ec.finance.domain.Test;
import com.zz91.ec.finance.service.TestService;

@Controller
public class RootController {
	
	@Resource
	TestService testService;
	@Resource
	PropConfig propConfig;

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
		
		out.put("config", propConfig);
		return null;
	}
	
}
