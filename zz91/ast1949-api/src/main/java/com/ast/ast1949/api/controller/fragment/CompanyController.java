/**
 * 
 */
package com.ast.ast1949.api.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.service.company.CompanyService;

/**
 * @author root
 *
 */
@Controller
public class CompanyController extends BaseController {
	
	@Resource
	private CompanyService companyService;
	
	/**
	 * 废料商人推荐/成功网商
	 * @param out
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView goodCompany(Map<String, Object> out,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Company> companyList=companyService.queryGoodCompany(size);
		map.put("list", companyList);
		return printJson(map, out);
	}
	
	//发布供求排行
	
	//最新开通的再生通会员
	@RequestMapping
	public ModelAndView recentZst(Map<String, Object> out, Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Company> list=companyService.queryRecentZst(size);
		map.put("list", list);
		return printJson(map, out);
	}
	
	/**
	 * 登录排行
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView rankLogin(Map<String, Object> out,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Company> companyByLogin=companyService.queryCompanyByLoginNum(size);
		map.put("companylist", companyByLogin);
		return printJson(map, out);
	}
}
