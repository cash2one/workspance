/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.zz91.ep.api.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.crm.CRMRightService;
import com.zz91.util.auth.ep.BasicEpAuthUser;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-5-4
 */
@Controller
public class ApiController extends BaseController {

	@Resource 
	private CompAccountService compAccountService;
	@Resource 
	private CompProfileService compProfileService;
	@Resource 
	private CRMRightService crmRightService;
	
	@Deprecated
	@RequestMapping
	public ModelAndView epAuthUser(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String a, String p, String project, String ip){
		EpAuthUser epAuthUser = null;
		BasicEpAuthUser basicEpAuthUser = null;
		do {
			//验证用户登录信息是否正确
			CompAccount compAccount=null;
			try {
				compAccount=compAccountService.validateAccount(a, p, ip);
			} catch (Exception e) {
				//e.printStackTrace();
				break;
			}
			epAuthUser = new EpAuthUser();
			basicEpAuthUser = new BasicEpAuthUser();
			
			epAuthUser.setUid(compAccount.getId());
			epAuthUser.setCid(compAccount.getCid());
			epAuthUser.setAccount(compAccount.getAccount());
			epAuthUser.setLoginName(compAccount.getName());
			
			basicEpAuthUser.setUid(epAuthUser.getUid());
			basicEpAuthUser.setCid(epAuthUser.getCid());
			
			epAuthUser = initEpAuthUser(epAuthUser, project);

			basicEpAuthUser.setMemberCode(epAuthUser.getMemberCode());
			basicEpAuthUser.setAccount(epAuthUser.getAccount());
			basicEpAuthUser.setLoginName(epAuthUser.getLoginName());
			
			String key=UUID.randomUUID().toString();
			String ticket="";
			try {
				ticket=MD5.encode(a+p+project+key);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			epAuthUser.setKey(key);
			epAuthUser.setTicket(ticket);
			
			MemcachedUtils.getInstance().getClient().set(ticket, 6*60*60, basicEpAuthUser);
			
		} while (false);
		//put basic epAuthUser to cache
		//put epAuthUser with crmRight to session
		return printJson(epAuthUser, out);
	}
	
	@Deprecated
	@RequestMapping
	public ModelAndView epAuthTicket(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String project, String t){
		EpAuthUser epAuthUser = null;
		do {
			BasicEpAuthUser user=(BasicEpAuthUser) MemcachedUtils.getInstance().getClient().get(t);
			if(user==null ){
				break;
			}
			
			epAuthUser = new EpAuthUser();
			epAuthUser.setUid(user.getUid());
			epAuthUser.setCid(user.getCid());
			epAuthUser.setAccount(user.getAccount());
			epAuthUser.setLoginName(user.getLoginName());
			
			epAuthUser=initEpAuthUser(epAuthUser, project);
			
			MemcachedUtils.getInstance().getClient().set(t, 6*60*60, user); //续期
			
		} while (false);
		return printJson(epAuthUser, out);
	}
	
	
	@RequestMapping
	public ModelAndView epAuthLogout(HttpServletRequest request, Map<String, Object> out, String t){
		MemcachedUtils.getInstance().getClient().delete(t);
		return printJson("{result:true}", out);
	}
	/**
	 * 初始化EpAuthUser对象
	 * @param account
	 * @param projectCode
	 * @return
	 */
	@Deprecated
	private EpAuthUser initEpAuthUser(EpAuthUser epAuthUser, String projectCode) {
		
		
		CompProfile compProfile = compProfileService.queryCompProfileById(epAuthUser.getCid());
		//如果memberCodeBlock存在，则通过memberCodeBlock查询所拥有的权限
		if(StringUtils.isNotEmpty(compProfile.getMemberCodeBlock())) {
			epAuthUser.setMemberCode(compProfile.getMemberCodeBlock());
		} else {
			epAuthUser.setMemberCode(compProfile.getMemberCode());
		}
		String[] crmRightList = crmRightService.getCrmRightListByCompanyIdAndMemberCode(compProfile.getId(), compProfile.getMemberCode(), compProfile.getMemberCodeBlock(), projectCode);
		epAuthUser.setRightList(crmRightList);
		return epAuthUser;
	}
	
	 
	
	
	/**
	 * 
	 * @param request
	 * @param out
	 * @param ac
	 * @param pwd
	 * @param ip
	 * @param project
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView validationUser(HttpServletRequest request,
			Map<String, Object> out, String a, String p, String ip,
			String project) throws IOException {

		Map<String, Object> result = new HashMap<String, Object>();
		EpAuthUser epAuthUser = null;
		do {
			// 验证用户登录信息是否正确
			Integer cid = null;
			try {
				cid = compAccountService.validateUser(a, p);
			} catch (NoSuchAlgorithmException e1) {
			} catch (UnsupportedEncodingException e1) {
			} catch (AuthorizeException e1) {
				result.put("error", e1.getMessage());
			}

			if (cid==null ||cid.intValue()<=0) {
				break;
			}
			
			epAuthUser = compAccountService.initEpAuthUser(cid, project);
			
			if (epAuthUser == null) {
				result.put("error", AuthorizeException.ERROR_SERVER);
				break;
			}
			//取得权限
			String [] rightList = compAccountService.getAuthListByCompanyIdAndMemberCode(epAuthUser.getCid(),epAuthUser.getMemberCode(), project);
			if(rightList==null||"".equals(rightList)){
				result.put("error", AuthorizeException.ERROR_SERVER);
				break;
			}
			// 生成ticket
			String key = UUID.randomUUID().toString();
			String ticket = null;
			try {
				ticket = MD5.encode(a + p + project + key);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			result.put("key", key);
			result.put("ticket", ticket);
			result.put("rightList",rightList);
			
			
		/*	compAccountService.updateLoginInfo(epAuthUser.getUid(), ip);
			LogUtil.getInstance().log(String.valueOf(epAuthUser.getCid()),
					"login", ip);*/
			
//			epAuthUser.setTicket(ticket);
//			epAuthUser.setKey(key);
			// 保存6小时
			MemcachedUtils.getInstance().getClient().set(ticket, 6 * 60 * 60, epAuthUser);
			result.put("epAuthUser", epAuthUser);
		} while (false);

		return printJson(result, out);
	}
	
	
	/**
	 * 
	 * @param request
	 * @param out
	 * @param project
	 * @param t
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView validationTicket(HttpServletRequest request, Map<String, Object> out, String project, String t) throws IOException{
		
		Map<String, Object> result=null;
		if(StringUtils.isEmpty(t)){
			return printJson(result, out);
		}
		
		EpAuthUser epAuthUser = (EpAuthUser) MemcachedUtils.getInstance().getClient().get(t);
		
		do {
			if(epAuthUser==null){
				break;
			}
			result = new HashMap<String, Object>();
			String key = UUID.randomUUID().toString();
			String vticket = null;
			try {
				vticket = MD5.encode(key+project+t);
			} catch (NoSuchAlgorithmException e) {
			} catch (UnsupportedEncodingException e) {
			}
			result.put("vticket", vticket);
			result.put("key", key);
			
			//根据项目重新取
			String [] rightList = compAccountService.getAuthListByCompanyIdAndMemberCode(epAuthUser.getCid(),epAuthUser.getMemberCode(), project);
			result.put("rightList",rightList);
			result.put("epAuthUser", epAuthUser);
			
		} while (false);
		return printJson(result, out);
	}
}
