/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-3
 */
package com.ast.ast1949.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAdminOprationinfoDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.persist.company.CompanyAdminOprationinfoDAO;
import com.ast.ast1949.service.company.CompanyAdminOprationinfoService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyAdminOprationinfoService")
public class CompanyAdminOprationinfoServiceImpl implements CompanyAdminOprationinfoService {

	@Autowired
	private CompanyAdminOprationinfoDAO companyAdminOprationinfoDAO;
	
//	public Integer countByCompanyId(Integer id) {
//		return companyAdminOprationinfoDAO.countByCompanyId(id);
//	}

//	public Integer insertCompanyAdminOprationinfo(
//			CompanyAdminOprationinfoDO companyAdminOprationinfo) {
//		
//		return companyAdminOprationinfoDAO.insertCompanyAdminOprationinfo(companyAdminOprationinfo);
//	}

//	public Integer updateSimplePropertyByCompanyId(
//			CompanyAdminOprationinfoDO companyAdminOprationinfo) {
//		
//		return companyAdminOprationinfoDAO.updateSimplePropertyByCompanyId(companyAdminOprationinfo);
//	}

	public CompanyAdminOprationinfoDO queryCompanyAdminOprationinfoByCompanyId(
			Integer id) {
		
		return companyAdminOprationinfoDAO.queryCompanyAdminOprationinfoByCompanyId(id);
	}

	public ExtResult add(CompanyAdminOprationinfoDO companyAdminOprationinfo) {
		Assert.notNull(companyAdminOprationinfo, "the object of companyAdminOprationinfo nust not be null");
		
		ExtResult result=new ExtResult();
		Integer count = companyAdminOprationinfoDAO.countByCompanyId(companyAdminOprationinfo.getCompanyId());
		if(count!=0){
			Integer im=companyAdminOprationinfoDAO.updateSimplePropertyByCompanyId(companyAdminOprationinfo);
			if(im.intValue()>0){
				result.setSuccess(true);
			}
		} else {
			Integer id=companyAdminOprationinfoDAO.insertCompanyAdminOprationinfo(companyAdminOprationinfo);
			if(id.intValue()>0){
				result.setSuccess(true);
			}
		}
		return result;
	}

}
