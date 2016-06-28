/**
 * @author zhujq
 * @date 2016-06-14
 */
package com.ast.feiliao91.mobile.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoConst;
import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.dto.MixCategory;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;

@Controller
public class RegisterController extends BaseController{
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyValidateService companyValidateService;
	@Resource
	private CompanyInfoService companyInfoService;
	/**
	 * 注册第一步发送手机验证码
	 * @param out
	 */
	@RequestMapping
	public void step1(Map<String,Object> out){
		
	}
	
	/**
	 * 注册第二步
	 * @return
	 */
	@RequestMapping
	public void step2(Map<String,Object> out,  String account){
		out.put("account", account);
	}
	
	/**
	 * 验证手机邮箱是否存在
	 * @param out
	 * @param mobile
	 * @param email
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView validateInfo(Map<String,Object> out, String mobile, String email) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		Integer i = companyAccountService.hasAM(mobile, email);
		map.put("info", i);
		map.put("mobile", mobile);
		return printJson(map, out);
	}
	
	/**
	 * 验证手机验证码
	 * @param mobile
	 * @param vcode
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView validateByMobile(Map<String,Object> out, String mobile, String vcode) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		//是否验证成功标志,默认为失败
		boolean isTrue = false;
		Integer i =companyValidateService.validateByMobile(mobile, vcode);
		if(i > 0){
			isTrue = true;
		}
		map.put("isTrue", isTrue);
		return printJson(map, out);
	}
	
	/**
	 * 获得左边的省份
	 * @param out
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView catChild(Map<String, Object> out, String parentCode) throws IOException {
		List<Category> list = new ArrayList<Category>();
		Map<String, String> map = CategoryFacade.getInstance().getChild(parentCode);
		if (map == null) {
			return printJson(list, out);
		}
		for (Entry<String, String> m : map.entrySet()) {
			Category c = new Category();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		return printJson(list, out);
	}
	
	/**
	 * 获得省份下的市和区
	 * @param out
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getSq(Map<String, Object> out, String parentCode) throws IOException {
		List<MixCategory> list = new ArrayList<MixCategory>();
		Map<String, String> map = CategoryFacade.getInstance().getChild(parentCode);
		if (map == null) {
			return printJson(list, out);
		}
		for (Entry<String, String> m : map.entrySet()) {
			MixCategory c = new MixCategory();
			c.setCode(m.getKey());
			c.setLabel(m.getValue());
			list.add(c);
		}
		//获得市下面的区
		for (MixCategory mixCategory : list) {
			List<Category> list1 = new ArrayList<Category>();
			Map<String, String> map1 = CategoryFacade.getInstance().getChild(mixCategory.getCode());
			do {
				if (map1 == null) {
					continue;
				}
				for (Entry<String, String> m : map1.entrySet()) {
					Category c = new Category();
					c.setCode(m.getKey());
					c.setLabel(m.getValue());
					list1.add(c);
				}
			} while (false);
			mixCategory.setCategory(list1);
		}
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView doStep2(Map<String,Object> out, HttpServletRequest request, HttpServletResponse response, CompanyInfo info, CompanyAccount account){
		//获取注册用户ip 
		account.setIp(HttpUtils.getInstance().getIpAddr(request));
		Integer cid = companyInfoService.insertCompanyInfo(info);
		if(cid > 0){
			//帐号信息处理
			account.setCompanyId(cid);
			Integer aid = companyAccountService.insertAccount(account);
			if(aid < 1){
				return new ModelAndView("redirect:"+"/register/step1.htm");
			}
		}
		//注册成功后，保存登录信息
		String ticket = null;
		try {
			 // TODO 增加私钥以保证安全
			ticket = MD5.encode(account.getAccount()+account.getPassword()+account.getIp());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//登录信息
		SsoUser ssoUser = new SsoUser();
		ssoUser.setAccount(account.getAccount());
		ssoUser.setAccountId(account.getId());
		ssoUser.setCompanyId(account.getCompanyId());
		HttpUtils.getInstance().setCookie(response,SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
		//6小时内有效
		MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, ssoUser);
		setSessionUser(request, ssoUser);
		return new ModelAndView("redirect:"+"/register/step3.htm");
	}
	
	/**
	 * 注册第三步
	 */
	@RequestMapping
	public ModelAndView step3(Map<String,Object> out, HttpServletRequest request, HttpServletResponse response){
//		SsoUser user = getCachedUser(request);
		return new ModelAndView();
	}
	
}
