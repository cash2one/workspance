package com.zz91.mail.service;

import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.domain.dto.PageDto;

public interface AccountService {
	
	/**
	 * 非暂停状态，账号可以被正常使用
	 */
	public final static int PAUSE_OFF = 1; 

	public AccountDomain queryAccountByCode(String code);

	public AccountDomain queryAccountByEmail(String email);

	public AccountDomain queryAccountByUsername(String username);

	public Integer insertAccount(AccountDomain account);

	public Integer updateAccount(AccountDomain account);

	public Integer deleteAccountById(Integer id);

	public Integer deleteAccountByHost(String code);

	public PageDto<AccountDomain> pageAllAccounts(AccountDomain account,
			PageDto<AccountDomain> page);

	public AccountDomain queryAccountById(Integer id);

	public Integer updateStatusOfAccount(Integer id, Integer pauseStatus);
	
	public void initCache(String version);
	
	public AccountDomain randomAccountFromCache(String code);
}
