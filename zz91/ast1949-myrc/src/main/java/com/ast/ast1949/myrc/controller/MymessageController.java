/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31
 */
package com.ast.ast1949.myrc.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.company.InquiryGroup;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.CrmSvrService;
import com.ast.ast1949.service.company.InquiryGroupService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.phone.PhoneService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */
@Controller
public class MymessageController extends BaseController {

	@Resource
	private InquiryService inquiryService;
	@Resource
	private InquiryGroupService inquiryGroupService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyService companyService;
	@Resource
	private MyrcService myrcService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private PhoneService phoneService;
	/**
	 * 收信箱
	 * 
	 * @param request
	 * @param out
	 * @param p
	 *            当前页
	 * @param inquiry
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView inbox(HttpServletRequest request, Map<String, Object> out,
			PageDto<InquiryDto> page, Integer groupId, String isViewed, String isRubbish,String inquiredType,String isReplyed,String keywords) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		if(StringUtils.isEmpty(isRubbish)){
			isRubbish="0";
		}
		
		if (StringUtils.isNotEmpty(keywords)) {
			if (StringUtils.isContainCNChar(keywords)) {
				keywords = StringUtils.decryptUrlParameter(keywords);
			}
		}else{
			keywords="";
		}
		
		out.put("groupId", groupId);
		out.put("isViewed", isViewed);
		out.put("isRubbish", isRubbish);
		out.put("inquiredType", inquiredType);
		out.put("isReplyed", isReplyed);
		out.put("keywords", keywords);
		out.put("keywordsEN", URLEncoder.encode(keywords, HttpUtils.CHARSET_UTF8));
		
		//用户所有的分组
		out.put("systemGroupList", inquiryGroupService.querySystemGroup());
		out.put("myGroupList", inquiryGroupService.queryGroupOfAccount(sessionUser.getAccount()));
		
		Inquiry inquiry = new Inquiry();
		inquiry.setGroupId(groupId);
		inquiry.setTitle(keywords);
		inquiry.setIsViewed(isViewed);
		inquiry.setIsReceiverDel("0");
		inquiry.setIsRubbish(isRubbish);
		inquiry.setInquiredType(inquiredType);
		inquiry.setIsReplyed(isReplyed);
		inquiry.setReceiverAccount(sessionUser.getAccount());
		page=inquiryService.pageInquiryByUser(inquiry, page);
		
		out.put("page", page);
		
		return null;
	}

	/**
	 * 垃圾箱
	 * 
	 * @param request
	 * @param out
	 * @param p
	 *            当前页
	 * @param inquiry
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView rubbish(HttpServletRequest request, Map<String, Object> out, PageDto<InquiryDto> page,
//			String isViewed) {
//		out.put(FrontConst.MYRC_SUBTITLE, "垃圾留言");
//		
//		SsoUser sessionUser = getCachedUser(request);
//		
//		Inquiry inquiry = new Inquiry();
//		inquiry.setIsViewed(isViewed);
//		inquiry.setIsRubbish("1");
//		inquiry.setIsReceiverDel("0");
//		inquiry.setReceiverAccount(sessionUser.getAccount());
//		page=inquiryService.pageInquiryByUser(inquiry, page);
//		
//		out.put("isViewed", isViewed);
//		
//		out.put("page", page);
//		
//		return null;
//
//	}

	/**
	 * 发信箱
	 * 
	 * @param request
	 * @param out
	 * @param p
	 * @param inquiry
	 * @return
	 */
	@RequestMapping
	public ModelAndView sent(HttpServletRequest request, Map<String, Object> out, Integer error,
			PageDto<InquiryDto> page, String isViewed) {
		
		SsoUser sessionUser = getCachedUser(request);
		//查询是否开通商铺服务
		myrcService.initMyrc(out, sessionUser.getCompanyId());
		
		Inquiry inquiry = new Inquiry();
		inquiry.setIsViewed(isViewed);
		inquiry.setIsSenderDel("0");
		inquiry.setSenderAccount(sessionUser.getAccount());
		page=inquiryService.pageInquiryBySender(sessionUser.getAccount(), page);
		
		out.put("page", page);
		
		out.put("error", error);
		
		return null;

	}
	
	@RequestMapping
	public ModelAndView spamInquiry(Map<String, Object> model, String ids, String isRubbish) throws IOException {
		ExtResult result = new ExtResult();
		Integer impact=0;
		Integer[] idArr=StringUtils.StringToIntegerArray(ids);
		boolean spam=true;
		if("0".equals(isRubbish)){
			spam = false;
		}
		for(Integer id:idArr){
			inquiryService.spamInquiry(id, spam);
			impact++;
		}
		if(impact.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
	
//	@RequestMapping
//	public ModelAndView spamCompany(Map<String, Object> model, String account) throws IOException {
//		ExtResult result = new ExtResult();
//		
//		return printJson(result, model);
//	}


	/**
	 * 删除信息
	 * 
	 * @param ids
	 *            要删除信息的ID
	 * @param isdel
	 *            删除标记
	 * @param url
	 *            删除成功，返回URL
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView removeReceiveInquiry( String ids, Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		Integer impact=0;
		Integer[] idArr=StringUtils.StringToIntegerArray(ids);
		for(Integer id:idArr){
			inquiryService.removeReceivedInquiry(id, true);
			impact++;
		}
		if(impact.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}
	
	@RequestMapping
	public ModelAndView removeSentInquiry( String ids, Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		Integer impact=0;
		Integer[] idArr=StringUtils.StringToIntegerArray(ids);
		for(Integer id:idArr){
			inquiryService.removeSentInquiry(id, true);
			impact++;
		}
		if(impact.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	/**
	 * 查看询盘信息详情
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param p
	 *            当前页
	 * @return
	 */
	@RequestMapping
	public ModelAndView showmessage(HttpServletRequest request, Map<String, Object> out, 
			Integer id, String inbox, Integer error) {
		
		SsoUser ssoUser = getCachedUser(request);
		
		out.put("id", id);
		out.put("inbox", inbox);
		
		Inquiry inquiry=inquiryService.queryOneInquiry(id);
		out.put("inquiry", inquiry);
		out.put(FrontConst.MYRC_SUBTITLE, "留言："+inquiry.getTitle());

		CompanyAccount account=companyAccountService.queryAccountByAccount(inquiry.getSenderAccount());
		out.put("sender", account);
		Company senderCompany = companyService.queryCompanyById(account.getCompanyId());
		out.put("senderCompany", senderCompany);
		Phone phone = phoneService.queryByCompanyId(senderCompany.getId());
		if(phone!=null&&StringUtils.isNotEmpty(phone.getTel())&&phone.getExpireFlag()==0){
			out.put("ldbTel", phone.getTel());
		}

		out.put("conversationList", inquiryService.queryConversation(inquiry.getConversationGroup()));
		if(StringUtils.isEmpty(inquiry.getIsViewed()) || "0".equals(inquiry.getIsViewed())){
			inquiryService.makeAsViewed(id, true);
		}

		out.put("error", error);
		
		//判断是否是信息完善的客户
		String checkInfo=companyService.validateCompanyInfo(ssoUser);
		out.put("checkInfo", checkInfo);
		
		
		// 普会接收普会询盘 联系方式 屏蔽 判断
		do{
			
			// 不是普会 不屏蔽
			if(!CrmSvrService.PT_CODE.equals(ssoUser.getMembershipCode())){
				break;
			}
			
			String raccount = getCachedUser(request).getAccount();
			// 不是接收方 自己发的询盘 自己联系方式 不屏蔽
			if(!raccount.equals(inquiry.getReceiverAccount())){
				break;
			}
			
			// 发送方是高会
			CompanyAccount ca = companyAccountService.queryAccountByAccount(inquiry.getSenderAccount());
			if(!CrmSvrService.PT_CODE.equals(companyService.querySimpleCompanyById(ca.getCompanyId()).getMembershipCode())){
				break;
			}
			
			// 享有简版再生通服务，可查看
			boolean isView = crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.JBZST_CODE);
			if(isView){
				break;
			}
			
			// 屏蔽查看
			out.put("isForbid", 1);
			
			
		}while(false);
		
//		SsoUser sessionUser = getCachedUser(request);
//		if(sessionUser.getAccount().equals(inquiry.getSenderAccount())){
//			//上一篇留言
//			Inquiry onInquiry = inquiryService.queryOnMessageById(id, null, sessionUser.getAccount());
//			out.put("onInquiry", onInquiry);
//			//下一篇留言
//			Inquiry downInquiry = inquiryService.queryDownMessageById(id, null, sessionUser.getAccount());
//			out.put("downInquiry", downInquiry);
//		}else {
//			//上一篇留言
//			Inquiry onInquiry = inquiryService.queryOnMessageById(id,  sessionUser.getAccount(), null);
//			out.put("onInquiry", onInquiry);
//			//下一篇留言
//			Inquiry downInquiry = inquiryService.queryDownMessageById(id,  sessionUser.getAccount(), null);
//			out.put("downInquiry", downInquiry);
//		}
		
//		page=inquiryService.pageAllConversation(inquiry.getSenderAccount(), inquiry.getReceiverAccount(), page);
//		out.put("page", page);
		
		return null;

	}

	/**
	 * 回复留言
	 * 
	 * @param model
	 * @param request
	 * @param inquiryDO
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView replyInquiry(Map<String, Object> out, HttpServletRequest request,
			Inquiry inquiry) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		inquiry.setSenderAccount(sessionUser.getAccount());
		Integer i=inquiryService.replyInquiry(inquiry, sessionUser.getCompanyId());
		if(i!=null && i.intValue()>0){
			out.put("error", 0);
			return new ModelAndView("redirect:sent.htm");
		}
		out.put("error", 1);
		out.put("id", inquiry.getId());
		return new ModelAndView("redirect:showmessage.htm");
	}

	/**
	 * 留言组列表
	 * 
	 * @param out
	 * @param id
	 *            公司Id
	 * @param action
	 * @param inquiryId
	 *            询盘Id
	 * @return
	 */
	@RequestMapping
	public ModelAndView groups(Map<String, Object> out, HttpServletRequest request, String error) {
		SsoUser sessionUser = getCachedUser(request);
		out.put("groups", inquiryGroupService.queryGroupOfAccount(sessionUser.getAccount()));
		out.put("sysGroups", inquiryGroupService.querySystemGroup());
		out.put("error", error);
		return null;
	}
	
	@RequestMapping
	public ModelAndView groupInquiry(HttpServletRequest request, Map<String, Object> out,
			String ids, Integer error){
		out.put("ids", ids);
		out.put("error", error);
		out.put("groups", inquiryGroupService.queryGroupOfAccount(getCachedUser(request).getAccount()));
		out.put("sysGroups", inquiryGroupService.querySystemGroup());
		return null;
	}
	
	@RequestMapping
	public ModelAndView doGroupInquiry(HttpServletRequest request, Map<String, Object> out, 
			String ids, Integer groupId) throws IOException {
		Integer[] inquiryIds=StringUtils.StringToIntegerArray(ids);
		Integer impact=0;
		for(Integer id:inquiryIds){
			Integer i = inquiryService.groupInquiry(id, groupId);
			if(i!=null && i.intValue()>0){
				impact++;
			}
		}
		if(impact>0){
			return new ModelAndView("redirect:"+request.getContextPath()+"/submitCallback.htm");
		}
		out.put("ids", ids);
		out.put("error", 1);
		return new ModelAndView("redirect:groupInquiry.htm");
	}
	
	@RequestMapping
	public ModelAndView checkGroupExists(Map<String, Object>out,String ids) throws IOException{
		ExtResult result = new ExtResult();
		Integer[] inquiryIds=StringUtils.StringToIntegerArray(ids);
		for (Integer id:inquiryIds) {
			Inquiry inquiry = inquiryService.queryOneInquiry(id);
			if (inquiry!=null&&inquiry.getGroupId()!=null&&!inquiry.getGroupId().equals(0)) {
				result.setSuccess(true);
				break;
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView doUnGroupInquiry(Map<String, Object> out, String ids) throws IOException {
		Integer[] inquiryIds=StringUtils.StringToIntegerArray(ids);
		Integer impact=0;
		for(Integer id:inquiryIds){
			Integer i = inquiryService.groupInquiry(id, 0);
			if(i!=null && i.intValue()>0){
				impact++;
			}
		}
		ExtResult result = new ExtResult();
		if(impact>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createGroup(HttpServletRequest request, Map<String, Object> out){
		return null;
	}

	/**
	 * 添加分组
	 * 
	 * @param out
	 * @param inquiryGroupDO
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doCreateGroup(HttpServletRequest request, Map<String, Object> out,
			InquiryGroup inquiryGroup) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		inquiryGroup.setAccount(sessionUser.getAccount());
		inquiryGroup.setCompanyId(sessionUser.getCompanyId());
		Integer i=inquiryGroupService.createGroup(inquiryGroup);
		if(i!=null && i.intValue()>0){
			out.put("error", 2);
			return new ModelAndView("redirect:groups.htm");
		}
		out.put("error", 1);
		return new ModelAndView("redirect:createGroup.htm");
	}
	
	@RequestMapping
	public ModelAndView updateGroup(HttpServletRequest request, Map<String, Object> out, Integer id, String name){
		out.put("id", id);
		out.put("name", inquiryGroupService.queryName(id));
		return null;
	}
	
	@RequestMapping
	public ModelAndView doUpdateGroup(HttpServletRequest request, Map<String, Object> out, InquiryGroup group) throws IOException{
		SsoUser sessionUser = getCachedUser(request);
		group.setAccount(sessionUser.getAccount());
		Integer i=inquiryGroupService.updateGroup(group);
		if(i!=null && i.intValue()>0){
			out.put("error", 2);
			return new ModelAndView("redirect:groups.htm");
		}
		out.put("error", 1);
		return new ModelAndView("redirect:updateGroup.htm");
	}

	/**
	 * 删除分组
	 * 
	 * @param out
	 * @param id
	 *            要删除的组Id
	 * @param cid
	 *            公司Id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView deleteGroup(Map<String, Object> out, HttpServletRequest request, Integer id)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		Integer i=inquiryGroupService.deleteGroup(id, sessionUser.getAccount());
		if(i!=null&&i.intValue()>0){
			out.put("error", 2);
		}
		return new ModelAndView("redirect:groups.htm");
	}

}
