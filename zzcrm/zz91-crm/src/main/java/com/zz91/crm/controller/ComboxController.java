package com.zz91.crm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.crm.domain.SysArea;
import com.zz91.crm.service.SysAreaService;
import com.zz91.util.lang.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-21 
 */
@Controller
public class ComboxController extends BaseController {

	final static Short TYPE=1;
	@Resource
	private SysAreaService sysAreaService;
	
	/**
     * 根据父节点取省/地区
     * 比如：取中国所有省份调用url为 getAreaCode.htm?parentCode=10011000
     * @param out
     * @param request 
     * @param parentCode(中国为10011000)
     * @return
     * 
     */
	@RequestMapping
	public ModelAndView queryAreaCode(Map<String, Object> out,HttpServletRequest request,String parentCode){
		List<SysArea> list = null;
		if (parentCode!=null && StringUtils.isNotEmpty(parentCode)){
			 list=sysAreaService.querySysAreaByCode(parentCode, TYPE);
		}
		return printJson(list, out);
	}
}
