/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-31
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyAccountContact;
import com.ast.ast1949.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-31
 */
public interface CompanyAccountContactDao {
	
	public List<CompanyAccountContact> queryContactOfAccount(String account);
	
	public List<CompanyAccountContact> queryContactByAccount(String account, PageDto<CompanyAccountContact> page,String isHidden);
	
	public Integer queryContactByAccountCount(String account,String isHadden);
	
	public Integer insertContact(CompanyAccountContact contact);
	
	public Integer updateContactById(CompanyAccountContact contact);
	
	public Integer deleteContactByAccount(Integer id, String account);
	
	public CompanyAccountContact queryOneContactById(Integer id);
}
