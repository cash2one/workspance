package com.ast.ast1949.web.controller.zz91.crm;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmOutLogDto;
import com.ast.ast1949.service.company.CrmOutLogService;
import com.ast.ast1949.web.controller.BaseController;

/**
 *	author:kongsj
 *	date:2013-5-17
 */
@Controller
public class HighseaController extends BaseController{
	
	@Resource
	private CrmOutLogService crmOutLogService;
	
	@RequestMapping
	public ModelAndView index(Map<String, Object>out,Integer companyId){
		out.put("companyId",companyId);
		return new ModelAndView();
	}
	
	
	@RequestMapping
	public ModelAndView query(CrmOutLogDto crmOutLogDto,PageDto<CrmOutLogDto> page,Map<String, Object>out) throws IOException{
		page.setSort("id");
		page = crmOutLogService.pageCrmOutLog(crmOutLogDto, page);
		return printJson(page, out);
	}
}
