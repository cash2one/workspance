package com.zz91.ep.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.admin.service.sys.SysAreaService;
import com.zz91.ep.domain.comp.WebsiteStatistics;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;

/**
 * 系统通用controller
 * @author Leon
 * 2011.10.17
 *
 */
@Controller
public class SysCommonController extends BaseController {

	@Resource
	private SysAreaService sysAreaService;
	@Resource
	private CompProfileService compProfileService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	/**
	 * 通过父code查询出子类别
	 * @param request
	 * @param out
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryAreaChild(HttpServletRequest request, Map<String, Object> out, String parentCode) 
			throws IOException{
		List<ExtTreeDto> areaNode = sysAreaService.queryAreaNode(parentCode);
		return printJson(areaNode, out);
	}
	
	@RequestMapping
	public ModelAndView webStatistics(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryWebStatistics(HttpServletRequest request, Map<String, Object> out,PageDto<WebsiteStatistics> page){
		page=compProfileService.pageWebsiteStatistics(page);
		return printJson(page, out);
	}
}
