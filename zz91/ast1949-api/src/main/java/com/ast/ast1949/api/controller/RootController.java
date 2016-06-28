/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010 下午04:58:55
 */
package com.ast.ast1949.api.controller;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ryan
 */
@Controller
public class RootController extends BaseController {

	@RequestMapping("index.htm")
	public void index(HttpServletRequest request, Map<String, Object> out)
		throws ParseException {
	}
	
}
