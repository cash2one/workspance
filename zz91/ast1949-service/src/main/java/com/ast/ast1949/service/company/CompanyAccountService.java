/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-28
 */
package com.ast.ast1949.service.company;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.exception.AuthorizeException;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-28
 */
public interface CompanyAccountService {

	public CompanyAccount queryAdminAccountByCompanyId(Integer id);
	
	public CompanyAccount queryAccountByAccount(String account);
	
	public Integer queryCompanyIdByEmail(String email);
	
	public Integer queryCompanyIdByAccount(String account);
	
	/**
	 * 验证用户是否合法
	 * <br />username:可以是 username，email，account(变更账户后的用户)
	 * <br />password:未加密的password
	 * <br />如果验证不通过抛出异常，如果验证通过，则返回账户(username)
	 */
	public String validateUser(String username, String password) 
		throws AuthorizeException, NoSuchAlgorithmException, UnsupportedEncodingException;
	
	public SsoUser initSessionUser(String account);
	
	/**
	 * 注册zz91账户
	 * <br />username：注册时申请的账户
	 * <br />email:注册时提交的email信息
	 * <br />password:注册时提交的密码
	 * <br />password2:二次密码
	 * <br />如果注册失败抛出异常，如果注册成功，返回账户(username)
	 * @throws Exception 
	 */
	public String registerUser(String username, String email, String password, 
			String password2, CompanyAccount account, Company company, String ip) 
			        throws Exception;
	
	/**
	 * 个人注册zz91账户
	 * <br />username：注册时申请的账户
	 * <br />password:注册时提交的密码
	 * <br />password2:二次密码
	 * <br />如果注册失败抛出异常，如果注册成功，返回账户(username)
	 * @throws Exception 
	 */
	public String registerUserAccount(String username,String password, 
			String password2, CompanyAccount account, Company company, String ip) 
			        throws Exception;
	
	public Integer updateAccountByUser(CompanyAccount account);
	
	public Integer countAccountOfMobile(String mobile);
	
	public String currentEmail(CompanyAccount account);
	
	public boolean changePassword(String account, String originalPassword, 
			String newPassword, String verifyPassword)
		throws AuthorizeException, NoSuchAlgorithmException, UnsupportedEncodingException, AuthorizeException;
	
	public Integer updateAccountByAdmin(CompanyAccount account);
	
	public List<CompanyAccount> queryAccountOfCompany(Integer companyId);
	
	public void updateLoginInfo(String account, String ip);
	
	public PageDto<CompanyDto> queryAccountByAdmin(CompanyAccount account,CompanyAccountSearchDto searchDto, PageDto<CompanyDto> page);
	
	public boolean resetPasswordByAdmin(String account, String password);

	public Integer queryComapnyIdByMobile(String mobile);

	public CompanyAccount queryAccountByProductId(Integer productId);

	public CompanyAccount queryAccountByCompanyId(Integer id);

	public SsoUser validateQQLogin(String account, String ip);
	
	public Integer checkEmail(String username,String email) throws Exception;
	
	public Integer updateInfoByaccount(CompanyAccount account);
	
	public Integer countUserByEmail(String email);
	
	public  PageDto<CompanyDto> queryAccountByAdminSearch(CompanyAccount account,CompanyAccountSearchDto searchDto,Company company, PageDto<CompanyDto> page);
	/**
	 * 获取公司id
	 * @param username
	 * @param password
	 * @return
	 */
	public Integer queryCompanyIdByNameAndPassw(String username,String password);	
	
	public void updateAccountByAccount(CompanyAccount account);

}