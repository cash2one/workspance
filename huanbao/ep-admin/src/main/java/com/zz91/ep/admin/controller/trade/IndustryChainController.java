/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-8 下午03:18:13
 */
package com.zz91.ep.admin.controller.trade;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.trade.CompanyIndustryChainService;
import com.zz91.ep.admin.service.trade.IndustryChainService;
import com.zz91.ep.domain.comp.CompanyIndustryChain;
import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;

/**
 * 特色产业链
 */
@Controller
public class IndustryChainController extends BaseController {
	
	@Resource
	private IndustryChainService industryChainService;
	@Resource
	private CompanyIndustryChainService companyIndustryChainService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object> out,Integer cid){
		out.put("cid", cid);
		return null;
	}
	
	@RequestMapping
	public ModelAndView querychain(Map<String, Object> out,HttpServletRequest request,
			String areaCode,PageDto<IndustryChain> page,Integer cid){
		page=industryChainService.pageIndustryChains(areaCode, page,cid);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView addIndustryChain(Map<String, Object> out,HttpServletRequest request,IndustryChain chain){
		ExtResult result = new ExtResult();
		Integer i=industryChainService.createIndustryChain(chain);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateIndustryChain(Map<String, Object> out,HttpServletRequest request,IndustryChain chain){
		ExtResult result = new ExtResult();
		Integer i=industryChainService.updateIndustryChain(chain);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateShowIndex(Map<String, Object> out,HttpServletRequest request,Integer id,Short showIndex){
		ExtResult result = new ExtResult();
		Integer i=industryChainService.updateShowIndexById(id, showIndex);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateDelStatus(Map<String, Object> out,HttpServletRequest request,Integer id,Short delStatus){
		ExtResult result = new ExtResult();
		Integer i=industryChainService.updateDelStatusById(id, delStatus);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(delStatus, out);
	}
	
	@RequestMapping
	public ModelAndView AddCompChain(Map<String, Object> out,HttpServletRequest request,CompanyIndustryChain chain){
		ExtResult result = new ExtResult();
		Integer i = companyIndustryChainService.createCompIndustryChain(chain);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateCompChain(Map<String, Object> out,HttpServletRequest request,CompanyIndustryChain chain){
		ExtResult result = new ExtResult();
		Integer i =companyIndustryChainService.updateCompIndustryChain(chain);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateCompChainDelStatus(Map<String, Object> out,HttpServletRequest request,Integer cid,Integer chainId,Short delStatus){
		ExtResult result = new ExtResult();
		Integer i=companyIndustryChainService.updateDelStatusByCidAndChainId(cid, chainId,delStatus);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
