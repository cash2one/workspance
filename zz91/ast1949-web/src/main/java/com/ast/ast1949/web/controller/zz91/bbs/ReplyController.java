/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-24
 */
package com.ast.ast1949.web.controller.zz91.bbs;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.bbs.BbsPostReplyService;
import com.ast.ast1949.service.bbs.BbsScoreService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.log.LogUtil;

/**
 * 帖子管理
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Controller
public class ReplyController extends BaseController {
	@Resource
	private BbsPostReplyService bbsPostReplyService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private BbsService bbsService;
	@Resource
	private BbsScoreService bbsScoreService;
	
	final static String BBS_REPLY_SUCCESS_OPERTION = "bbs_reply_success";
	final static String BBS_REPLY_FAILURE_OPERTION = "bbs_reply_failure";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@RequestMapping
	public void index(Map<String, Object> out) {
		
	}
	
//	@RequestMapping
//	public ModelAndView queryReplyByPost(HttpServletRequest request, Map<String, Object> out, 
//			Integer postId, String checkStatus, String isDel, PageDto<BbsPostReplyDO> page) throws IOException{
//		//TODO 根据贴子查找回贴
//		page = bbsPostReplyService.pageReplyByAdmin(postId, null, page);
//		return printJson(page, out);
//	}
	
	@RequestMapping
	public ModelAndView queryReply(HttpServletRequest request, Map<String, Object> out, 
			BbsPostReplyDO reply, PageDto<BbsPostReplyDO> page) throws IOException{
		if(page.getSort()==null){
			page.setSort("gmt_created");
			page.setDir("desc");
		}
		page=bbsPostReplyService.pageReplyByAdmin(reply, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request, Map<String, Object> out,
			Integer id, String checkStatus, String account,Integer companyId) throws IOException, ParseException{
		SessionUser sessionUser=getCachedUser(request);
		Integer i=bbsPostReplyService.updateCheckStatus(id, checkStatus, sessionUser.getAccount());
		
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			//重新计算一遍主贴回复数--只算审核通过的
			Integer bbsPostId=bbsPostReplyService.queryBbsPostIdByReplyId(id);
			Integer replyCount = bbsPostReplyService.queryReplyOfPostCount(bbsPostId, null);
			bbsService.updateReplyCount(bbsPostId, replyCount);
			// 日志系统 记录审核互助帖子回复情况
			if("1".equals(checkStatus)){
			//	bbsUserProfilerService.updateBbsReplyCount(account);
				// 用户信息回复数加1
				bbsService.updateBbsUserProfilerReplyNumber(account);
				// 主贴信息回复数加1
				/*Integer bbsPostId=bbsPostReplyService.queryBbsPostIdByReplyId(id);
				bbsService.updateBbsPostReplyCount(bbsPostId);*/
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(companyId, null, "get_post_bbs", null, id, null));
				LogUtil.getInstance().mongo(sessionUser.getAccount(), BBS_REPLY_SUCCESS_OPERTION,null, "{'id':'"+id+"','companyId':'"+companyId+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
				
				// 计算回复积分
				bbsScoreService.checkReply(id, 1);
			}else{
				LogUtil.getInstance().mongo(sessionUser.getAccount(), BBS_REPLY_FAILURE_OPERTION,null, "{'id':'"+id+"','companyId':'"+companyId+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
				// 计算回复积分
				bbsScoreService.checkReply(id, 0);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateReply(HttpServletRequest request, Map<String, Object> out,
			BbsPostReplyDO reply) throws IOException{
		Integer i=bbsPostReplyService.updateReplyByAdmin(reply);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request, Map<String, Object> out,
			Integer id,String account) throws IOException{
		// 主贴信息回复数减1
		Integer ids=bbsPostReplyService.queryBbsPostIdByReplyId(id);
		bbsService.updateBbsPostReplyCountForDel(ids);
		Integer i=bbsPostReplyService.deleteByAdmin(id);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			// 用户信息回复数减1
			bbsService.updateBbsUserProfilerReplyNumberForDel(account);
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView createReply(HttpServletRequest request, Map<String, Object> out,
			BbsPostReplyDO reply) throws IOException{
		SessionUser sessionUser = getCachedUser(request);
		Integer i = bbsPostReplyService.createReplyByAdmin(reply,sessionUser.getAccount());
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
}
