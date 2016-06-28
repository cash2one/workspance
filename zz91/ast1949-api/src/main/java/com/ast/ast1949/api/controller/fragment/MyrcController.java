package com.ast.ast1949.api.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.api.controller.BaseController;
import com.ast.ast1949.service.company.CrmCompanySvrService;

@Controller
public class MyrcController extends BaseController {
	@Resource
	CrmCompanySvrService crmCompanySvrService;

	/**
	 * 判断 myrc登录用户 是否开通商铺服务 
	 * 用于判断myrc Menu 商铺管理 是否显示
	 * @param request
	 * @param out
	 * @param cid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView validateSPService(HttpServletRequest request,
			Map<String, Object> out, Integer cid) throws IOException {
		Boolean spFlag = false;
		spFlag = crmCompanySvrService.validatePeriod(cid,CrmCompanySvrService.ESITE_CODE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spFlag", spFlag);
		return printJson(map, out);
	}
}