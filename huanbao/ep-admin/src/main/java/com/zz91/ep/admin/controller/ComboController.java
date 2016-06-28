/**
 * 
 */
package com.zz91.ep.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.service.crm.CRMMemberService;
import com.zz91.ep.admin.service.sys.SysAreaService;
import com.zz91.ep.admin.service.trade.IndustryChainService;
import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.domain.crm.CrmMember;
import com.zz91.ep.domain.sys.SysArea;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-11-25 
 */
/**
 * @author qizj qizj@zz91.net 2011-11-25
 */
@Controller
public class ComboController extends BaseController {

	@Resource
	private CRMMemberService crmMemberService;
	@Resource
	private SysAreaService sysAreaService;
	@Resource
	private IndustryChainService industryChainService;
	
	@RequestMapping
	public ModelAndView member(HttpServletRequest request,
			Map<String, Object> out, String parentCode) {
		List<CrmMember> list=crmMemberService.queryChildMembers(parentCode);
		CrmMember member=new CrmMember();
		member.setName("请选择");
		member.setCode("");
		List<CrmMember> resultList=new ArrayList<CrmMember>();
		resultList.add(member);
		resultList.addAll(list);
		
		return printJson(resultList, out);
	}
	
	@RequestMapping
	public ModelAndView queryMembersByCode(Map<String, Object> out,HttpServletRequest request,String parentCode){
		List<CrmMember> list = crmMemberService.queryChildMembers(parentCode);
		return printJson(list, out);
	}
	
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
    public ModelAndView getAreaCode(Map<String, Object> out, HttpServletRequest request, String parentCode){
    	List<SysArea> list = sysAreaService.queryAreaChild(parentCode);
		return printJson(list, out);
    }
    
    @RequestMapping 
    public ModelAndView getChainAreaName(Map<String, Object> out, HttpServletRequest request){
    	List<IndustryChain> list = industryChainService.querySimpChain();
    	Integer index=0;
    	for (IndustryChain chain : list) {
    		index++;
			chain.setId(index);
		}
    	return printJson(list, out);
    }
    
    @RequestMapping
    public ModelAndView getCategoryName(Map<String, Object> out,HttpServletRequest request,String areaCode){
    	List<IndustryChain> list= industryChainService.querySimpChainByAreaCode(areaCode);
    	return printJson(list, out);
    }
}
