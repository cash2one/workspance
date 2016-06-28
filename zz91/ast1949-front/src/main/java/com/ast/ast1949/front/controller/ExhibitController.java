/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.front.controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.zz91.util.velocity.AddressTool;
/**
 * @author yuyonghui
 * 
 */
@Controller
public class ExhibitController extends BaseController {

	@RequestMapping
	public ModelAndView index(Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("exhibit")+"/index.htm");
//		PageHeadDTO headDTO = new PageHeadDTO();
//		headDTO.setTopNavIndex(NavConst.EXHIBIT);
//		headDTO.setPageTitle("再生展会|再生展会_${site_name}");
//		headDTO.setPageKeywords("再生展会,废料展会,废金属展会,废塑料展会");
//		headDTO
//				.setPageDescription("${site_name}的行业展会频道为您提供最新的废料会展资讯和展会预告，同时您还可以通过展会查找功能方便快捷的定位展会。");
//		setSiteInfo(headDTO, out);
//
//		// 会展资讯10371004
//		out.put("newsExhibitList", exhibitService
//				.queryExhibitByExhibitCategoryCode(AstConst.EXHIBIT_NEWS,
//						AstConst.EXHIBIT_PAGE_SIZE));
//		// 会展预告10371002
//		List<ExhibitDO> noticeExhibitList = exhibitService.queryExhibitByExhibitCategoryCode(AstConst.EXHIBIT_NOTICE, AstConst.EXHIBIT_TOP_SIZE);
//		if(noticeExhibitList.get(0)!=null){
//			String str=noticeExhibitList.get(0).getContent();
//			str =Jsoup.clean(str, Whitelist.none());
//			noticeExhibitList.get(0).setContent(str.replace("&nbsp;", "").replace(" ", ""));
//		}
//		out.put("noticeExhibitList", noticeExhibitList);
//		// 会展专题10371003
//		
//		List<ExhibitDO> topicsExhibitList = exhibitService.queryExhibitByExhibitCategoryCode(AstConst.EXHIBIT_TOPICS, AstConst.EXHIBIT_TOP_SIZE);
//		if(topicsExhibitList.get(0)!=null){
//			String str=topicsExhibitList.get(0).getContent();
//			str =Jsoup.clean(str, Whitelist.none());
//			topicsExhibitList.get(0).setContent(str.replace("&nbsp;", "").replace(" ", ""));
//		}
//		out.put("topicsExhibitList", topicsExhibitList);
//
//		// zz91合作展会10371001
//		out.put("exhibitDTOList", exhibitService.queryExhibitByPlateCategory(
//				AstConst.EXHIBIT_TEARMWORK, 11));
//		// 各行业展会10371006
//		out.put("industryExhibitList", exhibitService
//				.queryExhibitByPlateCategory(AstConst.EXHIBIT_INDUSTRY, 11));
//
//		// 首页广告
//		out.put("categoryList", categoryService
//				.queryCategoriesByPreCode(AstConst.EXHIBIT_CODE));
	}

	@RequestMapping
	public ModelAndView list(String plateCategoryCode, PageDto<ExhibitDTO> page,
			Map<String, Object> out) {
		return new ModelAndView("redirect:"+AddressTool.getAddress("exhibit")+"/list"+plateCategoryCode+".htm");
//		setSiteInfo(new PageHeadDTO(), out);
//
//		out.put("categoryName", CategoryFacade.getInstance().getValue(
//				plateCategoryCode));
//
//		ExhibitDO exhibit = new ExhibitDO();
//		exhibit.setPlateCategoryCode(plateCategoryCode);
//		page = exhibitService.pageExhibit(exhibit, page);
//		out.put("page", page);
//		
//		out.put("plateCategoryCode", plateCategoryCode);
//
//		// 右侧页面
//		right(out);
	}

	@RequestMapping
	public ModelAndView details(Integer id, Map<String, Object> out) {
		setSiteInfo(new PageHeadDTO(), out);
		return new ModelAndView("redirect:"+AddressTool.getAddress("exhibit")+"/details"+id+".htm");
//		ExhibitDO exhibitDO = exhibitService.selectExhibitById(id);
//		Document doc=Jsoup.parse(exhibitDO.getContent());
//		exhibitDO.setContent(doc.select("body").html());
//		out.put("exhibitDO", exhibitDO);
//
//		out.put("categoryName", CategoryFacade.getInstance().getValue(
//				exhibitDO.getPlateCategoryCode()));
//
//		// 右侧页面
//		right(out);
	}

	@RequestMapping
	public ModelAndView searchExhibit(ExhibitDO exhibit, PageDto<ExhibitDTO> page,
			String mystartTime, String myendTime, Map<String, Object> out)
			throws ParseException {
		return new ModelAndView("redirect:"+AddressTool.getAddress("exhibit")+"/index.htm");
//		setSiteInfo(new PageHeadDTO(), out);
//		
//		if(page.getPageSize()==null || page.getPageSize()<=0){
//			page.setPageSize(20);
//		}
//		
//		if (mystartTime != null && mystartTime.length() > 0) {
//			exhibit.setStartTime(DateUtil.getDate(mystartTime, "yyyy-MM-dd"));
//		}
//		if (myendTime != null && myendTime.length() > 0) {
//			exhibit.setEndTime(DateUtil.getDate(myendTime, "yyyy-MM-dd"));
//		}
//		
//		page = exhibitService.pageExhibit(exhibit, page);
//		
//		out.put("page", page);
//		
//		out.put("exhibit", exhibit);
//		out.put("mystartTime", mystartTime);
//		out.put("myendTime", myendTime);
//		try {
//			if(exhibit.getName()!=null){
//				out.put("k", URLEncoder.encode(exhibit.getName(),"utf-8"));
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		// 右侧页面
//		right(out);
	}

//	/**
//	 * 前台右侧部分
//	 * 
//	 * @param out
//	 */
//	private void right(Map<String, Object> out) {
////		out.put("stringUtils", new StringUtils());
//		// 会展专题排行
//		List<ExhibitDTO> exhibitList;
//		// 会展资讯10371004
//		exhibitList = exhibitService.queryExhibit(
//				AstConst.EXHIBIT_NEWS,null, AstConst.EXHIBIT_PAGE_SIZE);
//		out.put("newsExhibitList", exhibitList);
//		// 会展预告10371002
//		exhibitList = exhibitService.queryExhibit(
//				AstConst.EXHIBIT_NOTICE,null, AstConst.EXHIBIT_PAGE_SIZE);
//		String str=exhibitList.get(0).getExhibitDO().getContent();
//		str =Jsoup.clean(str, Whitelist.none());
//		exhibitList.get(0).getExhibitDO().setContent(str.replace("&nbsp;", "").replace(" ", ""));
//		out.put("noticeExhibitList", exhibitList);
//		// 会展专题10371003
//		exhibitList = exhibitService.queryExhibit(
//				AstConst.EXHIBIT_TOPICS,null, AstConst.EXHIBIT_PAGE_SIZE);
//		out.put("topicsExhibitList", exhibitList);
//		// 合作展会10371001
//		exhibitList = exhibitService.queryExhibit(
//				AstConst.EXHIBIT_TEARMWORK,null, AstConst.EXHIBIT_PAGE_SIZE);
//		out.put("tearmExhibitList", exhibitList);
//	}
	
}
