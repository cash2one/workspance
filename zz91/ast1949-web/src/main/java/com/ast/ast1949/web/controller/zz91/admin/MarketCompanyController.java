package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketCompanyDto;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.web.controller.BaseController;

@Controller
public class MarketCompanyController extends BaseController{
	@Resource
	private MarketCompanyService marketCompanyService;
	@RequestMapping
	public ModelAndView index(Map<String, Object>out,HttpServletRequest request){
		return null;
	}
	@RequestMapping
	public ModelAndView queryListMarketByadmin(Map<String, Object>out,HttpServletRequest request,PageDto<MarketCompanyDto> page,CompanyAccount companyAccount,String companyName) throws IOException{
		page.setDir("desc");
		page.setSort("mc.id");
		page=marketCompanyService.queryListMarketByadmin(page, companyAccount, companyName);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView updateIsQuitByMarketId(Map<String, Object>out,HttpServletRequest request,Integer marketId,Integer companyId) throws IOException{
		ExtResult result=new ExtResult();
		if(marketId!=null&&marketId.intValue()>0&&companyId!=null&&companyId.intValue()>0){
			Integer isQuit=1;
			Integer i=marketCompanyService.updateIsQuitByBothId(marketId, companyId, isQuit);
			if(i!=null&&i.intValue()>0){
				result.setSuccess(true);
				result.setData("退出成功");
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView addMarketCompany(Map<String, Object>out,HttpServletRequest request,Integer companyId){
		out.put("companyId", companyId);
		return null;
	}
	
	@RequestMapping
	public ModelAndView insertMarketCopany(Map<String, Object>out,HttpServletRequest request,Integer marketId,Integer companyId) throws IOException{
		ExtResult result=new ExtResult();
		if(marketId!=null&&marketId.intValue()>0&&companyId!=null&&companyId.intValue()>0){
			Integer i=marketCompanyService.insertMarketCompany(marketId, companyId);
			if(i!=null&&i.intValue()>0){
				result.setSuccess(true);
				result.setData("添加成功");
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryListMarketBycompanyId(Map<String, Object>out,HttpServletRequest request,Integer companyId) throws IOException{
		List<Market> list=new ArrayList<Market>();
		if (companyId!=null) {
			list=marketCompanyService.queryMarketByCompanyId(companyId);
		}
		PageDto<Market> page=new PageDto<Market>();
		page.setRecords(list);
		return printJson(page, out);
	}

}
