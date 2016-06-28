/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-28
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;

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
	
	public List<CompanyAccount> queryAccountByAdmin(CompanyAccount account, PageDto page);
	
	public Integer queryAccountByAdminCount(CompanyAccount account);

	public String queryContactByAccount(String senderAccount);
	
	public String queryMobileByCompanyId(Integer companyId);

	public Integer queryCompanyIdByMobile(String mobile);
	
	public CompanyAccount queryAccountByProductId(Integer productId);
	
	public CompanyAccount queryAccountByCompanyId(Integer id);
	
}
