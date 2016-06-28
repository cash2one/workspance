package com.kl91.front.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.front.controller.BaseController;
import com.kl91.service.inquiry.InquiryService;

@Controller
public class ApiController extends BaseController {

	@Resource
	private InquiryService inquiryService;

	@RequestMapping
	public ModelAndView noView(HttpServletRequest request,
			Map<String, Object> out, Integer cid) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 未读询盘条数 可考虑放在缓存里
		map.put("numView", inquiryService.countNoViewedInquiry(cid));
		return printJson(map, out);
	}
}
