package com.zz91.mail.dao;

import java.util.List;

import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.domain.dto.PageDto;

public interface AccountDao {

	/**
	 * 根据账户编号搜索记录
	 * 
	 * @param code
	 * @return
	 */
	public AccountDomain queryAccountByCode(String code);

	/**
	 * 根据账户email搜索记录
	 * 
	 * @param email
	 * @return
	 */
	public AccountDomain queryAccountByEmail(String email);

	/**
	 * 根据账户username搜索记录
	 * 
	 * @param username
	 * @return
	 */
	public AccountDomain queryAccountByUsername(String username);

	/**
	 * 插入一条账户记录
	 * 
	 * @param account
	 * @return
	 */
	public Integer insertAccount(AccountDomain account);

	/**
	 * 更新一条账户记录
	 * 
	 * @param account
	 * @return
	 */
	public Integer updateAccount(AccountDomain account);

	/**
	 * 删除一条账户记录根据模板ID
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteAccountById(Integer id);

	/**
	 * 删除一条账户记录根据host
	 * 
	 * @param host
	 * @return
	 */
	public Integer deleteAccountByHost(String host);

	public List<AccountDomain> queryAccount(AccountDomain account,
			PageDto<AccountDomain> page);

	public Integer queryAccountCount(AccountDomain account);

	public AccountDomain queryAccountById(Integer id);

	public Integer updateStatusOfAccount(Integer id, Integer pauseStatus);

	public List<AccountDomain> queryAllAccount(String code, Integer pauseStatus, Integer size);
}
