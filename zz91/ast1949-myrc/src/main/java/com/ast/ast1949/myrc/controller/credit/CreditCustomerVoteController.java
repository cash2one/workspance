/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-23
 */
package com.ast.ast1949.myrc.controller.credit;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditCustomerVoteDto;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.service.credit.CreditCustomerVoteService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-23
 */
@Controller
public class CreditCustomerVoteController extends BaseController {

	@Autowired
	CreditCustomerVoteService creditCustomerVoteService;

	final static int PAGE_SIZE = 10;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out, PageDto<CreditCustomerVoteDto> page,
			CreditCustomerVoteDo vote) {

		SsoUser sessionUser = getCachedUser(request);
		vote.setToCompanyId(sessionUser.getCompanyId());
		vote.setCheckStatus("1");
		page.setPageSize(10);
		page.setDir("desc");
		page.setSort("ccv.id");

		page = creditCustomerVoteService.pageVoteByToCompany(vote, page);
		out.put("page", page);

		out.put("status", vote.getStatus());

		// 个人评价信息统计
		out.put("vote_0", creditCustomerVoteService.countVoteNumByToCompany(
				sessionUser.getCompanyId(), "0", "1", null));
		out.put("vote_1", creditCustomerVoteService.countVoteNumByToCompany(
				sessionUser.getCompanyId(), "1", "1", null));
		out.put("vote_2", creditCustomerVoteService.countVoteNumByToCompany(
				sessionUser.getCompanyId(), "2", "1", null));

		return null;
	}

	@RequestMapping
	public ModelAndView replyIndex(HttpServletRequest request,
			Map<String, Object> out, PageDto<CreditCustomerVoteDto> page,
			String p, CreditCustomerVoteDo vote) {
		if (p == null || !StringUtils.isNumber(p)) {
			p = "1";
		}

		SsoUser sessionUser = getCachedUser(request);
		vote.setFromCompanyId(sessionUser.getCompanyId());
		// vote.setCheckStatus("1");
		page.setPageSize(10);
		page.setDir("desc");
		page.setSort("ccv.id");
		page.setStartIndex((Integer.valueOf(p) - 1) * page.getPageSize());
		page = creditCustomerVoteService.pageVoteByFromCompany(vote, page);

		Integer totalPages = page.getTotalRecords() / page.getPageSize() + 1;
		if (page.getTotalRecords() % page.getPageSize() == 0) {
			totalPages--;
		}
		out.put("totalPages", totalPages);
		out.put("currentPage", Integer.valueOf(p));
		out.put("page", page);

		out.put("status", vote.getStatus());

		// 个人评价信息统计
		out.put("vote_0", creditCustomerVoteService.countVoteNumByToCompany(
				sessionUser.getCompanyId(), "0", "1", null));
		out.put("vote_1", creditCustomerVoteService.countVoteNumByToCompany(
				sessionUser.getCompanyId(), "1", "1", null));
		out.put("vote_2", creditCustomerVoteService.countVoteNumByToCompany(
				sessionUser.getCompanyId(), "2", "1", null));
		return null;
	}

	@RequestMapping
	public ModelAndView deleteVote(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		creditCustomerVoteService.deleteVoteByFromCompany(id, getCachedUser(request).getCompanyId());
		return new ModelAndView(new RedirectView("replyIndex.htm"));
	}

	@RequestMapping
	public ModelAndView replyToVote(HttpServletRequest request,
			Map<String, Object> out, Integer voteId, String replyContent)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = creditCustomerVoteService.replyToVote(voteId, replyContent);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	/**
	 * 在门市部中，用户之间互相评价
	 */
	@RequestMapping
	public ModelAndView voteToCompany(HttpServletRequest request,
			Map<String, Object> out, CreditCustomerVoteDo vote)
			throws IOException {
		// 不能给自己评价
		return printJs("parent.location.reload()", out);
	}

	// @RequestMapping
	// public ModelAndView initVoteToCompany(HttpServletRequest request,
	// Map<String, Object> out, Integer cid){
	// return null;
	// }
}
