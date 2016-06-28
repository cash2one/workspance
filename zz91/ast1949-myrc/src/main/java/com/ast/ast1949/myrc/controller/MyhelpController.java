/**
 * 
 */
package com.ast.ast1949.myrc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.myrc.util.FrontConst;

/**
 * @author yuyh
 * 
 */
@Deprecated
@Controller
public class MyhelpController extends BaseController {

//	@Autowired
//	private CustomerMessageService customerMessageService;

	@RequestMapping
	public void question(Map<String, Object> out, HttpServletRequest request) {
		out.put(FrontConst.MYRC_SUBTITLE, "我的疑问反馈 ");
	}

//	@RequestMapping
//	public ModelAndView addquestion(CustomerMessageDO customerMessage) {
////		customerMessage.setIsChecked(AstConst.IS_CHECKED_FALSE);
////		customerMessage.setIsRead(AstConst.IS_SHOWED_FALSE);
////		customerMessage.setIsReply(AstConst.IS_SHOWED_FALSE);
////		Integer i = customerMessageService
////				.insertCustomerMessage(customerMessage);
////		if (i > 0) {
////			return new ModelAndView(new RedirectView("question_suc.htm"));
////		} else {
////			return null;
////		}
//		return null;
//	}

	@RequestMapping
	public void question_suc(Map<String, Object> out) {

		out.put(FrontConst.MYRC_SUBTITLE, "我的疑问反馈 ");
	}
}
