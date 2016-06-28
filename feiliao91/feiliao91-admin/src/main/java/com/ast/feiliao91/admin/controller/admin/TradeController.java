package com.ast.feiliao91.admin.controller.admin;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.controller.BaseController;
import com.ast.feiliao91.domain.trade.CashAdvanceSearch;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.CashAdvanceDto;
import com.ast.feiliao91.service.trade.CashAdvanceService;
import com.zz91.util.auth.SessionUser;

@Controller
public class TradeController extends BaseController{
	@Resource
	private CashAdvanceService cashAdvanceService;//提现
	
	/**
	 * 默认页
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView cashAdvanceView(Map<String,Object> out){
		return null;
	}
	
	/**
	 * 
	 * @param out
	 * @param page
	 * @param search
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryAllCashAdvance(Map<String,Object> out,PageDto<CashAdvanceDto> page, CashAdvanceSearch search) throws IOException{
		page = cashAdvanceService.pageBySearch(page, search);
		return printJson(page, out);
	}
	
	/**
	 * 更新提现申请状态
	 * @param request
	 * @param out
	 * @param ids
	 * @param checkStatus
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateCashAdvanceStatus(HttpServletRequest request,Map<String,Object> out,String ids,Integer checkStatus) throws IOException{
		SessionUser sessionUser = getCachedUser(request);
		ExtResult result = new ExtResult();
		String s = cashAdvanceService.updateStatus(ids,checkStatus,sessionUser.getAccount());
		if(StringUtils.isEmpty(s)){
			result.setSuccess(false);
			result.setData("审核失败");
		}else{
			result.setSuccess(true);
			result.setData("审核成功");
		}
		return printJson(result, out);
	}
}









