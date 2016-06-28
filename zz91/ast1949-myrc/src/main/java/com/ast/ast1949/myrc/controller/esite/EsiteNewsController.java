/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-14
 */
package com.ast.ast1949.myrc.controller.esite;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.EsiteNewsService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-14
 */
@Controller
public class EsiteNewsController extends BaseController {

	@Autowired
	EsiteNewsService esiteNewsService;
	@Resource
	CrmCompanySvrService crmCompanySvrService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out, PageDto<EsiteNewsDo> page) {
		out.put(FrontConst.MYRC_SUBTITLE, "公司动态");

		SsoUser sessionUser = getCachedUser(request);
		page.setSort("post_time");
		page.setDir("desc");
		page.setPageSize(15);

		page = esiteNewsService.pageNewsByCompany(sessionUser.getCompanyId(),
				page);
		out.put("esiteNewsList", page.getRecords());
		out.put("page", page);
		return null;
	}

	@RequestMapping
	public ModelAndView createNews(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "添加公司动态");

		out.put("currentTime", new Date());
		return null;
	}

	@RequestMapping
	public ModelAndView insertNews(HttpServletRequest request,
			Map<String, Object> out, EsiteNewsDo news, String postTimeStr)
			throws IOException {
		ExtResult result = new ExtResult();
		do {

			SsoUser sessionUser = getCachedUser(request);
			news.setAccount(sessionUser.getAccount());
			Integer companyId = sessionUser.getCompanyId();
			news.setCompanyId(companyId);

			try {
				if (StringUtils.isNotEmpty(postTimeStr)) {
					news.setPostTime(DateUtil.getDate(postTimeStr,"yyyy-MM-dd HH:mm:ss"));
				}else{
					news.setPostTime(new Date());
				}
			} catch (ParseException e) {
				news.setPostTime(new Date());
			}

			// 是否有 门市部服务以及再生通服务
			boolean baiFlag = crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.BAIDU_CODE);
			boolean esiteFlag = crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.ESITE_CODE);
			boolean zstFlag = crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.ZST_CODE);
			boolean ldbFlag = crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.LDB_CODE);
			if(ldbFlag==false){
				ldbFlag = crmCompanySvrService.validatePeriod(companyId,CrmCompanySvrService.LDB_FIVE_CODE);
			}
			
			if(baiFlag==false&&esiteFlag==false&&zstFlag==false&&ldbFlag==false){
				break;
			}

			Integer i = esiteNewsService.insertNews(news);
			if (i != null && i > 0) {
				result.setSuccess(true);
				return new ModelAndView("redirect:index.htm");
			} else {
				result.setData("failureInsert");
			}
		} while (false);
		
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView initNews(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put(FrontConst.MYRC_SUBTITLE, "编辑公司动态");

		out.put("news", esiteNewsService.queryOneNewsById(id));
		out.put("currentTime", new Date());
		return null;
	}

	@SuppressWarnings("unused")
	@RequestMapping
	public ModelAndView updateNews(HttpServletRequest request,
			Map<String, Object> out, EsiteNewsDo news, String postTimeStr)
			throws IOException {
		SsoUser sessionUser = getCachedUser(request);
		news.setCompanyId(sessionUser.getCompanyId());

		try {
			news.setPostTime(DateUtil.getDate(postTimeStr,
					"yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ExtResult result = new ExtResult();
		Integer i = esiteNewsService.updateNewsById(news);
		if (i != null && i > 0) {
			result.setSuccess(true);
			return new ModelAndView("redirect:index.htm");
		} else {
			result.setData("failureUpdate");
		}
		//判断如果result数据是null值 返回404页面
        if(result==null){
        	return new ModelAndView("errorPage404");
        }
        else{
		return printJson(result, out);}
	}

	@RequestMapping
	public ModelAndView deleteNews(HttpServletRequest request,
			Map<String, Object> out, Integer id) throws IOException {
		esiteNewsService.deleteNewsByIdAndCompany(id, getCachedUser(request)
				.getCompanyId());
		return new ModelAndView(new RedirectView(request.getContextPath()
				+ "/esite/esitenews/index.htm"));
	}

}
