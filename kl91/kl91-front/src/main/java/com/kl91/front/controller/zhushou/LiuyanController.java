package com.kl91.front.controller.zhushou;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.company.Company;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.inquiry.Inquiry;
import com.kl91.domain.inquiry.InquiryDto;
import com.kl91.front.controller.BaseController;
import com.kl91.service.company.CompanyService;
import com.kl91.service.inquiry.InquiryService;

@Controller
public class LiuyanController extends BaseController {

	@Resource
	private InquiryService inquiryService;
	@Resource
	private CompanyService companyService;

	@RequestMapping
	public void shoujian(HttpServletRequest request, Map<String, Object> out,
			PageDto<InquiryDto> page) {
		page.setPageSize(7);
		inquiryService.queryToByCompanyId(
				getCachedUser(request).getCompanyId(), 
				page);
		out.put("page", page);
	}

	@RequestMapping
	public void fajian(HttpServletRequest request, Map<String, Object> out,
			PageDto<InquiryDto> page) {
		page.setPageSize(7);
		inquiryService.queryFromByCompanyId(getCachedUser(request)
				.getCompanyId(), page);
		out.put("page", page);
	}

	@RequestMapping
	public void huishou(HttpServletRequest request, Map<String, Object> out,
			PageDto<InquiryDto> page) {
		page.setPageSize(7);
		inquiryService.queryTrashByCompanyId(getCachedUser(request)
				.getCompanyId(), page);
		out.put("page", page);
	}

	@RequestMapping
	public ModelAndView edit(Map<String, Object> out, Integer id) {
		do {
			if (id == null) {
				break;
			}
			Inquiry inquiry = inquiryService.queryById(id);
			inquiryService.updateViewed(id);
			Company company = companyService.queryById(inquiry.getFromCid());
			out.put("inquiry", inquiry);
			out.put("company", company);
			return null;
		} while (false);
		return new ModelAndView("redirect:shoujian.htm");
	}

	@RequestMapping
	public ModelAndView doEdit(Map<String, Object> out, Integer id,
			String content) {
		do {
			if (id == null) {
				break;
			}
			int i = inquiryService.replyInquiry(id, content);
			if (i < 0) {
				break;
			}
			return new ModelAndView("redirect:shoujian.htm");
		} while (false);
		return null;
	}

	@RequestMapping
	public ModelAndView deleteToById(HttpServletRequest request,
			Map<String, Object> out, String id) {
		String url = request.getHeader("referer");
		do {
			if (id == null) {
				break;
			}
			inquiryService.deleteByTo(id, getCachedUser(request)
					.getCompanyId());
		} while (false);
		return new ModelAndView("redirect:" + url);
	}

	@RequestMapping
	public ModelAndView deleteFromById(HttpServletRequest request,
			Map<String, Object> out, String id) {
		String url = request.getHeader("referer");
		do {
			if (id == null) {
				break;
			}
			inquiryService.deleteByFrom(id, getCachedUser(request)
					.getCompanyId());
		} while (false);
		return new ModelAndView("redirect:" + url);
	}

	@RequestMapping
	public ModelAndView cpdeleteById(HttpServletRequest request,
			Map<String, Object> out, String toId, String fromId) {
		String url = request.getHeader("referer");
		do {
			if (toId != null) {
				inquiryService.cpdeleteByTo(toId, getCachedUser(request)
						.getCompanyId());
			}
			if (fromId != null) {
				inquiryService.cpdeleteByFrom(fromId, getCachedUser(request)
						.getCompanyId());
			}
		} while (false);
		return new ModelAndView("redirect:" + url);
	}

}
