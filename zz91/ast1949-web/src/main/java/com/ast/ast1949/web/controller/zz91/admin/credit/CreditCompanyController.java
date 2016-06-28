package com.ast.ast1949.web.controller.zz91.admin.credit;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.credit.CreditReferenceDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditReferenceDTO;
import com.ast.ast1949.service.credit.CreditReferenceService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;

@Controller
public class CreditCompanyController extends BaseController {

	@Autowired
	private CreditReferenceService creditCompanyService;

	@RequestMapping
	public void view() {
	}

	/**
	 * 初始化列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView list(PageDto page, CreditReferenceDo creditReference,
			CreditReferenceDTO dto, Map<String, Object> model) throws IOException {
		if (page == null) {
			page = new PageDto(AstConst.PAGE_SIZE);
		}
		page.setSort("cr.id");
		page.setDir("desc");

//		dto.setPage(page);
		dto.setCreditReference(creditReference);
		
		page = creditCompanyService.pageReferenceByConditions(dto, page);

		return printJson(page, model);
	}

	/**
	 * 审核信息
	 * @param model
	 * @param ids 记录编号集
	 * @param checkStatus  审核状态：0 未审核；1 已审核；2 审核不通过。
	 * @param cids 公司编号集
	 * @param currents 当前状态集
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView update(Map<String, Object> model, String ids, String checkStatus,String cids,String currents,
			HttpServletRequest request) throws IOException {
		ExtResult result = new ExtResult();
		
		SessionUser sessionUser = getCachedUser(request);

		if(currents!=null && currents.equals(checkStatus)) { //当前状态与要更新的状态相同
			result.setSuccess(true);
		} else {
			if (StringUtils.isNumber(ids)) {
				Integer affected = creditCompanyService.updateCheckStatusByAdmin(Integer
						.valueOf(ids), checkStatus, sessionUser.getAccount(),Integer.valueOf(cids));
				if (affected.intValue() > 0) {
					result.setSuccess(true);
				}
			}
		}

		return printJson(result, model);
	}

	/**
	 * 删除
	 * @param model
	 * @param ids
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model, String ids) throws IOException {
		ExtResult result = new ExtResult();
		String[] arr = ids.split(",");

		Integer im = 0;
		for (int i = 0; i < arr.length; i++) {
			if (StringUtils.isNumber(arr[i])) {
				 if(creditCompanyService.deleteReferenceById(Integer.valueOf(arr[i])).intValue()>0)
				 {
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
