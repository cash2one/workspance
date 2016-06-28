package com.ast.ast1949.web.controller.zz91.analysis;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.analysis.AnalysisOperate;
import com.ast.ast1949.domain.analysis.AnalysisProductTypeCode;
import com.ast.ast1949.dto.analysis.AnalysisProductTypeCodeDto;
import com.ast.ast1949.service.analysis.AnalysisOperateService;
import com.ast.ast1949.service.analysis.AnalysisProductTypeCodeService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author kongsj
 * @date 2012-9-4
 */
@Controller
public class AnalysisOperateController extends BaseController {

	final static String TARGET_DATE_FORMAT = "yyyy-MM-dd";
	@Resource
	private AnalysisOperateService analysisOperateService;
	@Resource
	private AnalysisProductTypeCodeService analysisProductTypeCodeService; 

	@RequestMapping
	public ModelAndView index(Map<String, Object> out) {
		return null;
	}

	@RequestMapping
	public ModelAndView queryAnalysisData(HttpServletRequest request,
			Map<String, Object> out, String from, String to)
			throws IOException, ParseException {
		String csAccount = null;
		// 判断是否有权限察看 日志
		// if(!AuthUtils.getInstance().authorizeRight("view_analysis_log",
		// request, null)){
		// csAccount=getCachedUser(request).getAccount();
		// }
		String targetFrom,targetTo = "";
		if (StringUtils.isNotEmpty(from)) {
			targetFrom = DateUtil.toString(DateUtil.getDate(from,TARGET_DATE_FORMAT), TARGET_DATE_FORMAT);
		} else {
			targetFrom = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT);
		}
		if (StringUtils.isNotEmpty(to)) {
			try {
				targetTo = DateUtil.toString(DateUtil.getDate(to,TARGET_DATE_FORMAT), TARGET_DATE_FORMAT);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			targetTo = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT);
		}

		List<AnalysisOperate> list = analysisOperateService.queryAnalysisOperate(csAccount,targetFrom, targetTo);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryAnalysisProductData(HttpServletRequest request,
			Map<String, Object> out, String from, String to,String account)
			throws IOException, ParseException {
		// 判断是否有权限察看 日志
		// if(!AuthUtils.getInstance().authorizeRight("view_analysis_log",
		// request, null)){
		// csAccount=getCachedUser(request).getAccount();
		// }
		String targetFrom,targetTo = "";
		if (StringUtils.isNotEmpty(from)) {
			targetFrom = DateUtil.toString(DateUtil.getDate(from,TARGET_DATE_FORMAT), TARGET_DATE_FORMAT);
		} else {
			targetFrom = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT);
		}
		if (StringUtils.isNotEmpty(to)) {
			try {
				targetTo = DateUtil.toString(DateUtil.getDate(to,TARGET_DATE_FORMAT), TARGET_DATE_FORMAT);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			targetTo = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -1), TARGET_DATE_FORMAT);
		}

		List<AnalysisProductTypeCode> prelist = analysisProductTypeCodeService.queryAnalysisProductTypeCode(account,targetFrom, targetTo);
		List<AnalysisProductTypeCodeDto> list = new ArrayList<AnalysisProductTypeCodeDto>();
		if(prelist.size()>0){
			list = analysisProductTypeCodeService.domainToDto(prelist.get(0));
		}
		return printJson(list, out);
	}
}