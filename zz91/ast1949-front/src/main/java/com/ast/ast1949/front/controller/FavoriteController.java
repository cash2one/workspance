package com.ast.ast1949.front.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;

@Controller
public class FavoriteController extends BaseController {

	@Resource
	private MyfavoriteService myfavoriteService;

	@RequestMapping
	public ModelAndView myFavorite(HttpServletRequest request, Map<String, Object> out,
			String destUrl, Integer contentId, String favoriteTypeCode,
			String title) throws UnsupportedEncodingException {
		SsoUser ssoUser = getCachedUser(request);
		title = StringUtils.decryptUrlParameter(title);
		out.put("destUrl", destUrl);
		out.put("title", title);
		do{
			if(ssoUser == null){
				break;
			}
			Boolean isExist = myfavoriteService.isExist(ssoUser.getCompanyId(),contentId, favoriteTypeCode);
			if (isExist != true) {
				out.put("isExist", isExist);
				out.put("contentId", contentId);
				out.put("favoriteTypeCode", favoriteTypeCode);
			}
		}while(false);
		return null;
	}

	@RequestMapping
	public ModelAndView doMyFavorite(HttpServletRequest request,MyfavoriteDO myfavoriteDO, String destUrl,
			Map<String, Object> out) {
		SsoUser ssoUser = getCachedUser(request);
		myfavoriteDO.setCompanyId(ssoUser.getCompanyId());
		int i = myfavoriteService.insertMyCollect(myfavoriteDO);
		if (i > 0) {
			out.put("result", i);
		}
		out.put("title", myfavoriteDO.getContentTitle());
		out.put("destUrl", destUrl);
		return null;
	}
}