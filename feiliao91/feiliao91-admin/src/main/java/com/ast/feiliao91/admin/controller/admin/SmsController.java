/**
 * @author zhujq
 * @date 2016-04-23
 */
package com.ast.feiliao91.admin.controller.admin;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.controller.BaseController;
import com.ast.feiliao91.domain.company.CompanyValidateSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyValidateDto;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.zz91.util.lang.StringUtils;

@Controller
public class SmsController extends BaseController{
	@Resource
	private CompanyValidateService companyValidateService;
	/**
	 * 默认页
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(Map<String,Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryList(Map<String,Object> out,PageDto<CompanyValidateDto> page, CompanyValidateSearch searchDto) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("gmt_modified");
		}
		page.setDir("desc");
		page = companyValidateService.pageBySearchByAdmin(page, searchDto);
		return printJson(page, out);
	}
}
