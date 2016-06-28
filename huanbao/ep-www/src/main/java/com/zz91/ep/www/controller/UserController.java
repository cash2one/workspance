/*
 * 文件名称：UserController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.www.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.ep.service.comp.CompAccountService;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：密码找回功能。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Controller
public class UserController extends BaseController {
	
	@Resource
	private CompAccountService compAccountService;
	
	/**
	 * 函数名称：find_pwf
	 * 功能描述：访问找回密码页面时。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param errormessage String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView find_pwf(HttpServletRequest request,Map<String, Object> out, String errormessage) throws UnsupportedEncodingException{
		if ("invalid".equals(errormessage)) {
			out.put("errormessage", ("重置密码请求已失效,请重新提交!"));
		} else if("noaccount".equals(errormessage)){
			out.put("errormessage", ("抱歉您输入的“用户名”或者“邮箱”不正确!"));
		}
		return null;
	}

	/**
	 * 函数名称：sendEmail
	 * 功能描述：将重设密码信息插入重设密码表,并发送邮件。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param errormessage String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView sendEmail(Map<String, Object> model, String emailOrAccount, HttpServletRequest request)
		throws IOException, NoSuchAlgorithmException{
		// 设置密码更改标记
		// 跳转到相应的页面,给出提示
		String key = compAccountService.insertResetPwdKey(emailOrAccount);
		if(key!=null){
			// 发送邮件
			String url="#";
			if(request.getServerPort()==80){
				url=request.getServerName()+request.getContextPath();
			}else{
				url=request.getServerName()+":"+request.getServerPort()+request.getContextPath();
			}
			//发送重设密码邮件
			String email = compAccountService.listResetPwdByKey(key).getEmail();
			compAccountService.sendResetPasswordMail(key,email,url);
			return new ModelAndView("redirect:/user/message_right.htm?email=" + email) ;
		}
		return new ModelAndView("redirect:/user/find_pwf.htm?errormessage=noaccount") ;
	}
	
	/**
	 * 函数名称：message_right
	 * 功能描述：申请密码设置和发送邮件成功。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param email String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView message_right(HttpServletRequest request,Map<String, Object> out, String email){
		out.put("email", email);
		return null;
	}
	
	/**
	 * 函数名称：validateKeyIsValid
	 * 功能描述：验证重置密码是否有效。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param email String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView validateKeyIsValid(Map<String, Object> model, String k, String am, HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	//验证Key是否正确并且未失效,若正确且没有失效,则跳转到密码修改页面,若失效或者不正确,则调整到错误提示页面
		if (compAccountService.validatePwdKey(am, k))
			return new ModelAndView("redirect:/user/repw.htm?k=" + k) ;
		else {
			return new ModelAndView("redirect:/user/find_pwf.htm?errormessage=invalid");
		}
	}
	
	/**
	 * 函数名称：repw
	 * 功能描述：密码重置页面。
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param k String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView repw(HttpServletRequest request,Map<String, Object> out, String k){
		out.put("k", k);
		return null;
	}

	/**
	 * 函数名称：repw
	 * 功能描述：重置密码并销毁Key
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param k String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView resetPassword(Map<String, Object> model, String k, String newPwd)
		throws IOException, NoSuchAlgorithmException {
		if(compAccountService.resetPasswordFromRetPwdKey(k, newPwd)) {
			// 日志记录
			//LogUtil.getInstance().init();
			//LogUtil.getInstance().log(operator, operation, ip, data)
			return new ModelAndView("redirect:/user/reset_success.htm");
		} else {
			model.put("k", k);
			return new ModelAndView("redirect:/user/find_pwf.htm?errormessage=invalid");
		}
	}
	
	/**
	 * 函数名称：reset_success
	 * 功能描述：重置密码成功页面
	 * 输入参数：
	 *        @param request HttpServletRequest
	 *        @param out Map
	 *        @param k String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView reset_success(HttpServletRequest request,Map<String, Object> out){
		return null;
	}

}