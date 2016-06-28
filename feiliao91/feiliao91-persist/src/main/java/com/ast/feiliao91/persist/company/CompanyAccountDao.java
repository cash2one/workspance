package com.ast.feiliao91.persist.company;

import com.ast.feiliao91.domain.company.CompanyAccount;

public interface CompanyAccountDao {

	public Integer insert(CompanyAccount companyAccount);

	public Integer selectByAccountAndPassword(String account, String mobile, String email, String password);
	/**
	 * 1、手机帐号
	 * 2、邮箱
	 * 3、是否存在
	 * @param mobile
	 * @return
	 */
	public Integer countByMobileOrEmail(String account, String email);
	
	/**
	 * 根据帐号检索帐号信息
	 */
	public CompanyAccount queryByAccount(String account);
	
	/**
	 * 更改密码明文也改
	 */
	public Integer updatePwd(String account,String pwd,String pwdMD5);
	
	/**
	 * 根据公司id获取账户信息
	 */
	public CompanyAccount queryByCompanyId(Integer companyId);
	 /** 
	 * 修改或者保存手机号
	 */
	public Integer updatePhone(String account,String phone);

	/**
	 * 修改或者保存邮箱
	 */
	public Integer updateEmail(String account,String email);

	/**
	 * 根据支付密码，和 公司id 获取公司信息
	 */
	public CompanyAccount queryByCompanyIdAndPayPwd(Integer companyId, String payPassword);
	
	/**
	 * 更改余额
	 * @param companyId
	 * @param sumMoney
	 * @return
	 */
	public Integer updateSumMoney(Integer companyId,Float sumMoney);

	/**
	 * 修改支付密码
	 * @param account
	 * @param pwd
	 * @return
	 */
	public Integer updatePayPwd(String account, String pwd);

	public String selectPassWord(Integer companyId);
	
	/**
	 * 更新登录时间
	 * @param companyId
	 * @return
	 */
	public Integer updateGmtLastLogin(Integer companyId);
	/**
	 * 根据companyId修改公司信息
	 * @param companyAccount
	 * @return
	 */
	public Integer updateByCompanyAccount(CompanyAccount companyAccount);
}
