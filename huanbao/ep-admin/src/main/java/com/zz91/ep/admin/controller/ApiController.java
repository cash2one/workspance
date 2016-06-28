/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-4
 */
package com.zz91.ep.admin.controller;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.admin.service.crm.CRMRightService;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.admin.service.trade.MessageService;
import com.zz91.ep.admin.service.trade.TradeBuyService;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.crm.CrmCompany;
import com.zz91.ep.domain.trade.Message;
import com.zz91.util.auth.ep.BasicEpAuthUser;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-4
 */
@Controller
public class ApiController extends BaseController {
	
	@Resource 
	private CompAccountService compAccountService;
	@Resource 
	private CompProfileService compProfileService;
	@Resource 
	private CRMRightService crmRightService;
	@Resource 
	private ParamService paramService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	@Resource
	private TradeBuyService tradeBuyService;
	@Resource
	private MessageService messageService;
	
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
	
	@RequestMapping
	public ModelAndView todayUpdateCompany(HttpServletRequest request, Map<String, Object> out,String date,Integer start,Integer limit) throws ParseException{
		Date date2=null;
		if (date!=null){
			date2=DateUtil.getDate(date, "yyyy-MM-dd");
		}else {
			date2=DateUtil.getDate(new Date(), "yyyy-MM-dd");
		}
		List<CrmCompany> list= compProfileService.queryTodayUpdateCompany(date2,start,limit);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView todayUpdateCompanyCount(HttpServletRequest request, Map<String, Object> out,String date) throws ParseException{
		Date date2=null;
		if (date!=null){
			date2=DateUtil.getDate(date, "yyyy-MM-dd");
		}else {
			date2=DateUtil.getDate(new Date(), "yyyy-MM-dd");
		}
		Integer count= compProfileService.queryTodayUpdateCompanyCount(date2);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("totals", count);
		return printJson(map, out);
	}
	
	/**
	 * 最大导入公司信息ID
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView maxCompanyId(HttpServletRequest request, Map<String, Object> out){
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer maxid = compProfileService.queryMaxId();
		map.put("maxid", maxid);
		return printJson(map, out);
	}
	
	/**
	 * 最大导入帐号信息ID
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView maxAccountId(HttpServletRequest request, Map<String, Object> out){
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer maxid = compAccountService.queryMaxId();
		map.put("maxid", maxid);
		return printJson(map, out);
	}
	
	/**
	 * 最大导入供应信息ID
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView maxSupplyId(HttpServletRequest request, Map<String, Object> out){
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer maxid = tradeSupplyService.queryMaxId();
		map.put("maxid", maxid);
		return printJson(map, out);
	}
	
	/**
	 * 最大导入求购信息ID
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView maxBuyId(HttpServletRequest request, Map<String, Object> out){
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer maxid = tradeBuyService.queryMaxId();
		map.put("maxid", maxid);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView params(HttpServletRequest request, Map<String, Object> out,String types){
		List<Param> list = paramService.queryParamByType(types);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView compfileMessageCount(HttpServletRequest request, Map<String, Object> out
			,String messageTime,Integer id){
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer total = messageService.queryCompfileMessageCount();
		map.put("total", total);
		return printJson(map, out);
	}
	@RequestMapping
	public ModelAndView compfileMessageTime(HttpServletRequest request, Map<String, Object> out
			,Integer start,Integer size){
		List<Message> list = messageService.queryCompfileMessageTime(start, size);
		return printJson(list, out);
	}
}
