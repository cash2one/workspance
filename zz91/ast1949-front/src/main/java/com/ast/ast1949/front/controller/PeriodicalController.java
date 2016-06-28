/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-31
 */
package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.information.PeriodicalDetails;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.PageHeadDTO;
import com.ast.ast1949.dto.information.PeriodicalDTO;
import com.ast.ast1949.service.information.PeriodicalDetailsService;
import com.ast.ast1949.service.information.PeriodicalService;
import com.ast.ast1949.util.NavConst;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Controller
public class PeriodicalController extends BaseController {

	@Autowired
	private PeriodicalService periodicalService;
	@Autowired
	private PeriodicalDetailsService periodicalDetailsService;

	private final static int PERIODICAL_PAGE_SIZE = 9;
//	private final static int DETAIlS_PAGE_SIZE = 35;

	@RequestMapping
	public void index(Map<String, Object> out) {
		// 设置页面头部信息
		PageHeadDTO headDTO = new PageHeadDTO();
		headDTO.setTopNavIndex(NavConst.MAGAZINE);
		headDTO.setPageTitle("废料商情|废料商情_${site_name}");
		headDTO.setPageKeywords("废料商情,废料杂志,zz91废料商情,再生手册,展会资讯,废料综合");
		headDTO
				.setPageDescription("zz91废料商情,国内再生行业唯一一本专业性、综合性商情营销杂志,集产品展示、行业资讯、展会信息、市场贸易等于一体，全方位报道国内外再生资源行业市场行情及商展信息推介");
		setSiteInfo(headDTO, out);

		out.put("resourceUrl", MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		List<PeriodicalDTO> periodicalList = periodicalService
				.listFrontCoverPeriodicalBySize(PERIODICAL_PAGE_SIZE);
		out.put("periodicalList", periodicalList);
	}

	@RequestMapping
	public void list(Integer periodicalId, String p, Map<String, Object> out) {
		setSiteInfo(new PageHeadDTO(), out);
		// 子页详细信息列表页面
		p = StringUtils.getPageIndex(p);
		PageDto pageDTO = new PageDto();
		pageDTO.setTotalRecords(periodicalDetailsService
				.countPageDetailsByPeriodicalId(periodicalId));
		//根据总记录数计算总页数
		List<PeriodicalDetails> periodicalList = periodicalDetailsService
				.pageDetailsByPeriodicalId(periodicalId, pageDTO);
		out.put("periodicalList", periodicalList);
		out.put("pageDTO", pageDTO);
		out.put("totalRecords", pageDTO.getTotalRecords());
		out.put("currentPage", Integer.valueOf(p));
		out.put("periodicalId", periodicalId);
		out.put("resourceUrl", MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		out.put("model", "periodical");
		cover(periodicalId, out);
	}

	@RequestMapping
	public void details(Integer id, Integer type, Map<String, Object> out) {
		setSiteInfo(new PageHeadDTO(), out);
		out.put("resourceUrl", MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		out.put("model", "periodical");
		if (id != null) {
			PeriodicalDetails periodicalDetails = periodicalDetailsService.selectDetailsById(id);
			out.put("periodicalDetails", periodicalDetails);
			cover(periodicalDetails.getPeriodicalId(), out);
		}
	}

	@RequestMapping
	public ModelAndView updateview(Map<String, Object> model, Integer id) throws IOException {
		ExtResult result = new ExtResult();
		result.setData(periodicalService.updateNumViewById(id));
		result.setSuccess(true);
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView updateup(Map<String, Object> model, Integer id) throws IOException {
		ExtResult result = new ExtResult();
		result.setData(periodicalService.updateNumUpById(id));
		result.setSuccess(true);
		return printJson(result, model);
	}

	private void cover(Integer periodicalId, Map<String, Object> out) {
		// 封面，封二,封三,
		List<PeriodicalDetails> list = periodicalDetailsService.pageDetailsByPeriodicalIdAndType(
				periodicalId, 0);
		if (list.size() >= 1) {
			out.put("oneCover", list.get(0));
		}
		if (list.size() >= 2) {
			out.put("twoCover", list.get(1));
		}
		if (list.size() >= 3) {
			out.put("threeCover", list.get(2));
		}
		// 封底
		List<PeriodicalDetails> list1 = periodicalDetailsService.pageDetailsByPeriodicalIdAndType(
				periodicalId, 1);
		out.put("backCover", list1);
		// 目录
		List<PeriodicalDetails> list2 = periodicalDetailsService.pageDetailsByPeriodicalIdAndType(
				periodicalId, 3);
		out.put("indexList", list2);
	}

}
