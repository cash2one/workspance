package com.zz91.mail.controller.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.mail.controller.BaseController;
import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.domain.dto.ExtResult;
import com.zz91.mail.domain.dto.PageDto;
import com.zz91.mail.service.AccountService;

/**
 * 账户管理controller
 * @author Leon
 * 2011.11.17
 *
 */
@Controller
public class AccountController extends BaseController {
	
	@Resource
	private AccountService accountService;
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryAccounts(HttpServletRequest request, Map<String, Object> out, 
			 AccountDomain account, PageDto<AccountDomain> page){
		page = accountService.pageAllAccounts(account, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView createAccount(HttpServletRequest request, Map<String, Object> out, AccountDomain accountDomain){
		Integer i = accountService.insertAccount(accountDomain);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateAccount(HttpServletRequest request, Map<String, Object> out, AccountDomain account){
		Integer i = accountService.updateAccount(account);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteAccount(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i = accountService.deleteAccountById(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneAccount(HttpServletRequest request, Map<String, Object> out, Integer id){
		AccountDomain accountDomain = accountService.queryAccountById(id);
		PageDto<AccountDomain> page=new PageDto<AccountDomain>();
		List<AccountDomain> list=new ArrayList<AccountDomain>();
		list.add(accountDomain);
		page.setRecords(list);
		return printJson(page, out);
	}
	@RequestMapping
	public ModelAndView updatePauseStatus(HttpServletRequest request, Map<String, Object> out,
			Integer id, Integer pauseStatus){
		
		Integer i=accountService.updateStatusOfAccount(id, pauseStatus);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView reloadCache(HttpServletRequest request, Map<String, Object> out){
		accountService.initCache(null);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
}
