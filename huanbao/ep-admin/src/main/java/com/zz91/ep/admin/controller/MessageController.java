package com.zz91.ep.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.service.comp.CompAccountService;
import com.zz91.ep.admin.service.trade.MessageService;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;
import com.zz91.util.mail.MailUtil;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-1 
 */
@Controller
public class MessageController extends BaseController{
	@Resource
	private MessageService messageService;
	@Resource
	private CompAccountService compAccountService;
	
	@RequestMapping
	public ModelAndView supplyMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryMessage(Map<String, Object> out,HttpServletRequest request,Integer targetId,
			Integer cid,Integer targetCid,String targetType,PageDto<MessageDto> page){
		if (targetType.contains(",")){
			String[] str=targetType.split(",");
			targetType=str[1];
		}
		if (targetType==null || targetType==""){
			targetType="0";
		}
		page=messageService.pageAllMessage(cid,targetType, targetId, targetCid, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		Message message=messageService.queryShortMessageById(id);
		return printJson(message, out);
	}
	
	@RequestMapping
	public ModelAndView compMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView buyMessage(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView sendMessage(Map<String, Object> out,HttpServletRequest request,Message message){
		ExtResult result = new ExtResult();
		message.setUid(0);
		message.setDelSendStatus((short)1);
		Integer i=messageService.sendMessageByUser(message, (int)message.getTargetType());
		if (i!=null && i.intValue()>0) {
    	  //向询盘留言接收者发送邮件
    	  CompAccount compAccount = compAccountService.getCompAccountByCid(message.getTargetCid());
    	  Map<String, Object> map = new HashMap<String, Object>();
    	  MailUtil.getInstance().sendMail(MailArga.TITLE_XUNPAN, compAccount.getEmail(),  MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
  				MailArga.TEMPLATE_XUNPAN_CODE, map, MailUtil.PRIORITY_HEIGHT);
	      result.setSuccess(true);
		}
		return printJson(result, out);
	}
}
