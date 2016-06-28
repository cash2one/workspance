package com.ast.feiliao91.trade.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrderReturnDto;
import com.ast.feiliao91.service.goods.OrderReturnService;
import com.zz91.util.seo.SeoUtil;

@Controller
public class RefundController extends BaseController {
	@Resource
	private OrderReturnService orderReturnService;

	@RequestMapping
	public ModelAndView my(PageDto<OrderReturnDto> page,
			Map<String, Object> out, HttpServletRequest request) {
		SsoUser ssoUser = getCachedUser(request);
		page.setDir("desc");
		page.setSort("gmt_created");
		page.setPageSize(15);
		page = orderReturnService.myRefund(page, ssoUser.getCompanyId());
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("refund", out);
		return null;
	}

	@RequestMapping
	public ModelAndView get(PageDto<OrderReturnDto> page,
			Map<String, Object> out, HttpServletRequest request) {
		SsoUser ssoUser = getCachedUser(request);
		page.setDir("desc");
		page.setSort("gmt_created");
		page.setPageSize(15);
		page = orderReturnService.getRefund(page, ssoUser.getCompanyId());
		out.put("page", page);
		SeoUtil.getInstance().buildSeo("refund", out);
		return null;
	}

}
