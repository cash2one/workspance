/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-31
 */
package com.ast.ast1949.service.company;


import java.util.List;

import com.ast.ast1949.domain.company.CompanyAccountContact;
import com.ast.ast1949.dto.PageDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-31
 */
public interface CompanyAccountContactService {
	
	public List<CompanyAccountContact> queryContactOfAccount(String account);
	
	public PageDto<CompanyAccountContact> pageContactByCompany(String account,PageDto<CompanyAccountContact> page,String isHadden);
	
	public Integer createContact(CompanyAccountContact contact);
	
	public Integer updateContactById(CompanyAccountContact contact);
	
	public Integer deleteContactByIdAndAccount(Integer id, String account);
	
	public CompanyAccountContact queryOneContactById(Integer id);
}
