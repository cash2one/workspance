/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.service.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAdminUserDO;
import com.ast.ast1949.persist.company.CompanyAdminUserDAO;
import com.ast.ast1949.service.company.CompanyAdminUserService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyAdminUserService")
public class CompanyAdminUserServiceImpl implements CompanyAdminUserService {
	
	@Autowired
	private CompanyAdminUserDAO companyAdminUserDAO;

//	public Integer insertCompanyAdminUserDO(
//			CompanyAdminUserDO companyAdminUserDO) {
//		Assert.notNull(companyAdminUserDO, "the object of companyAdminUserDO must not be null");
//		Assert.notNull(companyAdminUserDO.getCompanyId(),"the company id must not be null");
//		Assert.notNull(companyAdminUserDO.getAdminUserId(), " the admin user id must not be null");
//		
//		return companyAdminUserDAO.insertCompanyAdminUserDO(companyAdminUserDO);
//	}

//	public Integer changeAdminUserIdByCompanyId(Map<String, Object> param) {
//		Assert.notNull(param, "the param must not be null");
//		
//		return ;
//	}

//	public Integer queryCountByCompanyId(Integer id) {
//		Assert.notNull(id, "the id must not be null");
//		
//		return companyAdminUserDAO.queryCountByCompanyId(id);
//	}

	public Integer assignCustomers(Integer companyId, Integer adminUserId) {
		Assert.notNull(companyId, "the companyId must not be null");
		Assert.notNull(adminUserId, "the adminUserId must not be null");
		Integer i=companyAdminUserDAO.queryCountByCompanyId(companyId);
		if(i.intValue()<=0){
			CompanyAdminUserDO companyAdminUserDO=new CompanyAdminUserDO();
			companyAdminUserDO.setAdminUserId(adminUserId);
			companyAdminUserDO.setCompanyId(companyId);
			
			return companyAdminUserDAO.insertCompanyAdminUserDO(companyAdminUserDO);
		} else {
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("adminUserId", adminUserId);
			param.put("companyId", companyId);
			return companyAdminUserDAO.changeAdminUserIdByCompanyId(param);
		}
	}

	public Integer deleteByCompanyId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return companyAdminUserDAO.deleteByCompanyId(id);
	}
}
