/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-28
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-28
 */
public interface CompanyAccountDao {

	public CompanyAccount queryAdminAccountByCompanyId(Integer id);
	
	public CompanyAccount queryAccountByAccount(String account);
	
	public Integer queryCompanyIdByEmail(String email);
	
	public Integer queryCompanyIdByAccount(String account);
	
	public Integer updatePassword(String account, String password);
	
	public Integer insertAccount(CompanyAccount account);
	
	public Integer updateAccountByUser(CompanyAccount account);
	
	public Integer countAccountOfMobile(String mobile);
	
	public Integer updateAccountByAdmin(CompanyAccount account);
	
	public List<CompanyAccount> queryAccountOfCompany(Integer companyId);
	
	public Integer updateLoginInfo(String account, String ip);
	
	public List<CompanyAccount> queryAccountByAdmin(CompanyAccount account,CompanyAccountSearchDto searchDto, PageDto<CompanyDto> page);
	
	public Integer queryAccountByAdminCount(CompanyAccount account,CompanyAccountSearchDto searchDto);

	public String queryContactByAccount(String senderAccount);
	
	public String queryMobileByCompanyId(Integer companyId);

	public Integer queryCompanyIdByMobile(String mobile);
	
	public CompanyAccount queryAccountByProductId(Integer productId);
	
	public CompanyAccount queryAccountByCompanyId(Integer id);
	
	public Integer updateInfoByaccount(CompanyAccount account); 
	
	public Integer countUserByEmail(String email);
	/**
	 * 获取公司id
	 * @param username
	 * @param password
	 * @return
	 */
	public Integer queryCompanyIdByNameAndPassw(String username,String password);
	/**
	 * zhengr根据公司ID查找账户帐号
	 * @param companyId
	 * @return
	 */
	public String queryCompanyAccountByCompanyId(Integer companyId);
	/**
	 * 更新登录次数和上一次登录时间
	 * @param account
	 */
	public void updateAccountByAccount(CompanyAccount account);
}
