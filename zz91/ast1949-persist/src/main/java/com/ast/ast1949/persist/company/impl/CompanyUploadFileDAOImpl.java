/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-16
 */
package com.ast.ast1949.persist.company.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.persist.company.CompanyUploadFileDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("companyUploadFileDAO")
public class CompanyUploadFileDAOImpl extends SqlMapClientDaoSupport implements CompanyUploadFileDAO{

	public Integer deleteComapanyUploadFileById(Integer id) {
	      Assert.notNull(id, "id is not null");
		return getSqlMapClientTemplate().delete("companyUploadFile.deleteComapanyUploadFileById", id);
	}

	public Integer insertCompanyUploadFile(
			CompanyUploadFileDO companyUploadFileDO) {
		Assert.notNull(companyUploadFileDO, "companyUploadFileDO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().insert("companyUploadFile.insertCompanyUploadFile", companyUploadFileDO).toString());
	}

	@SuppressWarnings("unchecked")
	public List<CompanyUploadFileDO> queryByCompanyId(Integer companyId) {
		
		return getSqlMapClientTemplate().queryForList("companyUploadFile.queryByCompanyId", companyId);
	}

	@Override
	public CompanyUploadFileDO queryById(Integer id) {
		return (CompanyUploadFileDO) getSqlMapClientTemplate().queryForObject("companyUploadFile.queryById", id);
	}

//	public Integer updateCompanyUploadFile(
//			CompanyUploadFileDO companyUploadFileDO) {
//		return getSqlMapClientTemplate().update("companyUploadFile.updateCompanyUploadFile", companyUploadFileDO);
//	}

//	public CompanyUploadFileDO queryByCompanyIdAndModefied(Integer companyId) {
//		return (CompanyUploadFileDO)getSqlMapClientTemplate()
//			.queryForObject("companyUploadFile.queryByCompanyIdAndModefied",companyId);
//	}

}
