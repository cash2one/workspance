/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.exhibit.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.information.ExhibitService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.NavConst;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.seo.SeoUtil;
/**
 * @author yuyonghui
 * 
 */
@Controller
public class ExhibitController extends BaseController {

	@Autowired
	private ExhibitService exhibitService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping
	public void oldIndex(Map<String, Object> out) {
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.EXHIBIT);
		headDTO.setPageTitle("再生展会|再生展会_${site_name}");
		headDTO.setPageKeywords("再生展会,废料展会,废金属展会,废塑料展会");
		headDTO.setPageDescription("${site_name}的行业展会频道为您提供最新的废料会展资讯和展会预告，同时您还可以通过展会查找功能方便快捷的定位展会。");
		setSiteInfo(headDTO, out);

		// 会展资讯10371004
		out.put("newsExhibitList", exhibitService.queryExhibit(AstConst.EXHIBIT_NEWS,null, 8));
		// 会展预告10371002
		List<ExhibitDTO> noticeExhibitList = exhibitService.queryExhibit(AstConst.EXHIBIT_NOTICE,null, 1);
		if(noticeExhibitList.get(0)!=null){
			String str=noticeExhibitList.get(0).getExhibitDO().getContent();
			str =Jsoup.clean(str, Whitelist.none());
			noticeExhibitList.get(0).getExhibitDO().setContent(str.replace("&nbsp;", "").replace(" ", ""));
		}
		if(noticeExhibitList.size()>0){
			out.put("noticeExhibitList", noticeExhibitList.get(0));
		}
		
		// 会展专题10371003
		List<ExhibitDTO> topicsExhibitList = exhibitService.queryExhibit(AstConst.EXHIBIT_TOPICS,null, 1);
		if(topicsExhibitList.get(0)!=null){
			String str=topicsExhibitList.get(0).getExhibitDO().getContent();
			str =Jsoup.clean(str, Whitelist.none());
			topicsExhibitList.get(0).getExhibitDO().setContent(str.replace("&nbsp;", "").replace(" ", ""));
		}
		out.put("topicsExhibitList", topicsExhibitList);

		// zz91合作展会10371001
		out.put("exhibitDTOList", exhibitService.queryExhibitByPlateCategory(
				AstConst.EXHIBIT_TEARMWORK, 11));
		// 各行业展会10371006
		out.put("industryExhibitList", exhibitService
				.queryExhibitByPlateCategory(AstConst.EXHIBIT_INDUSTRY, 11));

		// 首页广告
		out.put("categoryList", categoryService
				.queryCategoriesByPreCode(AstConst.EXHIBIT_CODE));
	}

	@RequestMapping
	public void list(Map<String, Object> out,HttpServletRequest request) {
		String category=request.getParameter("category");
		String plateCategoryCode=request.getParameter("plateCategoryCode");
		PageDto<ExhibitDTO> page=new PageDto<ExhibitDTO>();
		
		String startIndexString=request.getParameter("startIndex");
		if(StringUtils.isNotEmpty(startIndexString)){
			Integer startIndex=Integer.valueOf(startIndexString);
			page.setStartIndex(startIndex);
		}
		String pageSizeString=request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSizeString)){
			Integer pageSize=Integer.valueOf(pageSizeString);
			page.setPageSize(pageSize);
		}
		setSiteInfo(new PageHeadDTO(), out);

		out.put("categoryName", CategoryFacade.getInstance().getValue(
				plateCategoryCode));

		ExhibitDO exhibit = new ExhibitDO();
		exhibit.setPlateCategoryCode(plateCategoryCode);
		exhibit.setExhibitCategoryCode(category);
		page = exhibitService.pageExhibit(exhibit, page);
		out.put("page", page);
		
		out.put("plateCategoryCode", plateCategoryCode);
		out.put("category", category);

		// 右侧页面
		right(out);
		// 页面seo
		SeoUtil.getInstance().buildSeo("list", out);
	}

	@RequestMapping
	public void details(Integer id, Map<String, Object> out) {
		
		ExhibitDO exhibitDO = exhibitService.selectExhibitById(id);
//		Document doc=Jsoup.parse(exhibitDO.getContent());
//		exhibitDO.setContent(doc.select("body").html());
		out.put("exhibitDO", exhibitDO);

		out.put("categoryName", CategoryFacade.getInstance().getValue(
				exhibitDO.getPlateCategoryCode()));

		// 右侧页面
		right(out);
		
		PageHeadDTO head=new PageHeadDTO();
		head.setPageTitle(exhibitDO.getName()+"_${site_name}");
		setSiteInfo(head, out);
		// 页面Seo
		SeoUtil.getInstance().buildSeo("details", new String[]{exhibitDO.getName()}, null, null, out);
	}

	@RequestMapping
	public void searchExhibit(ExhibitDO exhibit, PageDto<ExhibitDTO> page,
			String mystartTime, String myendTime, Map<String, Object> out)
			throws ParseException {
		
		setSiteInfo(new PageHeadDTO(), out);
		
		if(page.getPageSize()==null || page.getPageSize()<=0){
			page.setPageSize(20);
		}
		
		if (mystartTime != null && mystartTime.length() > 0) {
			exhibit.setStartTime(DateUtil.getDate(mystartTime, "yyyy-MM-dd"));
		}
		if (myendTime != null && myendTime.length() > 0) {
			exhibit.setEndTime(DateUtil.getDate(myendTime, "yyyy-MM-dd"));
		}
		
		page = exhibitService.pageExhibit(exhibit, page);
		
		out.put("page", page);
		
		out.put("exhibit", exhibit);
		out.put("mystartTime", mystartTime);
		out.put("myendTime", myendTime);
		try {
			if(exhibit.getName()!=null){
				out.put("k", URLEncoder.encode(exhibit.getName(),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 右侧页面
		right(out);
	}

	/**
	 * 前台右侧部分
	 * 
	 * @param out
	 */
	private void right(Map<String, Object> out) {
//		out.put("stringUtils", new StringUtils());
		// 会展专题排行
		List<ExhibitDTO> exhibitList;
		// 会展资讯10371004
		exhibitList = exhibitService.queryExhibit(
				AstConst.EXHIBIT_NEWS,null, AstConst.EXHIBIT_PAGE_SIZE);
		out.put("newsExhibitList", exhibitList);
		// 会展预告10371002
		exhibitList = exhibitService.queryExhibit(
				AstConst.EXHIBIT_NOTICE,null, AstConst.EXHIBIT_PAGE_SIZE);
		String str=exhibitList.get(0).getExhibitDO().getContent();
		str =Jsoup.clean(str, Whitelist.none());
		exhibitList.get(0).getExhibitDO().setContent(str.replace("&nbsp;", "").replace(" ", ""));
		out.put("noticeExhibitList", exhibitList);
		// 会展专题10371003
		exhibitList = exhibitService.queryExhibit(
				AstConst.EXHIBIT_TOPICS,null, AstConst.EXHIBIT_PAGE_SIZE);
		out.put("topicsExhibitList", exhibitList);
		// 合作展会10371001
		exhibitList = exhibitService.queryExhibit(
				AstConst.EXHIBIT_TEARMWORK,null, AstConst.EXHIBIT_PAGE_SIZE);
		out.put("tearmExhibitList", exhibitList);
	}
	
	//首页
	@RequestMapping
	public void index(Map<String,Object>out){
		// 展会预告 模块 code：10371002
		List<ExhibitDTO> noticeExhibitList = exhibitService.queryExhibit(AstConst.EXHIBIT_NOTICE,null, 1);
		if(noticeExhibitList.get(0)!=null){
			String str=noticeExhibitList.get(0).getExhibitDO().getContent();
			str =Jsoup.clean(str, Whitelist.none());
			noticeExhibitList.get(0).getExhibitDO().setContent(str.replace("&nbsp;", "").replace(" ", ""));
		}
		out.put("noticeExhibitList", noticeExhibitList);

		// 模块 List

		
		// 展会推荐 模块 code：10371008
		out.put("recommendExhibitList", exhibitService.queryExhibit("10371008",null, 6));
		
		// 热门资讯 模块 code：10371009
		out.put("topExhibitList", exhibitService.queryExhibit("10371009",null, 6));

		// 展会回顾 展会频道类别 code：10021001
		// 园区推荐 展会频道类别 code：10021000
		// 企业推荐 展会频道类别 code：10021002
		// 产品和服务 展会频道类别 code：10021003
		// ZZ91足迹 展会频道类别 code：10021004

		// 产业资讯 互助模块 code：********
		// 企业访谈 互助模块 code：102610001008
		
		// 页面Seo
		SeoUtil.getInstance().buildSeo(out);
	}
	
	//再生展区
	@RequestMapping
	public void zaisheng(Map<String,Object>out){
		// 模块 List
		List<ExhibitDTO> exhibitList;
		
		// 展会预告 模块 code：10371002
		List<ExhibitDTO> noticeExhibitList = exhibitService.queryExhibit(AstConst.EXHIBIT_NOTICE,null, 1);
		if(noticeExhibitList.get(0)!=null){
			String str=noticeExhibitList.get(0).getExhibitDO().getContent();
			str =Jsoup.clean(str, Whitelist.none());
			noticeExhibitList.get(0).getExhibitDO().setContent(str.replace("&nbsp;", "").replace(" ", ""));
		}
		out.put("noticeExhibitList", noticeExhibitList);

		// 展会资讯10371004
		exhibitList = exhibitService.queryExhibit(AstConst.EXHIBIT_NEWS,null, 6);
		out.put("newsExhibitList", exhibitList);

		// 参展企业 展会频道类别 code：10021006

		// 展会供求 模块 code：10371010
		exhibitList = exhibitService.queryExhibit("10371010",null, 6);
		out.put("tradeExhibitList", exhibitList);

		// 展会回顾 展会频道类别 code：10021001

		// 展会推荐 模块 code：10371008
		exhibitList = exhibitService.queryExhibit("10371008",null, 5);
		out.put("recommendExhibitList", exhibitList);

		// 企业访谈 互助模块 code：********

		// 参展指南 模块 code：10371011
		exhibitList = exhibitService.queryExhibit("10371011",null, 5);
		out.put("guideExhibitList", exhibitList);

		// 废塑料展会 模块 code：10381002
		exhibitList = exhibitService.queryExhibit(null,"10381002", 8);
		out.put("plasticExhibitList", exhibitList);

		// 废金属展会 模块 code：103810018
		exhibitList = exhibitService.queryExhibit(null,"10381001", 8);
		out.put("metalExhibitList", exhibitList);

		// 综合废料展会 模块 code：10381000
		exhibitList = exhibitService.queryExhibit(null,"10381000", 8);
		out.put("complexExhibitList", exhibitList);

		// ZZ91所有展会集锦 展会频道类别 code：10021005

		// 页面Seo
		SeoUtil.getInstance().buildSeo("index2", out);
	}
	// 战略合作
	@RequestMapping
	public void zhanlue(Map<String,Object>out){
		// 模块 List
		List<ExhibitDTO> exhibitList;

		// 产业动态 模块 code：10371012
		exhibitList = exhibitService.queryExhibit("10371012",null, 8);
		out.put("cydtExhibitList", exhibitList);
		// 展会推荐 模块 code：10371008
		out.put("recommendExhibitList", exhibitService.queryExhibit("10371008",null, 9));

		// 合作园区-园区推荐 展会频道类别 code：10021000

		// 企业访谈 互助模块 code：********

		// 产品和服务 展会频道类别 code：10021003

		// 合作企业-企业推荐 展会频道类别 code：10021002

		// 页面Seo
		SeoUtil.getInstance().buildSeo("index3",out);
	}
	// 线下产品与服务
	@RequestMapping
	public void chanpinfuwu(Map<String,Object>out){
		// 历届展会 展会频道类别 code：10021007
		
		// 页面Seo
		SeoUtil.getInstance().buildSeo("index4",out);
	}
	
	@RequestMapping
	public ModelAndView submitCallback(HttpServletRequest request,
			Map<String, Object> out, String success, String data) {
		if (StringUtils.isEmpty(data)) {
			data = "{}";
		}
		try {
			data = StringUtils.decryptUrlParameter(data);
		} catch (UnsupportedEncodingException e) {
		}
		out.put("success", success);
		out.put("data", data);
		return null;
	}
}
