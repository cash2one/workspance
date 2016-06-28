package com.ast.ast1949.web.controller.zz91.admin.credit;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditFileDTO;
import com.ast.ast1949.service.credit.CreditFileService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.MemcachedUtils;

@Controller
public class CreditFileController extends BaseController {

	@Autowired
	private CreditFileService creditFileService;
	@Autowired
	ScoreChangeDetailsService scoreChangeDetailsService;

	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void view(Map<String, Object> model) {
		model.put("uploadModel", WebConst.UPLOAD_MODEL_CREDIT);
		model.put("resourceUrl", MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
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
	public ModelAndView list(PageDto page, CreditFileDo creditFile, CreditFileDTO dto,
			Map<String, Object> model) throws IOException {
		if (page == null) {
			page = new PageDto(AstConst.PAGE_SIZE);
		}
		page.setSort("cf.id");
		page.setDir("desc");

//		dto.setPage(page);
		dto.setCreditFile(creditFile);

		page=creditFileService.pageCriditFileByCondition(dto, page);
//		page.setTotalRecords(creditFileService.countCreditFileByConditions(dto));
//		page.setRecords(creditFileService.queryCreditFileByConditions(dto));

		return printJson(page, model);
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
