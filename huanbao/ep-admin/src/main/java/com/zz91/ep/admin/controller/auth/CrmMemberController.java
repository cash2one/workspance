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
import com.zz91.ep.admin.service.crm.CRMMemberService;
import com.zz91.ep.domain.crm.CrmMember;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;

/**
 * 会员权限管理controller
 * @author Leon
 * 2011.10.17
 *
 */
@Controller
public class CrmMemberController extends BaseController {
	@Resource 
	private CRMMemberService crmMemberService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView crmMemberChild(HttpServletRequest request, Map<String, Object> out, String parentCode){
		List<ExtTreeDto> crmMemberNode = crmMemberService.queryCrmMemberNode(parentCode);
		return printJson(crmMemberNode, out);
	}
	
	@RequestMapping
	public ModelAndView rightChild(HttpServletRequest request, Map<String, Object> out, String parentCode, String memberCode){
		List<ExtTreeDto> rightTree = crmMemberService.queryRightTreeNode(parentCode, memberCode);
		return printJson(rightTree, out);
	}
	
	@RequestMapping
	public ModelAndView updateCrmMember(HttpServletRequest request, Map<String, Object> out, CrmMember crmMember){
		Integer i=crmMemberService.updateCrmMember(crmMember);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createCrmMember(HttpServletRequest request, Map<String, Object> out, CrmMember crmMember, String parentCode){
		Integer i=crmMemberService.createCrmMember(crmMember, parentCode);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView listCrmMember(HttpServletRequest request, Map<String, Object> out){
		PageDto<CrmMember> page=new PageDto<CrmMember>();
		page.setRecords(crmMemberService.queryCrmMember());
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView deleteCrmMember(HttpServletRequest request, Map<String, Object> out, String memberCode){
		Integer i=crmMemberService.deleteCrmMember(memberCode);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView updateCrmMemberRight(HttpServletRequest request, Map<String, Object> out, String memberCode, Integer crmRightId, Boolean checked){
		Integer i = crmMemberService.updateCrmMemberRight(memberCode, crmRightId, checked);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneCrmMember(HttpServletRequest request, Map<String, Object> out, String memberCode){
		CrmMember crmMember=crmMemberService.queryOneCrmMember(memberCode);
		PageDto<CrmMember> page=new PageDto<CrmMember>();
		List<CrmMember> list=new ArrayList<CrmMember>();
		list.add(crmMember);
		page.setRecords(list);
		return printJson(page, out);
	}
}
