package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;

@Controller
public class BindController extends BaseController{
	
	@Resource
	private OauthAccessService oauthAccessService;
	@Resource
	private AuthService authService;
	
	@RequestMapping
	public ModelAndView mobile(Map<String, Object> out,String destUrl){
		out.put("destUrl", destUrl);
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView unMobile(Map<String, Object> out,String destUrl){
		out.put("destUrl", destUrl);
		out.put("unbind", 1);
		return new ModelAndView("/bind/mobile");
	}

	/**
	 * 获取验证码
	 * @param out
	 * @param request
	 * @param mobile
	 * @param type type为空，表示绑定逻辑，type不为空，表示解绑逻辑
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getMobileCode(Map<String, Object> out,HttpServletRequest request,String mobile) throws IOException{
		ExtResult result = new ExtResult();
		do {
			// 未登陆
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			// 手机号为空
			if (StringUtils.isEmpty(mobile)) {
				break;
			}
			// 手机号是否已经存在于其他账户
			Integer i = 0;
			if (authService.validateAuthUserByMobile(ssoUser.getAccount(), mobile)) {
				i = oauthAccessService.addOneMobileAccess(mobile, OauthAccessService.OPEN_TYPE_MOBILE, ssoUser.getAccount());
			}
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView doMobile(Map<String, Object>out,HttpServletRequest request,String mobile,String code,String destUrl){
		out.put("destUrl",destUrl);
		do {
			// 获取登陆信息
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			
			if(StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)){
				break;
			}
			// 获取验证信息
			OauthAccess oa = oauthAccessService.queryAccessByOpenIdAndType(mobile, OauthAccessService.OPEN_TYPE_MOBILE);
			//验证信息是否存在
			if(oa==null){
				break;
			}
			// 编码是否一致
			if(!code.equals(oa.getCode())){
				break;
			}
			// 编码是否过期 30分钟
			Date date = new Date();
			if(date.getTime()-oa.getGmtCreated().getTime()>30*60*1000){
				break;
			}
			
			// 更新手机帐号登陆
			authService.updateMobile(ssoUser.getAccount(), mobile);
			return new ModelAndView();
		} while (false);
		out.put("result", 1);
		return new ModelAndView("/bind/mobile");
	}
	
	@RequestMapping
	public ModelAndView doUnMobile(Map<String, Object>out,HttpServletRequest request,String mobile,String code,String destUrl){
		out.put("destUrl",destUrl);
		do {
			// 获取登陆信息
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				break;
			}
			
			if(StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)){
				break;
			}
			// 获取验证信息
			OauthAccess oa = oauthAccessService.queryAccessByOpenIdAndType(mobile, OauthAccessService.OPEN_TYPE_MOBILE);
			//验证信息是否存在
			if(oa==null){
				break;
			}
			// 编码是否一致
			if(!code.equals(oa.getCode())){
				break;
			}
			// 编码是否过期 30分钟
			Date date = new Date();
			if(date.getTime()-oa.getGmtCreated().getTime()>30*60*1000){
				break;
			}
			
			// 更新手机帐号登陆
			authService.updateMobile(ssoUser.getAccount(), "");
			// 记录手机解除绑定的日志
			authService.addUnBindMobileLog(ssoUser.getCompanyId(), mobile);
			return new ModelAndView();
		} while (false);
		out.put("result", 1);
		out.put("unbind", 1);
		return new ModelAndView("/bind/mobile");
	}

}
