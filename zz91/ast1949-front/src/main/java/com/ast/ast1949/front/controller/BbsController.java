package com.ast.ast1949.front.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.velocity.AddressTool;

@Deprecated
@Controller
public class BbsController
{
	@RequestMapping
	public ModelAndView index(Map<String,Object> out)
	{
		return new ModelAndView("redirect:"+AddressTool.getAddress("huzhu")+"/index.htm");
//		out.put("pageTitle", "再生互助_废料生意人的交流平台_中国再生资源交易网");
	}
}