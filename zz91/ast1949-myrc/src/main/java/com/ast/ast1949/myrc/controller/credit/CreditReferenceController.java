/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-23
 */
package com.ast.ast1949.myrc.controller.credit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.credit.CreditReferenceDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.credit.CreditReferenceService;
import com.zz91.util.auth.frontsso.SsoUser;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-23
 */
@Controller
public class CreditReferenceController extends BaseController {

	@Autowired
	CreditReferenceService creditReferenceService;

	final static int MAX_REFERENCE = 10;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "资信参考人");

		List<CreditReferenceDo> list = creditReferenceService
				.queryReferenceByCompany(getCachedUser(request).getCompanyId());
		out.put("referenceList", list);
		out.put("numReference", list.size());
		out.put("maxReference", MAX_REFERENCE);
		return null;
	}

	@RequestMapping
	public ModelAndView create(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "添加 资信参考人");
		return null;
	}

	@RequestMapping
	public ModelAndView insertReference(HttpServletRequest request,
			Map<String, Object> out, CreditReferenceDo reference)
			throws IOException {
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		reference.setAccount(sessionUser.getAccount());
		reference.setCompanyId(sessionUser.getCompanyId());
		reference.setCheckStatus("0");

		Integer i = creditReferenceService.insertReferenceByCompany(reference);
		if (i > 0) {
			result.setSuccess(true);
			return new ModelAndView("redirect:index.htm");
		}
		if (i == 0) {
			result.setData("overLimit");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView edit(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put(FrontConst.MYRC_SUBTITLE, "修改 资信参考人");
		CreditReferenceDo reference = creditReferenceService
				.queryReferenceById(id);
		out.put("reference", reference);
		return null;
	}

	@RequestMapping
	public ModelAndView updateReference(HttpServletRequest request,
			Map<String, Object> out, CreditReferenceDo reference)
			throws IOException {
		ExtResult result = new ExtResult();
		reference.setCompanyId(getCachedUser(request).getCompanyId());
		Integer i = creditReferenceService.updateReferenceByCompany(reference);
		if (i > 0) {
			result.setSuccess(true);
			return new ModelAndView("redirect:index.htm");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView deleteReference(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		creditReferenceService.deleteReferenceByCompany(id, getCachedUser(request).getCompanyId());
		return new ModelAndView(new RedirectView("index.htm"));
	}
}
