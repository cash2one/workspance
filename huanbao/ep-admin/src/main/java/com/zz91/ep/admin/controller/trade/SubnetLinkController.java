/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 下午02:35:55
 */
package com.zz91.ep.admin.controller.trade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.trade.SubnetLinkService;
import com.zz91.ep.domain.trade.SubnetLink;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;

@Controller
public class SubnetLinkController extends BaseController {
	
	@Resource
	private SubnetLinkService subnetLinkService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryAllParent(Map<String, Object> out,HttpServletRequest request){
		List<SubnetLink> list=subnetLinkService.queryParentLink();
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView addSubnetLink(Map<String, Object> out,HttpServletRequest request,SubnetLink link){
		ExtResult result = new ExtResult();
		Integer i=subnetLinkService.createSubnetLink(link);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateSubnetLink(Map<String, Object> out,HttpServletRequest request,SubnetLink link){
		ExtResult result = new ExtResult();
		Integer i=subnetLinkService.updateSubnetLink(link);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryLinkByParentId(Map<String, Object> out,HttpServletRequest request,Integer parentId,PageDto<SubnetLink> page){
		page = subnetLinkService.pageSubnetLink(parentId, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView deleteChildLink(Map<String, Object> out,HttpServletRequest request,Integer id){
		ExtResult result = new ExtResult();
		Integer i=subnetLinkService.deleteLinkById(id);
		if (i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
