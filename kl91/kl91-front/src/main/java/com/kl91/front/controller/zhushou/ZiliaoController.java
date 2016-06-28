package com.kl91.front.controller.zhushou;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.company.Company;
import com.kl91.front.controller.BaseController;
import com.kl91.service.company.CompanyService;
import com.zz91.util.encrypt.MD5;

@Controller
public class ZiliaoController extends BaseController {

	@Resource
	private CompanyService companyService;

	@RequestMapping
	public void ziliao(HttpServletRequest request, Map<String, Object> out,
			Integer result) {
		Company company = companyService.queryById(getSessionUser(request)
				.getCompanyId());
		if(company.getTel()!=null&&company.getTel().length()!=2){
			String[] tel = company.getTel().split(",");
			out.put("tel1", tel[0]);
			out.put("tel2", tel[1]);
			out.put("tel3", tel[2]);
		}
		if(company.getFax()!=null&&company.getFax().length()!=2){
			String[] fax = company.getFax().split(",");
			out.put("fax1", fax[0]);
			out.put("fax2", fax[1]);
			out.put("fax3", fax[2]);
		}
		out.put("company", company);
		out.put("result", result);
	}

	@RequestMapping
	public void mima(HttpServletRequest request,Map<String, Object> out) {
		out.put("id", getSessionUser(request).getCompanyId());
	}

	@RequestMapping
	public ModelAndView doZiliao(HttpServletRequest request,
			Map<String, Object> out, String contact, String mobile,
			Integer sex, String email) {
		Integer i = companyService.updateCompanyByMyrc(contact, mobile, sex,
				email, getSessionUser(request).getCompanyId());
		if (i > 0) {
			out.put("result", 1);
		} else {
			out.put("result", 2);
		}
		return new ModelAndView("forward:ziliao.htm");
	}

	@RequestMapping
	public ModelAndView updateCompanyInfo(HttpServletRequest request,
			Map<String, Object> out, Company company) {
		do {
			if (company == null || company.getId() == null) {
				break;
			}
			Integer i = companyService.editCompany(company);
			if (i > 0) {
				out.put("result", 3);
			} else {
				out.put("result", 4);
			}
		} while (false);
		return new ModelAndView("forward:ziliao.htm");
	}
	
	@RequestMapping
	public ModelAndView doChangePwd(Map<String,Object>out,String opwd,String pwd,Integer id) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		do{
			opwd = MD5.encode(opwd.trim(),MD5.LENGTH_32);
			Company company = companyService.queryById(id);
			if(!opwd.equals(company.getPassword())){
				// 原密码输入错误
				out.put("result",2);
				break;
			}
			pwd = MD5.encode(pwd.trim(),MD5.LENGTH_32);
			Integer i = companyService.updatePwdById(pwd, id);
			if(i>0){
				// 密码修改成功
				out.put("result", 1);
			}else{
				//密码更改错误
				out.put("result",3);
			}
		}while(false);
		return new ModelAndView("forward:mima.htm");
	}
}
