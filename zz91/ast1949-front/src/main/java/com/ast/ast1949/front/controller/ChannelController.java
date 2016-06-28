/**
] * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-7
 */
package com.ast.ast1949.front.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.velocity.AddressTool;

/**
 * @author yuyonghui
 * 
 */
@Deprecated
@Controller
public class ChannelController extends BaseController {

	@RequestMapping
	public ModelAndView index(Integer id, Map<String, Object> out, HttpServletResponse response) {
		if(id==null){
			id=1;
		}
		return new ModelAndView("redirect:"+AddressTool.getAddress("price")+"/channel/index.htm?id="+id);
	}

}
