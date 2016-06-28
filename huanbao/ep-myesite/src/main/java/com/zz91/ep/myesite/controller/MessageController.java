package com.zz91.ep.myesite.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.trade.Message;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.MailArga;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.MessageDto;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.trade.MessageService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.sms.SmsUtil;

@Controller
public class MessageController extends BaseController {
	
	@Resource
	private MessageService messageService;
	@Resource
	private CompAccountService compAccountService;
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private MyEsiteService myEsiteService;
	
//	/**
//     * 首页
//     * @param out
//     * @param request
//     * @return
//     */
//    @RequestMapping
//    public ModelAndView index(Map<String, Object> out, HttpServletRequest request) {
//    	return new ModelAndView("redirect:receiveMessage.htm");
//    }
    
	@RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request,
    		Integer type,Integer readStatus, Integer replyStatus, 
    		PageDto<MessageDto> page){
    	do {
//    		page.setLimit(3);
    		page = messageService.queryMessagesByCompany(getCompanyId(request), MessageService.SENDSTATUS_RECEIVE, null,type,replyStatus,readStatus,page);
    		
    		out.put("page", page);
    		out.put("type", type);
    		
    		out.put("readStatus", readStatus);
    		out.put("replyStatus", replyStatus);
    		
    		out.put("sendStatus", MessageService.SENDSTATUS_RECEIVE);
    		
    	} while (false);
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }


	/**
	 * 我收到的留言
     * @param out
     * @param request
     * @param sendStatus 操作的留言 0：发出 1：收到
     * @param tagetId 目标（供求信息）编号（null时不筛选）
     * @param type 消息类型(0：供应留言 1：求购留言 2：公司留言 3：网站留言/问题反馈 4：CRM留言/小秘书)
     * @param replyStatus 0：未回复 1：已回复（null时为所有状态）
     * @param page
     * @return
    */ 
//    @RequestMapping
//    public ModelAndView receiveMessage(Map<String, Object> out, HttpServletRequest request,
//           Integer type, Integer replyStatus,Integer readStatus, PageDto<MessageDto> page) {
//        do {
//            if (type == null) {
//                type = MessageService.TARGET_SUPPLY;
//             }
//            page.setSort("ms.gmt_created");
//            page.setDir("desc");
//            page = messageService.queryMessagesByCompany(getCompanyId(request), MessageService.SENDSTATUS_RECEIVE, null, type, replyStatus,readStatus, page);
//            out.put("messagetype", type);
//            out.put("replyStatus", replyStatus);
//            out.put("page", page);
//            out.put("item", "message");
//        } while (false);
//        return null;
//    }
  
    
    /**
     * 我发出的留言
     * @param out
     * @param request
     * @param tagetId
     * @param type
     * @param replyStatus
     * @param page
     * @return
     */
//    @RequestMapping
//    public ModelAndView sendMessage(Map<String, Object> out, HttpServletRequest request,
//           Integer type, Integer replyStatus, Integer readStatus,PageDto<MessageDto> page) {
//        if (type == null) {
//            type = MessageService.TARGET_SUPPLY;
//         }
//        page.setSort("ms.gmt_created");
//        page.setDir("desc");
//        page = messageService.queryMessagesByCompany(getCompanyId(request), MessageService.SENDSTATUS_SEND, null, type, replyStatus, readStatus,page);
//        out.put("messagetype", type);
//        out.put("replyStatus", replyStatus);
//        out.put("page", page);
//        out.put("item", "message");
//        return null;
//    }

    /**
     * 客服留言
     * @param out
     * @param request
     * @param tagetId
     * @param replyStatus
     * @param page
     * @return
     */
//    @RequestMapping
//    public ModelAndView epMessage(Map<String, Object> out, HttpServletRequest request,
//           Integer replyStatus, Integer readStatus,PageDto<MessageDto> page) {
//    	page.setSort("ms.gmt_created");
//        page.setDir("desc");
//        page = messageService.queryMessagesByCompany(getCompanyId(request), MessageService.SENDSTATUS_SEND, null, MessageService.TARGET_EP, replyStatus,readStatus, page);
//        out.put("page", page);
//        out.put("replyStatus", replyStatus);
//        out.put("item", "message");
//        return null;
//    }
    
    /**
     * 删除留言
     * @param out
     * @param request
     * @param id
     * @return
     */
    @RequestMapping
    public ModelAndView delete(Map<String, Object> out, HttpServletRequest request, Integer id, Integer type) {
        ExtResult result = new ExtResult();
        do{
            if (id == null || type == null) {
                break;
            }
            Integer count = messageService.updateMessageBysendStatus(id, getCompanyId(request), type);
            if (count != null && count > 0) {
                result.setSuccess(true);
            }
        }while(false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return printJson(result, out);
    }

    /**
     * 回复留言
     * @param out
     * @param request
     * @param id
     * @param context
     * @return
     */
    @RequestMapping
    public ModelAndView reply(Map<String, Object> out, HttpServletRequest request, Integer id, String context) {
        ExtResult result = new ExtResult();
        do{
            Integer count = messageService.updateReplyMessage(context, getCompanyId(request), id);
            if (count != null && count > 0) {
                result.setSuccess(true);
            }
        }while(false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return printJson(result, out);
    }
    
    /**
     * 向询盘留言接收者发送邮件
     * @param out
     * @param request
     * @param message
     * @param refurl
     * @return
     */
    @RequestMapping
    public ModelAndView sendInquiry(Map<String, Object> out,HttpServletRequest request, Message message, String refurl,String buyTitle,String categoryName){
    	ExtResult result = new ExtResult();
    	do{
    		EpAuthUser sessionUser=EpAuthUtils.getInstance().getEpAuthUser(request, null);
    		if (sessionUser == null) {
    			break;
    		}
    		
    		message.setCid(sessionUser.getCid());
    		message.setUid(sessionUser.getUid());
    		if(message.getTargetUid()==null && message.getTargetCid()==null){
    		   message.setTargetUid(sessionUser.getUid());
    		   message.setTargetCid(sessionUser.getCid());
    		}
    		Integer i=messageService.sendMessageByUser(message, (int)message.getTargetType());
    		
            if (i!=null && i.intValue()>0) {
            	// 向询盘留言接收者发送邮件
            	CompAccount compAccount = compAccountService.getCompAccountByCid(message.getTargetCid());
            	Map<String, Object> map = new HashMap<String, Object>();
            	map.put("compAccount", compAccount);
            	MailUtil.getInstance().sendMail(MailArga.TITLE_XUNPAN, compAccount.getEmail(),  MailArga.ACCOUNT_DEFAULT_ACCOUNT_CODE,
          				MailArga.TEMPLATE_XUNPAN_CODE, map, MailUtil.PRIORITY_HEIGHT);
            	
            	//询盘发送短信(不包括公司)
          		if (message.getTargetType()==0 || message.getTargetType()==1){
          			CompAccount sendAccount = compAccountService.querySimplyCompAccountByCid(sessionUser.getCid());
          			if (StringUtils.isNotEmpty(compAccount.getMobile())){
          				if (compAccount.getMobile().startsWith("0") && compAccount.getMobile().length()==12){;
          					compAccount.setMobile(compAccount.getMobile().substring(1, 12));
          				}

          				if (compAccount.getMobile().startsWith("86-") && compAccount.getMobile().length()==14){
          					compAccount.setMobile(compAccount.getMobile().substring(3, 14));
          				}
          				
          				if (compAccount.getMobile().startsWith("086-") && compAccount.getMobile().length()==15){
          					compAccount.setMobile(compAccount.getMobile().substring(4, 15));
          				}
          				
          				if (isMobile(compAccount.getMobile())){
          					String content= null;
          					if (message.getTargetType()==0){
          						content=categoryName;
          					}
          					if (message.getTargetType()==1){
          						content=buyTitle;
          					}
          					
          					String[] parm = {compAccount.getName(),sendAccount.getName(),content};
          					SmsUtil.getInstance().sendSms("sms_huanbao", compAccount.getMobile(), null, new Date(), parm);
          				}
          			}
          		}
            	result.setSuccess(true);
            	out.put("flag", 1);
            }
    	}while(false);

    	//out.put("result", result);
        //out.put("refurl", refurl);
    	myEsiteService.init(out, getCachedUser(request).getCid());
        return new ModelAndView("redirect:"+refurl);
    }
    
    public static boolean isMobile(String mobiles){     
      Pattern p = Pattern.compile("^(1(([35][0-9])|(47)|[8][01236789]))\\d{8}$");
      Matcher m = p.matcher(mobiles);     
      return m.matches();     
  } 
    
    
    
    
    @RequestMapping
    public ModelAndView messageDetails(Map<String, Object> out, HttpServletRequest request,Integer id,Integer sendStatus){
    	MessageDto dto = messageService.queryMessageByIdAndSendStatus(id, sendStatus);
    	String name = compProfileService.queryNameById(getCompanyId(request));
    	if(name==null){
    		name="wo";
    	}
    	out.put("name",name);
    	out.put("messageDto", dto);
    	myEsiteService.init(out, getCachedUser(request).getCid());
    	out.put("sendStatus", sendStatus);
    	return null;
    }
    
    /**
     * 回复留言
     * @param out
     * @param request
     * @param id
     * @param context
     * @return
     */
    @RequestMapping
    public ModelAndView doReply(Map<String, Object> out, HttpServletRequest request, Integer id, String replyDetails,Integer sendStatus) {
        do{
            Integer count = messageService.updateReplyMessage(replyDetails, getCompanyId(request), id);
            if (count != null && count > 0) {
            out.put("replyDetails", replyDetails);
            out.put("id", id);
            out.put("sendStatus", sendStatus);
            return new ModelAndView("redirect:messageDetails.htm");
            }
        }while(false);
        myEsiteService.init(out, getCachedUser(request).getCid());
        return new ModelAndView("redirect:index.htm");
    }
    
    /**
     * 我收到的留言
     * @param out
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView send(Map<String, Object> out, HttpServletRequest request,Integer type, PageDto<MessageDto> page){
    do {
    	page.setSort("ms.gmt_created");
    	page.setDir("desc");
    	page = messageService.queryMessagesByCompany(getCompanyId(request), MessageService.SENDSTATUS_SEND, null,type,null,null,page);
    	out.put("page", page);
    	out.put("type", type);
    	out.put("sendStatus", MessageService.SENDSTATUS_SEND);
    	} while (false);
    myEsiteService.init(out, getCachedUser(request).getCid());
    	return null;
    }
    
}
