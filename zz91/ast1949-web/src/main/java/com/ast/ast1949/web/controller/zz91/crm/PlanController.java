/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-20
 */
package com.ast.ast1949.web.controller.zz91.crm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.web.controller.BaseController;

/**
 * CRM中工作计划有关的方法
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-20
 */
@Controller
public class PlanController extends BaseController{

	/**
	 * 客户人员进入管理自己的工作计划时使用
	 */
	@RequestMapping
	public ModelAndView index(Map<String, Object> out, HttpServletRequest request){
		return null;
	}
	
}
