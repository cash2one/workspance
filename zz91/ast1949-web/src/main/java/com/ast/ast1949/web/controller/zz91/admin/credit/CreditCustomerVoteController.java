package com.ast.ast1949.web.controller.zz91.admin.credit;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.credit.CreditCustomerVoteDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditCustomerVoteDto;
import com.ast.ast1949.service.credit.CreditCustomerVoteService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;

@Controller
public class CreditCustomerVoteController extends BaseController {

	@Autowired
	private CreditCustomerVoteService creditCustomerVoteService;

	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view() {

	}

	/**
	 * 初始化列表
	 */
	@RequestMapping
	public ModelAndView list(PageDto page, CreditCustomerVoteDo vote,
			CreditCustomerVoteDto dto, Map<String, Object> model)
			throws IOException {

		if (page == null) {
			page = new PageDto(AstConst.PAGE_SIZE);
		}
		page.setSort("ccv.id");
		page.setDir("desc");

		dto.setVote(vote);
		page = creditCustomerVoteService.pageCreditCustomerVoteByConditions(dto, page);

		return printJson(page, model);
	}

	/**
	 * 修改状态
	 */
	@RequestMapping
	public ModelAndView updateCheckStatus(String ids, String checkStatus,
			Map<String, Object> model, HttpServletRequest request, String cids,
			String status, String currents) throws IOException {
		ExtResult result = new ExtResult();

		SessionUser sessionUser = getCachedUser(request);
		String[] arr = ids.split(",");
		String[] companyId = cids.split(",");
		String[] sta = status.split(",");
		String[] current = currents.split(",");

		Integer im = 0;
		for (int i = 0; i < arr.length; i++) {
			if (checkStatus.equals(current[i])) {
				im++;
			} else {
				if (StringUtils.isNumber(arr[i])) {
					Integer affected = creditCustomerVoteService
							.updateCheckStatusByAdmin(Integer.valueOf(arr[i]),
									checkStatus, sessionUser.getAccount(), Integer
											.valueOf(companyId[i]), sta[i]);
					if (affected.intValue() > 0) {
						im++;
					}
				}
			}
		}

		if (im.intValue() == arr.length) {
			result.setSuccess(true);
		}

		return printJson(result, model);
	}

	/**
	 * 删除
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model, String ids,
			String cids) throws IOException {
		ExtResult result = new ExtResult();
		String[] arr = ids.split(",");
		String[] companyId = cids.split(",");

		Integer im = 0;
		for (int i = 0; i < arr.length; i++) {
			if (StringUtils.isNumber(arr[i])) {
				Integer affected = creditCustomerVoteService
						.deleteVoteByFromCompany(Integer.valueOf(arr[i]),
								Integer.valueOf(companyId[i]));
				if (affected.intValue() > 0) {
					im++;
				}
			}
		}

		if (im.intValue() == arr.length) {
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

}
