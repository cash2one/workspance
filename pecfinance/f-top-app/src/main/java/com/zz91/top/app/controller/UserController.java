/**
 * 
 */
package com.zz91.top.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.internal.util.WebUtils;
import com.zz91.top.app.config.TopConfig;
import com.zz91.top.app.domain.AdminUser;
import com.zz91.top.app.domain.SessionUser;
import com.zz91.top.app.dto.TbShopAccessDto;
import com.zz91.top.app.service.AdminUserService;
import com.zz91.top.app.service.TbShopAccessService;
import com.zz91.top.app.utils.AppConst;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays
 *
 */
@Controller
public class UserController extends BaseController{

	@Resource
	private TopConfig topConfig;
	@Resource
	private TbShopAccessService shopService;
	@Resource
	private AdminUserService adminUserService;
	
	@RequestMapping
	public ModelAndView login(HttpServletRequest request, Map<String, Object> out){
		//if unlogin -> top authorize
		//else -> index
		StringBuffer sb=new StringBuffer();
		sb.append(topConfig.getAuthUrl())
			.append("?client_id=").append(topConfig.getAppKey())
			.append("&response_type=code&redirect_uri=").append(topConfig.getCallback());
		return new ModelAndView("redirect:"+sb.toString());
	}
	
	@RequestMapping
	public ModelAndView logout(HttpServletRequest request, Map<String, Object> out){
		request.getSession().removeAttribute(AppConst.SESSION_KEY);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping
	public ModelAndView tbCallback(HttpServletRequest request, Map<String, Object> out, 
			HttpServletResponse response,
			String code, String error, String error_description){
		
		if(StringUtils.isNotEmpty(error)){
			out.put("error", error);
			out.put("error_description", error_description);
			return new ModelAndView("redirect:/error.htm");
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("client_id", topConfig.getAppKey());
		params.put("client_secret", topConfig.getAppSecret());
		params.put("grant_type", "authorization_code");
		params.put("code", code);
		params.put("redirect_uri", "http://test.zz91.com:8080/top/user/tokenCallback.htm");
		params.put("view", "web");
		
		try {
			String responseJson =  WebUtils.doPost(topConfig.getTokenUrl(), params, 3000, 3000);
			
			JSONObject jobj = JSONObject.fromObject(responseJson);
			TbShopAccessDto shopDto = new TbShopAccessDto();
			shopDto.setTbResponse(jobj);
			Integer i = shopService.createOrUpdateShopToken(shopDto);
			
			if(i!=null && i.intValue()>0){
				SessionUser sessionUser = new SessionUser();
				sessionUser.setShopId(shopDto.getShopAccess().getId());
				sessionUser.setGmtLastLogin(shopDto.getShopAccess().getGmtLastLogin());
				sessionUser.setTaobaoUserId(shopDto.getShopAccess().getTaobaoUserId());
				sessionUser.setTaobaoUserNick(shopDto.getShopAccess().getTaobaoUserNick());
				sessionUser.setAccessToken(shopDto.getShopAccess().getAccessToken());
				
				request.getSession().setAttribute(AppConst.SESSION_KEY, sessionUser);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/index.do");
	}
	
	@RequestMapping
	public ModelAndView tokenCallback(HttpServletRequest request, Map<String, Object> out){
		
		return printJson(new HashMap<String, String>(), out);
	}
	
	@RequestMapping
	public ModelAndView adminLogin(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView doAdminLogin(HttpServletRequest request, Map<String, Object> out,
			String username, String password){
		
		AdminUser adminUser = adminUserService.loginCheck(username, password);
		
		if(adminUser!=null){
			SessionUser sessionUser = adminUserService.buildSessionUser(adminUser);
			request.getSession().setAttribute(AppConst.SESSION_KEY, sessionUser);
		}else{
			out.put("error", "");
		}
		
		return new ModelAndView("redirect:/");
	}
}
