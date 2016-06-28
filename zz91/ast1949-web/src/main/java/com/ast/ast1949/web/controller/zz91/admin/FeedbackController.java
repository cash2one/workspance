/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-11.
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.site.FeedbackDto;
import com.ast.ast1949.service.site.FeedbackService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.sms.SmsUtil;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Controller
public class FeedbackController extends BaseController {
	@Autowired
	FeedbackService feedbackService;

	@RequestMapping
	public void view(Map<String, Object> model, HttpServletRequest request) {

	}

	/**
	 * 读取数据
	 * 
	 * @param model
	 * @param feedback
	 *            查询条件
	 * @param page
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model,
			HttpServletRequest request, FeedbackDo feedback, String onlyme,
			PageDto<FeedbackDto> page) throws IOException {
		// if (page == null) {
		// page = new PageDto<FeedbackDto>(AstConst.PAGE_SIZE);
		// } else {
		// if (page.getPageSize() == null) {
		// page.setPageSize(AstConst.PAGE_SIZE);
		// }
		// }

		// if (feedback.getCategory() != null) {
		// if (feedback.getCategory() < 0) {
		// feedback.setCategory(null);
		// }
		// } else {
		// feedback.setCategory(FeedbackService.CATEGORY_VIP);
		// }

		if (onlyme != null && "Y".equals(onlyme)) {
			feedback.setCheckPerson(getCachedUser(request).getAccount());
		}

		page = feedbackService.pageFeedbackByAdmin(feedback, page);

		return printJson(page, model);
	}

	/**
	 * 读取我的客户留言
	 * 
	 * @param request
	 * @param model
	 * @param feedback
	 * @param page
	 * @return
	 * @throws IOException
	 */
	// @RequestMapping
	// public ModelAndView queryOnlyMe(HttpServletRequest request, Map<String,
	// Object> model,
	// FeedbackDo feedback, PageDto<FeedbackDto> page) throws IOException {
	// if (page == null) {
	// page = new PageDto<FeedbackDto>(AstConst.PAGE_SIZE);
	// } else {
	// if (page.getPageSize() == null) {
	// page.setPageSize(AstConst.PAGE_SIZE);
	// }
	// }
	//
	// // TODO 客户留言功能未完善
	// List<FeedbackDto> list=new ArrayList<FeedbackDto>();
	// page.setRecords(list);
	//
	// // SessionUser sessionUser = getCachedUser(request);
	// // if (sessionUser != null) {
	// // page = feedbackService.pageFeedbackByCrm(feedback, user.getId(),
	// page);
	// // }
	//
	// return printJson(page, model);
	// }

	/**
	 * 回复
	 * 
	 * @param model
	 * @param request
	 * @param feedback
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView reply(Map<String, Object> model,
			HttpServletRequest request, FeedbackDo feedback) throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		Integer im = feedbackService.replyFeedback(feedback.getId(),
				feedback.getReplyContent(), sessionUser.getAccount());
		if (im.intValue() > 0) {
			result.setSuccess(true);
		}

		return printJson(result, model);
	}

	/**
	 * 读取一条留言信息
	 * 
	 * @param model
	 * @param id
	 *            编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView querySimpleFeedbackById(Map<String, Object> model,
			String id) throws IOException {
		PageDto<FeedbackDo> p = new PageDto<FeedbackDo>();
		if (StringUtils.isNumber(id)) {
			List<FeedbackDo> list = new ArrayList<FeedbackDo>();
			FeedbackDo feedback = feedbackService.queryFeedbackById(Integer
					.parseInt(id));
			list.add(feedback);
			p.setRecords(list);
		}
		return printJson(p, model);
	}

	/**
	 * 不处理
	 * 
	 * @param model
	 * @param request
	 * @param id
	 *            编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView notReply(Map<String, Object> model,
			HttpServletRequest request, String id) throws IOException {
		ExtResult result = new ExtResult();

		if (StringUtils.isNumber(id)) {
			SessionUser sessionUser = getCachedUser(request);
			if (feedbackService.notReplyFeedback(Integer.parseInt(id),
					sessionUser.getAccount()).intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param id
	 *            编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model, String id)
			throws IOException {
		ExtResult result = new ExtResult();
		if (StringUtils.isNumber(id)) {
			if (feedbackService.deleteFeedbackByAdmin(Integer.parseInt(id))
					.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 短信发送
	 * 
	 * @param model
	 * @param request
	 * @param feedback
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView sms(Map<String, Object> out,
			HttpServletRequest request, String mobiles, String content)
			throws IOException {
		if(StringUtils.isEmpty(content)&&content.length()>60){
			content = content.substring(0, 60);
		}
		ExtResult result = new ExtResult();
			SessionUser sessionUser = getCachedUser(request);
			String[] mobile = mobiles.split(",");
			JSONObject js = new JSONObject();
			js.put("account", sessionUser.getAccount());
			js.put("date", new Date());
			js.put("content", content);
			for (String str : mobile) {
				js.put("mobile", str);
				LogUtil.getInstance().log("crm_cs", "send_sms",HttpUtils.getInstance().getIpAddr(request), js.toString());
				SmsUtil.getInstance().sendSms(str, content+"【ZZ91客服部】","yuexin");
			}
			result.setSuccess(true);
		return printJson(result, out);
	}
}
