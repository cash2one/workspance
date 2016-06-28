package com.ast.feiliao91.service.company;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.company.CompanyAccount;

public interface CompanyAccountService {
	
	/**
	 *  指定唯一登录接口
	 *  
	 *  1验证帐号是否存在 feiliao91网 是则登录
	 *  2验证帐号是否存在 zz91网 是则导入 再登录
	 *  
	 * @param account
	 * @param password
	 * @return 1：登录成功 其他：登录失败
	 */
	public Integer doLogin(String account,String password);
	/**
	 *保存公司帐号信息
	 * @param account
	 * @return
	 */
	public Integer insertAccount(CompanyAccount account);
	/**
	 * 1、帐号
	 * 2、 邮箱
	 * 3、是否存在
	 * @param mobile
	 * @return
	 */
	public Integer hasAM(String account, String email);
	
	/**
	 * 登录模块使用初始化 ssouser
	 */
	public SsoUser initSsoUser(String account);
	/**
	 * 公司号码信息
	 * @param account
	 * @return
	 */
	public CompanyAccount queryAccountByAccount(String account);
	
	/**
	 * 公司号码信息
	 * @param companyId
	 * @return
	 */
	public CompanyAccount queryAccountByCompanyId(Integer companyId);
	
	/**
	 * 修改公司密码
	 */
	public Integer updatePwd(String account,String pwd,String pwdr);
	
	/**
	 * 修改公司密码
	 * @param account
	 * @param pwd
	 * @return
	 */
	public Integer updatePwd(String account,String pwd);
	/**
	 * 保存或者修改公司手机号
	 */
	public Integer updatePhone(String account,String phone);
	/**
	 * 保存或者修改公司邮件
	 */
	public Integer updateEmail(String account,String email);
	
	/**
	 * 根据公司以及支付密码 获得公司账户信息
	 * @param companyId
	 * @param payPassword
	 * @return
	 */
	public CompanyAccount queryByCompanyIdAndPayPwd(Integer companyId,String payPassword);
	
	public Integer updateSumMoney(Integer companyId,Float sumMoney);
	
	/**
	 * 修改支付密码
	 * @param account
	 * @param pwd
	 * @return
	 */
	public Integer updatePayPwd(String account ,String pwd);
	
	/**
	 * 判断支付密码是否存在
	 * return 0 不存在　１存在
	 */
    public Integer judgePassWord(Integer companyId);
    
    /**
     * 更新用户登录时间
     * @param companyId
     */
	public Integer updateGmtLastLogin(Integer companyId);
	/**
	 * 根据companyId修改公司信息
	 * @param companyAccount
	 * @return
	 */
	public Integer updateByCompanyAccount(CompanyAccount companyAccount);
}
