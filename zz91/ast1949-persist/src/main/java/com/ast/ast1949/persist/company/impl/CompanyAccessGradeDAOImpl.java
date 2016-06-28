/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-4
 */
package com.ast.ast1949.persist.company.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccessGradeDO;
import com.ast.ast1949.persist.company.CompanyAccessGradeDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("companyAccessGradeDAO")
public class CompanyAccessGradeDAOImpl extends SqlMapClientDaoSupport implements CompanyAccessGradeDAO {

//	public Integer chengeAccessGradeCode(Map<String, Object> param) {
//		Assert.notNull(param, "the param must not be null");
//		return getSqlMapClientTemplate().update("companyAccessgrade.chengeAccessGradeCode", param);
//	}

	public Integer insertCompanyAccessGrade(
			CompanyAccessGradeDO companyAccessGradeDO) {
		Assert.notNull(companyAccessGradeDO, "the object of companyAccessGradeDO must not be null");
		
		return (Integer) getSqlMapClientTemplate().insert("companyAccessgrade.insertCompanyAccessGrade", companyAccessGradeDO);
	}

	public Integer queryCountByCompanyId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject("companyAccessgrade.queryCountByCompanyId", id);
	}

	public Integer deleteByCompanyId(Integer id) {
		return getSqlMapClientTemplate().delete("companyAccessgrade.deleteByCompanyId",id);
	}
	
//	final private int DEFAULT_BATCH_SIZE=20;
//	public Integer batchDeleteByCompanyId(int[] entities){
//		int impacted=0;
//		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)/DEFAULT_BATCH_SIZE;
//		try {
//			for(int currentBatch=0;currentBatch<batchNum;currentBatch++){
//				getSqlMapClient().startBatch();
//				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
//				int endIndex = (currentBatch+1)* DEFAULT_BATCH_SIZE;
//				endIndex = endIndex>entities.length?entities.length:endIndex;
//				for(int i=beginIndex;i<endIndex;i++){
//					impacted+=getSqlMapClientTemplate().delete("companyAccessgrade.deleteByCompanyId",entities[i]);
//				}
//				getSqlMapClient().executeBatch();
//			}
//		} catch (Exception e) {
//			throw new PersistLayerException("batch delete failed.",e);
//		}
//		return impacted;
//	}
}
