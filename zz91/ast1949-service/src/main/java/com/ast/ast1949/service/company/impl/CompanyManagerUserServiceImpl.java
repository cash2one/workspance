/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-28
 */
package com.ast.ast1949.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyManagerUserDO;
import com.ast.ast1949.persist.company.CompanyManagerUserDAO;
import com.ast.ast1949.service.company.CompanyManagerUserService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyManagerUserService")
public class CompanyManagerUserServiceImpl implements CompanyManagerUserService {

	@Autowired
	private CompanyManagerUserDAO companyManagerUserDAO;
	
//	public Integer countCompanyManagerUserByCompanyId(Integer id) {
//		Assert.notNull(id, "the id must be not null");
//		return companyManagerUserDAO.countCompanyManagerUserByCompanyId(id);
//	}

//	public Integer deleteCompanyManagerUserByCompanyId(Integer id) {
//		Assert.notNull(id, "the id must be not null");
//		return companyManagerUserDAO.deleteCompanyManagerUserByCompanyId(id);
//	}

//	public Integer insertCompanyManagerUser(
//			CompanyManagerUserDO companyManagerUser) {
//		Assert.notNull(companyManagerUser, "the object of companyManagerUser must be not null");
//		return companyManagerUserDAO.insertCompanyManagerUser(companyManagerUser);
//	}

//	public Integer updateAdminUserIdByCompanyId(
//			CompanyManagerUserDO companyManagerUser) {
//		Assert.notNull(companyManagerUser, "the object of companyManagerUser must be not null");
//		return companyManagerUserDAO.updateAdminUserIdByCompanyId(companyManagerUser);
//	}

	public boolean join(CompanyManagerUserDO companyManagerUser) {
		Assert.notNull(companyManagerUser, "the object of companyManagerUser must be not null");
		
		boolean success=false;
		Integer count=companyManagerUserDAO.countCompanyManagerUserByCompanyId(companyManagerUser.getCompanyId());
		if(count.intValue()<=0){
			Integer im= companyManagerUserDAO.insertCompanyManagerUser(companyManagerUser);
			if(im.intValue()<=0){
				success=false;
			} else {
				success=true;
			}
		} else {
			Integer im2= companyManagerUserDAO.updateAdminUserIdByCompanyId(companyManagerUser);
			if(im2.intValue()<=0){
				success=false;
			} else {
				success=true;
			}
		}
		
		return success;
	}

	public boolean cancelJoin(Integer id) {
		Assert.notNull(id, "the id must be not null");
		
		boolean success=false;
		
		Integer count=companyManagerUserDAO.countCompanyManagerUserByCompanyId(id);
		if(count.intValue()>0){
			Integer im= companyManagerUserDAO.deleteCompanyManagerUserByCompanyId(id);
			if(im.intValue()<=0){
				success=false;
			} else {
				success=true;
			}
		} else {
			success=true;
		}
		return success;
	}

	public CompanyManagerUserDO queryCompanyManagerUserByCompanyId(Integer id) {
		Assert.notNull(id, "the id must be not null");
		return companyManagerUserDAO.queryCompanyManagerUserByCompanyId(id);
	}

}
