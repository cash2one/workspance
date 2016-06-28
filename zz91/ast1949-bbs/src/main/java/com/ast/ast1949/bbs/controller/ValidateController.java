package com.ast.ast1949.bbs.controller;


import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.zz91.util.lang.StringUtils;

@Controller
public class ValidateController extends BaseController {
	
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	
	@RequestMapping
	public ModelAndView ajaxAccountName(HttpServletRequest request, Map<String, Object> out, 
			String fieldId, String fieldValue, String extraData) throws UnsupportedEncodingException{
		boolean result=true;
		fieldValue=StringUtils.decryptUrlParameter(fieldValue);
		if (StringUtils.isNotEmpty(fieldValue)) {
			Integer userCount= bbsUserProfilerService.countUserProfilerByAccount(fieldValue);
		
			if (userCount == null || userCount == 0) {
				result=false;
			}
		}
		out.put("json", buildResult(fieldId, result));
		return new ModelAndView("json");
	}
	
	private String buildResult(String fieldId, boolean result){
		return "[\""+fieldId+"\","+result+",\"\"]";
	}

}
