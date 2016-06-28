package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.TradeLogDto;
import com.ast.feiliao91.service.company.CompanyAccountService;
import com.ast.feiliao91.service.goods.OrdersService;
import com.ast.feiliao91.service.trade.CashAdvanceService;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.zz91.util.banknumber.BankNumberUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class CashController extends BaseController{
	@Resource
	private OrdersService ordersService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CashAdvanceService cashAdvanceService;
	@Resource
	private TradeLogService tradeLogService;
	@RequestMapping
	public ModelAndView index(Map<String, Object> out ,HttpServletRequest request,PageDto<TradeLogDto> page){
		SeoUtil.getInstance().buildSeo("cash_index", out);
		SsoUser ssoUser = getCachedUser(request);
//		page = ordersService.pageMyOrder(ssoUser.getCompanyId(), page);
		page = tradeLogService.pageMyBill(ssoUser.getCompanyId(), page);
		out.put("page", page); // 账单信息
		//查询密码是否存在
		Integer i=companyAccountService.judgePassWord(ssoUser.getCompanyId());
		out.put("i", i);
		CompanyAccount account = companyAccountService.queryAccountByCompanyId(ssoUser.getCompanyId());
		out.put("account",account); //账户信息 
		return new ModelAndView();
	}
	
	/**
	 * 提现弹窗
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView getmoneypop(Map<String, Object> out,HttpServletRequest request){
		SsoUser ssoUser = getCachedUser(request);
		out.put("companyId", ssoUser.getCompanyId());
		return null;
	}
	
	/**
	 * 验证银行帐号
	 * 6228482898203884775
	 */
	@RequestMapping
	public ModelAndView validateBankAccount(Map<String, Object> out,String bank_account )throws IOException {
		ExtResult result = new ExtResult();
		do {
			if (bank_account.length() == 16 || bank_account.length() == 19) {  

			}else{
				result.setData("卡号位数无效");
				break;
			}
			if (BankNumberUtil.checkBankCard(bank_account) == false) {  
				result.setData("卡号校验失败");
				break;
			} 
			String name = BankNumberUtil.getNameOfBank(bank_account.substring(0, 6), 0);
			String[] firstName = name.split("\\.");
			//士琳说只显示到银行为止
			result.setData(firstName[0]);
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 插入CashAdvance表
	 */
	@RequestMapping
	public ModelAndView insertCashAdvance(Map<String, Object> out,
			String companyId,String bankAccount,String bank,String bankName,
			String linkman,String mobile,String money,HttpServletRequest request)throws IOException {
		ExtResult result = new ExtResult();
		do {
			if (StringUtils.isEmpty(companyId) || StringUtils.isEmpty(bankAccount) || StringUtils.isEmpty(bankName) 
					|| StringUtils.isEmpty(bank) || StringUtils.isEmpty(linkman) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(money)) {
				break;
			}
			
			Float getMoney = Float.parseFloat(money);
			SsoUser ssoUser = getCachedUser(request);
			CompanyAccount account = companyAccountService.queryAccountByCompanyId(ssoUser.getCompanyId());
			//提现金额是否大于当前余额
			if(getMoney>account.getSumMoney() || getMoney == 0.0){
				result.setData("0");
				break;
			}
			CashAdvance cashAdvance = new CashAdvance();
			cashAdvance.setCompanyId(Integer.valueOf(companyId));
			cashAdvance.setBankAccount(bankAccount);
			cashAdvance.setBankName(bankName);
			cashAdvance.setBank(bank);
			cashAdvance.setLinkman(linkman);
			cashAdvance.setMobile(mobile);
			cashAdvance.setMoney(getMoney);
			cashAdvance.setStatus(0);
			//1.添加提现记录
			Integer i = cashAdvanceService.insertCashAdvance(cashAdvance);
			//2.减钱
			Integer j = companyAccountService.updateSumMoney(Integer.valueOf(companyId),account.getSumMoney()-getMoney);
			//3.添加流水小计
			Integer k = tradeLogService.insertCashAdvance(Integer.valueOf(companyId),i,getMoney);
			if (i > 0 && j ==1 && k > 0) {
				result.setData("1");
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 提现成功也
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView getmoney_succ(Map<String, Object> out) {
		return null;
	}
}
