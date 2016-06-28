/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-23
 */
package com.ast.ast1949.myrc.controller.credit;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.myrc.controller.BaseController;
import com.ast.ast1949.myrc.util.FrontConst;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-23
 */
@Controller
public class CreditFileController extends BaseController {

	@Autowired
	CreditFileService creditFileService;
	@Autowired
	CreditIntegralDetailsService creditIntegralDetailsService;

	final static String FILE_CATEGORY = "1040";
	final static String FILE_OPERATION_KEY = "credit_file";
	final static String UPLOAD_MODEL = "creditfile";

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out) {
		out.put(FrontConst.MYRC_SUBTITLE, "荣誉证书");
		SsoUser sessionUser = getCachedUser(request);
		out.put("fileList", creditFileService.queryFileByCompany(sessionUser.getCompanyId()));

		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));

		out.put("categoryMap", CategoryFacade.getInstance()
				.getChild(FILE_CATEGORY));

		out.put("integral", creditIntegralDetailsService
				.countIntegralByOperationKey(sessionUser.getCompanyId(),
						FILE_OPERATION_KEY));

		return null;
	}
	
	@RequestMapping
	public ModelAndView create(HttpServletRequest request,
			Map<String, Object> out, String code) {
		out.put(FrontConst.MYRC_SUBTITLE, "添加 荣誉证书");

		out.put("categoryMap", CategoryFacade.getInstance()
				.getChild(FILE_CATEGORY));
//		out.put("uploadModel", UPLOAD_MODEL);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		out.put("code", code);
		return null;
	}

	@RequestMapping
	public ModelAndView insertFile(HttpServletRequest request,
			Map<String, Object> out, CreditFileDo file, String startTimeStr,
			String endTimeStr) throws IOException {
		ExtResult result = new ExtResult();
		SsoUser sessionUser = getCachedUser(request);
		file.setCompanyId(sessionUser.getCompanyId());
		file.setAccount(sessionUser.getAccount());
		try {
			file.setStartTime(DateUtil.getDate(startTimeStr,
					AstConst.DATE_FORMATE_DEFAULT));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			file.setEndTime(DateUtil.getDate(endTimeStr,
					AstConst.DATE_FORMATE_DEFAULT));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer i = creditFileService.insertFileByCompany(file);
		if (i > 0) {
			result.setSuccess(true);
			return new ModelAndView("redirect:index.htm");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView edit(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		out.put(FrontConst.MYRC_SUBTITLE, "修改 荣誉证书");
		out.put("creditFile", creditFileService.queryFileById(id));
		out.put("categoryMap", CategoryFacade.getInstance()
				.getChild(FILE_CATEGORY));
		out.put("uploadModel", UPLOAD_MODEL);
		out.put("resourceUrl", (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		return null;
	}

	@RequestMapping
	public ModelAndView updateFile(HttpServletRequest request,
			Map<String, Object> out, CreditFileDo file, String startTimeStr,
			String endTimeStr) throws IOException {
		ExtResult result = new ExtResult();
		file.setCompanyId(getCachedUser(request).getCompanyId());
		try {
			file.setStartTime(DateUtil.getDate(startTimeStr,
					AstConst.DATE_FORMATE_DEFAULT));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			file.setEndTime(DateUtil.getDate(endTimeStr,
					AstConst.DATE_FORMATE_DEFAULT));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer i = creditFileService.updateFileById(file);
		if (i > 0) {
			result.setSuccess(true);
			return new ModelAndView("redirect:index.htm");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView deleteFile(HttpServletRequest request,
			Map<String, Object> out, Integer id) {
		creditFileService.deleteFileById(id, getCachedUser(request).getCompanyId());
		return new ModelAndView(new RedirectView("index.htm"));
	}

}
