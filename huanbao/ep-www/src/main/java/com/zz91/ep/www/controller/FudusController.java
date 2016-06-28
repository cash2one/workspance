package com.zz91.ep.www.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.ep.service.common.ParamService;
import com.zz91.util.domain.Param;
import com.zz91.util.seo.SeoUtil;

@Controller
public class FudusController extends BaseController {
	
	@Resource
	private ParamService paramService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView absindex(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("absindex", null, null, null, out);
		return null;
	}
	
	@RequestMapping
	public ModelAndView friendlinks(HttpServletRequest request,Map<String, Object> out){
		//友情链接
		List<Param> friendLinkHangye = paramService.queryParamsByType("friend_link_hangye");
        out.put("friendLinkHangye", friendLinkHangye);
		List<Param> friendLinkQiye = paramService.queryParamsByType("friend_link_qiye");
        out.put("friendLinkQiye", friendLinkQiye);
		List<Param> friendLinkZhengfu = paramService.queryParamsByType("friend_link_zhengfu");
        out.put("friendLinkZhengfu", friendLinkZhengfu);
        SeoUtil.getInstance().buildSeo("friendlinks", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView absstate(HttpServletRequest request,Map<String, Object> out){
		return null;
	}
	@RequestMapping
	public ModelAndView absjoinus(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("absjoinus", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView absdevp(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("absdevp", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView absculture(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("absculture", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView abscpyinfo(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("abscpyinfo", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView absclause(HttpServletRequest request,Map<String, Object> out){
		return null;
	}
	@RequestMapping
	public ModelAndView epmap(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("epmap", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView leaveMsg(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("leaveMsg", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView putArticle(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("putArticle", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vpindex(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vpindex", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vpbranding(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vpbranding", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vpmarketing(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vpmarketing", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vpzhitongche(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vpzhitongche", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vpsirenzhuli(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vpsirenzhuli", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vphuanbaoshangpu(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vphuanbaoshangpu", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vpapply(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vpapply", null, null, null, out);
		return null;
	}
	@RequestMapping
	public ModelAndView vppay(HttpServletRequest request,Map<String, Object> out){
		SeoUtil.getInstance().buildSeo("vppay", null, null, null, out);
		return null;
	}
}
