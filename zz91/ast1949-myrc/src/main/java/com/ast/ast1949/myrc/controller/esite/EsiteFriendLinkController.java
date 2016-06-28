/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-14
 */
package com.ast.ast1949.myrc.controller.esite;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.EsiteFriendLinkDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.EsiteFriendLinkService;
import com.zz91.util.auth.frontsso.SsoUser;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-14
 */
@Controller
public class EsiteFriendLinkController extends BaseController {

	@Autowired
	EsiteFriendLinkService esiteFriendLinkService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "友情连接");

		SsoUser sessionUser = getCachedUser(request);
		out.put("esiteFriendLinkList", esiteFriendLinkService.queryFriendLinkByCompany(sessionUser.getCompanyId(), 10));
		return null;
	}

	@RequestMapping
	public ModelAndView createFriendLink(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "添加友情链接");
		return null;
	}

	@RequestMapping
	public ModelAndView insertFriendLink(HttpServletRequest request,
			Map<String, Object> out, EsiteFriendLinkDo friendLink) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		friendLink.setAccount(sessionUser.getAccount());
		friendLink.setCompanyId(sessionUser.getCompanyId());
		
		ExtResult result = new ExtResult();
		if(esiteFriendLinkService.isFriendLinkNumOverLimit(sessionUser.getCompanyId())){
			Integer i = esiteFriendLinkService.insertFriendLink(friendLink);
			if(i!=null && i>0){
				result.setSuccess(true);
				return new ModelAndView("redirect:index.htm");
			}else{
				result.setData("failureInsert");
			}
		}else{
			result.setData("overLimit");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView initFriendLink(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put(FrontConst.MYRC_SUBTITLE, "编辑友情链接");
		
		out.put("friendLink", esiteFriendLinkService.queryOneFriendLinkById(id));
		return null;
	}

	@RequestMapping
	public ModelAndView updateFriendLink(HttpServletRequest request,
			Map<String, Object> out, EsiteFriendLinkDo friendLink) throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		friendLink.setCompanyId(sessionUser.getCompanyId());

		ExtResult result = new ExtResult();
		Integer i = esiteFriendLinkService.updateFriendLinkById(friendLink);
		if(i!=null && i>0){
			result.setSuccess(true);
			return new ModelAndView("redirect:index.htm");
		}else{
			result.setData("failureUpdate");
		}
		
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView deleteFriendLink(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		esiteFriendLinkService.deleteFriendLinkByIdAndCompany(id, getCachedUser(request).getCompanyId());
		return new ModelAndView(new RedirectView(request.getContextPath()+"/esite/esitefriendlink/index.htm"));
	}
}
