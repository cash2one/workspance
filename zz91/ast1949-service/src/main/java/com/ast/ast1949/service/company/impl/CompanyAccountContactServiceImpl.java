/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-31
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccountContact;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyAccountContactDao;
import com.ast.ast1949.service.company.CompanyAccountContactService;
import com.zz91.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-31
 */
@Component("companyAccountContactService")
public class CompanyAccountContactServiceImpl implements
		CompanyAccountContactService {
	
	@Resource
	private CompanyAccountContactDao companyAccountContactDao;

	@Override
	public List<CompanyAccountContact> queryContactOfAccount(String account) {
		return companyAccountContactDao.queryContactOfAccount(account);
	}
	
	@Override
	public Integer createContact(CompanyAccountContact contact) {
		Assert.notNull(contact.getAccount(), "the contact can not be null");
		if(contact.getIsHidden()==null) {
			contact.setIsHidden("0");
		}
		return companyAccountContactDao.insertContact(contact);
	}

	@Override
	public Integer deleteContactByIdAndAccount(Integer id, String account) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(account, "the account can not be null");
		return companyAccountContactDao.deleteContactByAccount(id, account);
	}

	@Override
	public CompanyAccountContact queryOneContactById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return companyAccountContactDao.queryOneContactById(id);
	}

	@Override
	public PageDto<CompanyAccountContact> pageContactByCompany(String account,PageDto<CompanyAccountContact> page,String isHadden) {
		Assert.notNull(account, "the account can not be null");
		Assert.notNull(page, "the page can not be null");
		page.setRecords(companyAccountContactDao.queryContactByAccount(account, page,isHadden));
		page.setTotalRecords(companyAccountContactDao.queryContactByAccountCount(account,isHadden));
		return page;
	}

	@Override
	public Integer updateContactById(CompanyAccountContact contact) {
		Assert.notNull(contact, "the contact can not be null");
		return companyAccountContactDao.updateContactById(contact);
	}

}
