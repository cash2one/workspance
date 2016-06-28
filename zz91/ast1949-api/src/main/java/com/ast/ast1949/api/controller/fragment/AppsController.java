package com.ast.ast1949.api.controller.fragment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.service.company.InquiryService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

@Controller
public class AppsController extends BaseController {
	@Resource
	private InquiryService inquiryService;

	@RequestMapping
	public ModelAndView header(HttpServletRequest request,
			Map<String, Object> out, String ik) {
		if (StringUtils.isEmpty("ik")) {
			ik = "index";
		}
		out.put("ik", ik);
		List<InquiryDto> list = inquiryService.queryScrollInquiry();
		out.put("list", list);
		out.put("adsPhone", ParamUtils.getInstance().getValue(
				"site_info_front", "ads_phone"));
		out.put("modelMap", ParamUtils.getInstance().getValue(
				"asto1949_webapp", ik));
		return null;
	}
}
