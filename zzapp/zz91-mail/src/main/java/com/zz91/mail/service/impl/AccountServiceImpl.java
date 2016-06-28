package com.zz91.mail.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.mail.dao.AccountDao;
import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.domain.dto.PageDto;
import com.zz91.mail.service.AccountService;
import com.zz91.util.Assert;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	final static Map<String, List<AccountDomain>> ACCOUNT_CACHE=new HashMap<String, List<AccountDomain>>();

	@Resource
	private AccountDao accountDao;

	@Override
	public AccountDomain queryAccountByCode(String code) {
		return accountDao.queryAccountByCode(code);
	}

	@Override
	public AccountDomain queryAccountByEmail(String email) {
		return accountDao.queryAccountByEmail(email);
	}

	@Override
	public Integer insertAccount(AccountDomain Account) {
		if (Account.getIsDelete() == null || Account.getIsDelete() != 1) {
			Account.setIsDelete(0);
		}
		if (Account.getPauseStatus() == null || Account.getPauseStatus() != 1) {
			Account.setPauseStatus(0);
		}
		return accountDao.insertAccount(Account);
	}

	@Override
	public Integer updateAccount(AccountDomain Account) {
		if (Account.getIsDelete() == null || Account.getIsDelete() != 1) {
			Account.setIsDelete(0);
		}
		if (Account.getPauseStatus() == null || Account.getPauseStatus() != 1) {
			Account.setPauseStatus(0);
		}
		return accountDao.updateAccount(Account);
	}

	@Override
	public Integer deleteAccountById(Integer id) {
		return accountDao.deleteAccountById(id);
	}

	@Override
	public Integer deleteAccountByHost(String Host) {
		return accountDao.deleteAccountByHost(Host);
	}

	@Override
	public PageDto<AccountDomain> pageAllAccounts(AccountDomain account,
			PageDto<AccountDomain> page) {
		page.setRecords(accountDao.queryAccount(account, page));
		page.setTotals(accountDao.queryAccountCount(account));
		return page;
	}

	@Override
	public AccountDomain queryAccountById(Integer id) {
		return accountDao.queryAccountById(id);
	}

	@Override
	public Integer updateStatusOfAccount(Integer id, Integer pauseStatus) {
		Assert.notNull(id, "the id can not be null");
		if (pauseStatus != 1) {
			pauseStatus = 0;
		}
		return accountDao.updateStatusOfAccount(id, pauseStatus);
	}

	@Override
	public AccountDomain queryAccountByUsername(String username) {
		return accountDao.queryAccountByUsername(username);
	}

	@Override
	public void initCache(String version) {
		
		ACCOUNT_CACHE.clear();

		List<AccountDomain> list=accountDao.queryAllAccount(null, AccountService.PAUSE_OFF, 100);
		
		for(AccountDomain account:list){
			List<AccountDomain> subList=ACCOUNT_CACHE.get(account.getCode());
			if(subList==null){
				subList=new ArrayList<AccountDomain>();
				ACCOUNT_CACHE.put(account.getCode(), subList);
			}
			subList.add(account);
		}
		
	}

	@Override
	public AccountDomain randomAccountFromCache(String code) {
		if(code==null){
			return null;
		}
		
		List<AccountDomain> list=ACCOUNT_CACHE.get(code);
		
		if(list==null){
			return null;
		}
		
		if(list.size()>0){
			int i=(int) (Math.random()*list.size());
			return list.get(i);
		}
		
		return null;
	}

}
