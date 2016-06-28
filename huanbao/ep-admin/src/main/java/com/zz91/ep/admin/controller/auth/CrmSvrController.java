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
import com.zz91.ep.admin.service.crm.CRMSvrService;
import com.zz91.ep.domain.crm.CrmSvr;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;

/**
 * 服务权限管理controller
 * @author Leon
 * 2011.10.17
 *
 */
@Controller
public class CrmSvrController extends BaseController {
	@Resource 
	private CRMSvrService crmSvrService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView crmSvrChild(HttpServletRequest request, Map<String, Object> out, String parentCode){
		List<ExtTreeDto> crmSvrNode = crmSvrService.queryCrmSvrNode(parentCode);
		return printJson(crmSvrNode, out);
	}
	
	@RequestMapping
	public ModelAndView rightChild(HttpServletRequest request, Map<String, Object> out, String parentCode, Integer crmSvrId){
		List<ExtTreeDto> rightTree = crmSvrService.queryRightTreeNode(parentCode, crmSvrId);
		return printJson(rightTree, out);
	}
	
	@RequestMapping
	public ModelAndView updateCrmSvr(HttpServletRequest request, Map<String, Object> out, CrmSvr crmSvr){
		Integer i=crmSvrService.updateCrmSvr(crmSvr);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createCrmSvr(HttpServletRequest request, Map<String, Object> out, CrmSvr crmSvr, String parentCode){
		Integer i=crmSvrService.createCrmSvr(crmSvr, parentCode);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView listCrmSvr(HttpServletRequest request, Map<String, Object> out){
		PageDto<CrmSvr> page=new PageDto<CrmSvr>();
		page.setRecords(crmSvrService.queryCrmSvr());
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView deleteCrmSvr(HttpServletRequest request, Map<String, Object> out, String code){
		Integer i=crmSvrService.deleteCrmSvr(code);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updateCrmSvrRight(HttpServletRequest request, Map<String, Object> out, Integer crmSvrId, Integer crmRightId, Boolean checked){
		Integer i = crmSvrService.updatecrmSvrRight(crmSvrId, crmRightId, checked);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneCrmSvr(HttpServletRequest request, Map<String, Object> out, String code){
		CrmSvr crmSvr=crmSvrService.queryOneCrmSvr(code);
		PageDto<CrmSvr> page=new PageDto<CrmSvr>();
		List<CrmSvr> list=new ArrayList<CrmSvr>();
		list.add(crmSvr);
		page.setRecords(list);
		return printJson(page, out);
	}
}
