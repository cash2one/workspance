/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.util.lang.StringUtils;



/**
 * ajax 验证，用于验证用户注册信息是否可用
 */
@Controller
public class ValidateController extends BaseController {
	
	@Resource
	private CompAccountService  compAccountService;

	/**
	 * 注册页面检验邮件是否重复
	 * @param email
	 * @param out
	 * @return 返回true,证明不重复,用户可以使用该email
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regEmail(String email, Map<String, Object> out) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(email)) {
			Integer i=compAccountService.queryCountCompAcountByEmail(email);
			if (i== null || i.intValue()<=0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}
	
	/**
	 * 注册页面检验手机是否重复
	 * @param mobile
	 * @param out
	 * @return 返回true 表示手机没有重复,当前注册客户可以使用改电话号码
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regMobile(String mobile, Map<String, Object> out) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(mobile)) {
			
			Integer i=compAccountService.queryCountCompAcountByMobile(mobile);
			if (i== null || i.intValue()<=0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}
	
	/**
	 * 注册页面检验帐号是否重复
	 * @param account
	 * @param out
	 * @return 返回true 表示用户没有重复,当前注册用户可以正常使用
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView regAccount(String account, Map<String, Object> out) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(account)) {
			
			Integer i=compAccountService.queryCountCompAcountByAccount(account);
			if (i== null || i.intValue()<=0) {
				map.put("success", true);
			}
		}
		return printJson(map, out);
	}
    
}