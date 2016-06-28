package com.ast.ast1949.web.controller.zz91.admin.credit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditFileDTO;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class CreditFileController extends BaseController {

	@Autowired
	private CreditFileService creditFileService;
	@Autowired
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private CompanyAttestService companyAttestService;
	@Resource 
	private CompanyService companyService;
	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view(Map<String, Object> model) {
		model.put("uploadModel", WebConst.UPLOAD_MODEL_CREDIT);
		model.put("resourceUrl", MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
	}
	
	@RequestMapping
	public void edit(String id,String companyId,String activeFlg, Map<String, Object> model) {
		model.put("id", id);
		model.put("companyId", companyId);
		
		//用于激活选项卡的位置
		if ("1".equals(activeFlg)) {
			model.put("activeFlg", activeFlg);
		} else {
			model.put("activeFlg", "0");
		}
		
		CompanyAttest companyAttest = companyAttestService.queryAttestByCid(Integer.valueOf(companyId));
		String flag = "";
		if (companyAttest != null) {
			flag ="1";
			model.put("attestId", companyAttest.getId());
		} else {
			flag = "0";
		}
		model.put("flag", flag);
 	}
	@RequestMapping
	public ModelAndView init(Integer id, Map<String, Object> model) throws IOException {
		
		CreditFileDo creditFile =  creditFileService.queryFileById(id);
		List<CreditFileDo> list  =  new ArrayList<CreditFileDo>();
		list.add(creditFile);
		return printJson(list, model);
 	}
	/**
	 * 读取列表
	 * 
	 * @param page
	 * @param creditFile
	 * @param dto
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView list(PageDto<CreditFileDTO> page, CreditFileDo creditFile, CreditFileDTO dto,
			Map<String, Object> model) throws IOException {
		page.setSort("cf.id");
		page.setDir("desc");

//		dto.setPage(page);
		dto.setCreditFile(creditFile);

		page=creditFileService.pageCriditFileByCondition(dto, page);
//		page.setTotalRecords(creditFileService.countCreditFileByConditions(dto));
//		page.setRecords(creditFileService.queryCreditFileByConditions(dto));

		return printJson(page, model);
	}
	
	@RequestMapping
	public void listOfFile(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("uploadModel", WebConst.UPLOAD_MODEL_CREDIT);
		out.put("resourceUrl", MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
		out.put("companyId", companyId);
	}
	//进入门市部的诚信档案
	@RequestMapping
	public ModelAndView goEsite(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		if (companyId == null) {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("front"));
		}
		Company company = companyService.queryDomainOfCompany(companyId);
		if (StringUtils.isNotEmpty(company.getDomainZz91())) {
			return new ModelAndView("redirect:http://"+company.getDomainZz91()+".zz91.com/cxda.htm?type=1");
		} else {
			return new ModelAndView("redirect:"
					+ AddressTool.getAddress("front"));
		}
		
	}
	/**
	 * 修改审核状态
	 * @param model
	 * @param ids 记录编号
	 * @param checkStatus 状态值
	 * @param cids 公司编号
	 * @param currents 当前状态
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "updateCheckStatus.htm", method = RequestMethod.GET)
	public ModelAndView updateCheckStatus(Map<String, Object> model, String ids,
			String checkStatus, String cids, String currents, HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		
		if (currents!=null&&currents.equals(checkStatus)) {
			result.setSuccess(true);
		} else {
			if (StringUtils.isNumber(ids)) {
				Integer affected = creditFileService.updateCheckStatusByAdmin(Integer
						.valueOf(ids), checkStatus, sessionUser.getAccount(), Integer
						.valueOf(cids));
				if (affected.intValue() > 0) {
					scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(
							Integer.valueOf(cids), null, "get_credit_file", null,
							Integer.valueOf(ids), null));
					
					result.setSuccess(true);
				}
			}
		}
		
		return printJson(result, model);
	}
	@RequestMapping
	public ModelAndView updateFile(Map<String, Object> model, CreditFileDo creditFile, HttpServletRequest request)
			throws IOException {
		ExtResult result = new ExtResult();
		//截取正确的图片数据库存储地址
		if(StringUtils.isNotEmpty(creditFile.getPicName())) {
			String  picNameg=creditFileService.selectpicNameByCompanyId(creditFile);		
			creditFile.setPicName(picNameg);
		}
		//仅仅是保存更新
		Integer i = creditFileService.updateFileByAdmin(creditFile);
		if (i != null && i.intValue()>0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		
		return printJson(result, model);
	}
//	/**
//	 * 删除
//	 * @param model
//	 * @param ids 编号
//	 * @param cids 公司编号
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping
//	public ModelAndView delete(Map<String, Object> model, String ids, String cids)
//			throws IOException {
//		ExtResult result = new ExtResult();
//
//		if (StringUtils.isNumber(ids)) {
//			if (creditFileService.deleteFileById(Integer.valueOf(ids),
//					Integer.valueOf(cids)).intValue() > 0) {
//				result.setSuccess(true);
//			}
//		}
//
//		return printJson(result, model);
//	}
}
