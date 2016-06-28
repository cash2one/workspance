/**
 * @author zhujq
 * @date 2016-06-17
 * @describe 淘再生手机站生意管家首页
 */
package com.ast.feiliao91.mobiletrade.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.feiliao91.mobiletrade.controller.BaseController;

@Controller
public class RootController extends BaseController{
	
	@RequestMapping
	public void index(Map<String, Object> out,HttpServletRequest request) {

		out.put("hotTags", "okok");
	}
	
}
