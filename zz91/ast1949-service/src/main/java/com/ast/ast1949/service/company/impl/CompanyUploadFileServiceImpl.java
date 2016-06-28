/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-16
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.persist.company.CompanyUploadFileDAO;
import com.ast.ast1949.service.company.CompanyUploadFileService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("companyUploadFileService")
public class CompanyUploadFileServiceImpl implements CompanyUploadFileService{

	@Autowired
	private CompanyUploadFileDAO companyUploadFileDAO;

	public Integer deleteComapanyUploadFileById(Integer id) {
		Assert.notNull(id, "id is not null");
		return companyUploadFileDAO.deleteComapanyUploadFileById(id);
	}

	public Integer insertCompanyUploadFile(
			CompanyUploadFileDO companyUploadFileDO) {
		Assert.notNull(companyUploadFileDO, "companyUploadFileDO is not null");
		return companyUploadFileDAO.insertCompanyUploadFile(companyUploadFileDO);
	}

	public List<CompanyUploadFileDO> queryByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "companyId is not null");
		return companyUploadFileDAO.queryByCompanyId(companyId);
	}

	@Override
	public CompanyUploadFileDO queryById(Integer id) {
		if(id==null || id.intValue()<=0){
			return new CompanyUploadFileDO();
		}
		return companyUploadFileDAO.queryById(id);
	}

//	public Integer updateCompanyUploadFile(
//			CompanyUploadFileDO companyUploadFileDO) {
//	   Assert.notNull(companyUploadFileDO, "companyUploadFileDO is not null");	
//		return companyUploadFileDAO.updateCompanyUploadFile(companyUploadFileDO);
//	}

//	public CompanyUploadFileDO queryByCompanyIdAndModefied(Integer companyId) {
//		Assert.notNull(companyId, "The companyId must not be null");
//		return companyUploadFileDAO.queryByCompanyIdAndModefied(companyId);
//	}
	
}
