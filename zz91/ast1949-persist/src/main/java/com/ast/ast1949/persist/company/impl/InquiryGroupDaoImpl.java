/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-2
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.InquiryGroup;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.InquiryGroupDao;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Component("inquiryGroupDao")
public class InquiryGroupDaoImpl extends BaseDaoSupport implements InquiryGroupDao {
	
	final static String SQL_PREFIX="inquiryGroup";
	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryGroup> queryGroupOfCompany(Integer companyId, String account) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("account", account);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryGroupOfCompany"), root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryGroup> querySystemGroup() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySystemGroup"));
	}

	@Override
	public Integer insertGroup(InquiryGroup group) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertGroup"), group);
	}

	@Override
	public Integer updateGroup(InquiryGroup group) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateGroup"), group);
	}

	@Override
	public Integer deleteGroup(Integer id, String account) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("account", account);
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteGroup"), root);
	}

	@Override
	public String queryName(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryName"), id);
	}

//	final private int DEFAULT_BATCH_SIZE=20;
//
//	public Integer deleteInquiryGroupById(Integer id) {
//		return getSqlMapClientTemplate().delete("inquiryGroup.deleteInquiryGroupById", id);
//	}
//
//	public Integer insertInquiryGroup(InquiryGroupDO inquiryGroupDO) {
//		return (Integer) getSqlMapClientTemplate().insert("inquiryGroup.insertInquiryGroup", inquiryGroupDO);
//	}
//
////	public InquiryGroupDO queryInquiryGroupById(Integer id) {
////		return (InquiryGroupDO) getSqlMapClientTemplate().queryForObject("inquiryGroup.queryInquiryGroupById", id);
////	}
//
//	public Integer updateInquiryGroup(InquiryGroupDO inquiryGroupDO) {
//		return getSqlMapClientTemplate().update("inquiryGroup.updateInquiryGroup", inquiryGroupDO);
//	}
//
//
//	@SuppressWarnings("unchecked")
//	public List<InquiryGroupDO> queryInquiryGroupListByCompanyId(Integer companyId) {
//		return getSqlMapClientTemplate().queryForList("inquiryGroup.queryInquiryGroupListByCompanyId", companyId);
//	}
//
//	public Integer batchSetInquiryGroup(int[] inquiryArray, String inquiryGroupId) {
//		Assert.hasText(inquiryGroupId, "The inquiryGroupId must not be null");
//
//		int impacted=0;
//		if(inquiryArray!=null&&inquiryArray.length>0){
//			int batchNum = (inquiryArray.length + DEFAULT_BATCH_SIZE - 1)/DEFAULT_BATCH_SIZE;
//			try {
//				for(int currentBatch=0;currentBatch<batchNum;currentBatch++){
//					getSqlMapClient().startBatch();
//					int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
//					int endIndex = (currentBatch+1)* DEFAULT_BATCH_SIZE;
//					endIndex = endIndex>inquiryArray.length?inquiryArray.length:endIndex;
//
//					Map<String,Object> obj=new HashMap<String,Object>();
//					for(int i=beginIndex;i<endIndex;i++){
//						obj.put("id", inquiryArray[i]);
//						obj.put("inquiryGroupId", inquiryGroupId);
//						impacted += getSqlMapClientTemplate().update("inquiryGroup.batchSetInquiryGroup", obj);
//					}
//					getSqlMapClient().executeBatch();
//
//				}
//			} catch (Exception e) {
//				throw new PersistLayerException("batch set InquiryGroup failed.",e);
//			}
//		}
//
//		return impacted;
//	}
//
//	
//	@SuppressWarnings("unchecked")
//	public List<InquiryGroupDO> queryCustomizecInquiryGroupListByCompanyId(
//			Integer companyId) {
//		return getSqlMapClientTemplate().queryForList("inquiryGroup.queryCustomizecInquiryGroupListByCompanyId", companyId);
//	}



}
