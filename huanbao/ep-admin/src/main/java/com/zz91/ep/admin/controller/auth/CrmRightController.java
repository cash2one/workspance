package com.zz91.ep.admin.controller.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.crm.CRMRightService;
import com.zz91.ep.domain.crm.CrmRight;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;

/**
 * 权限管理controller
 * @author Leon
 * 2011.10.17
 *
 */
@Controller
public class CrmRightController extends BaseController {
	@Resource
	private CRMRightService crmRightService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView child(HttpServletRequest request, Map<String, Object> out, String parentCode){
		if(parentCode==null){
			parentCode="";
		}
		List<ExtTreeDto> treenode= crmRightService.queryTreeNode(parentCode);
		return printJson(treenode, out);
	}
	
	@RequestMapping
	public ModelAndView deleteRight(HttpServletRequest request, Map<String, Object> out, String code){
		ExtResult result = new ExtResult();
		Integer i = crmRightService.deleteRightByCode(code);
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView queryOneRight(HttpServletRequest request, Map<String, Object> out, String code){
		CrmRight right = crmRightService.queryOneRight(code);
		List<CrmRight> list=new ArrayList<CrmRight>();
		list.add(right);
		PageDto page=new PageDto();
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView createRight(HttpServletRequest request, Map<String, Object> out, CrmRight right, String parentCode){
		Integer i = crmRightService.createRight(right, parentCode);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateRight(HttpServletRequest request, Map<String, Object> out, CrmRight right){
		Integer i=crmRightService.updateRight(right);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
