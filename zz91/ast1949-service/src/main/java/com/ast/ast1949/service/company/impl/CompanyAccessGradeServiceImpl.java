/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-5
 */
package com.ast.ast1949.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccessGradeDO;
import com.ast.ast1949.persist.company.CompanyAccessGradeDAO;
import com.ast.ast1949.service.company.CompanyAccessGradeService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyAccessGradeService")
public class CompanyAccessGradeServiceImpl implements CompanyAccessGradeService {

	@Autowired
	private CompanyAccessGradeDAO companyAccessGradeDAO;
	
//	public Integer chengeAccessGradeCode(Map<String, Object> param) {
//		Assert.notNull(param, "the param must not be null");
//		
//		return companyAccessGradeDAO.chengeAccessGradeCode(param);
//	}

	public Integer insertCompanyAccessGrade(
			CompanyAccessGradeDO companyAccessGradeDO) {
		Assert.notNull(companyAccessGradeDO, "the object of companyAccessGradeDO must not be null");
		
		return companyAccessGradeDAO.insertCompanyAccessGrade(companyAccessGradeDO);
	}

	public Integer queryCountByCompanyId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return companyAccessGradeDAO.queryCountByCompanyId(id);
	}

//	public Integer blacklisted(int id) {
//		Assert.notNull(id, "the id must not be null");
//		
//		if (queryCountByCompanyId(id).intValue()>0) {
//			Map<String, Object> param=new HashMap<String, Object>();
//			param.put("accessGradeCode", 0);
//			param.put("companyId", id);
//			return chengeAccessGradeCode(param);
//		} else {
//			CompanyAccessGradeDO companyAccessGradeDO=new CompanyAccessGradeDO();
//			companyAccessGradeDO.setAccessGradCode("0");
//			companyAccessGradeDO.setCompanyId(id);
//			return insertCompanyAccessGrade(companyAccessGradeDO);
//		}
//	}

//	public Integer classify(int id, String code) {
//		Assert.notNull(id, "the id must not be null");
//		Assert.notNull(code, "the code must not be null");
//		
//		if (queryCountByCompanyId(id).intValue()>0) {
//			Map<String, Object> param=new HashMap<String, Object>();
//			param.put("accessGradeCode", code);
//			param.put("companyId", id);
//			
//			return chengeAccessGradeCode(param);
//		} else {
//			CompanyAccessGradeDO companyAccessGradeDO=new CompanyAccessGradeDO();
//			companyAccessGradeDO.setAccessGradCode(code);
//			companyAccessGradeDO.setCompanyId(id);
//			
//			return insertCompanyAccessGrade(companyAccessGradeDO);
//		}
//	}

//	public Integer batchDeleteByCompanyId(int[] entities) {
//		Assert.notNull(entities, "the entities must not be null");
//		return companyAccessGradeDAO.batchDeleteByCompanyId(entities);
//	}

	public Integer deleteByCompanyId(Integer id) {
		return companyAccessGradeDAO.deleteByCompanyId(id);
	}

}
