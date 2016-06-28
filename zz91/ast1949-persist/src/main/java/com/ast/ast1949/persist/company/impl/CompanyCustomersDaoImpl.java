/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-29
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.PaginationResult;
import com.ast.ast1949.domain.company.CompanyCustomersDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCustomersDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyCustomersDao;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("companyCustomersDao")
public class CompanyCustomersDaoImpl extends BaseDaoSupport implements CompanyCustomersDao {
	private static final String sqlPreFix = "companyCustomers";

	@Override
	public List<CompanyCustomersDO> queryCompanyCustomersForImportByInquiry(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(super.addSqlKeyPreFix(sqlPreFix,
				"queryCompanyCustomersForImportByInquiry"), companyId);
	}

	@Override
	public PageDto<CompanyCustomersDTO> queryCompanyCustomerListByCompanyIdAndGroupId(
			CompanyCustomersDO customer, PageDto page) {
		customer.setSqlKey(addSqlKeyPreFix(sqlPreFix, "queryCompanyCustomerListByCompanyIdAndGroupId"));
		PaginationResult paginationResult=queryPaginationData(customer, page);
		page.setTotalRecords(paginationResult.getResultTotal());
		page.setRecords(paginationResult.getResultList());
		return page;
	}

//	@SuppressWarnings("unchecked")
//	public List<CompanyCustomersDTO> queryCompanyCustomersForFront(
//			CompanyCustomersDTO companyCustomersDTO) {
//		Assert.notNull(companyCustomersDTO, "companyCustomersDTO is not null");
//		return template.queryForList("companyCustomers.queryCompanyCustomersForFront",
//				companyCustomersDTO);
//	}

//	@SuppressWarnings("unchecked")
//	public List<CompanyCustomersDO> queryCompanyCustomersByGroupId(Integer groupId) {
//		Assert.notNull(groupId, "groupId is not null");
//		return template.queryForList("companyCustomers.queryCompanyCustomersByGroupId", groupId);
//	}

	public Integer insertCompanyCustomers(CompanyCustomersDO companyCustomersDO) {
		Assert.notNull(companyCustomersDO, "companyCustomersDO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().insert("companyCustomers.insertCompanyCustomers",
				companyCustomersDO).toString());
	}

	public CompanyCustomersDO queryCompanyCustomersById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (CompanyCustomersDO) getSqlMapClientTemplate().queryForObject(
				"companyCustomers.queryCompanyCustomersById", id);
	}

	public Integer updateCompanyCustomers(CompanyCustomersDO companyCustomersDO) {

		return getSqlMapClientTemplate().update("companyCustomers.updateCompanyCustomers", companyCustomersDO);
	}

//	public Integer deleteCompanyCustomersById(Integer id) {
//
//		return template.delete("companyCustomers.deleteCompanyCustomersById", id);
//	}

	public Integer updateGroupById(Integer[] ids, Integer companyCustomersGroupId) {

		int impacted = 0;
		if (companyCustomersGroupId != null && ids.length > 0) {
			int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
			try {
				for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
					getSqlMapClient().startBatch();
					int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
					int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
					endIndex = endIndex > ids.length ? ids.length : endIndex;

					Map<String, Object> obj = new HashMap<String, Object>();
					for (int i = beginIndex; i < endIndex; i++) {
						obj.put("id", ids[i]);
						obj.put("companyCustomersGroupId", companyCustomersGroupId);
						impacted += getSqlMapClientTemplate().update("companyCustomers.updateGroupById", obj);
					}
					getSqlMapClient().executeBatch();

				}
			} catch (Exception e) {
				throw new PersistLayerException("batch set InquiryGroup failed.", e);
			}
		}
		return impacted;
	}

//	public Integer queryCompanyCustomersRecordCount(CompanyCustomersDTO companyCustomersDTO) {
//
//		return Integer.valueOf(template.queryForObject(
//				"companyCustomers.queryCompanyCustomersRecordCount", companyCustomersDTO)
//				.toString());
//	}

	final private int DEFAULT_BATCH_SIZE = 20;

	public Integer batchDeleteCompanyCustomersById(Integer[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().update("companyCustomers.deleteCompanyCustomersById",
							entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.", e);
		}
		return impacted;

	}

//	@SuppressWarnings("unchecked")
//	public List<CompanyCustomersDO> queryCompanyCustomersByCompanyId(Integer companyId) {
//		Assert.notNull(companyId, "The companyId must not be null");
//		return template
//				.queryForList("companyCustomers.queryCompanyCustomersByCompanyId", companyId);
//	}

	@Override
	public Integer updateCustomersGroup(Integer newGroupId,
			Integer companyCustomersId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newGroupId", newGroupId);
		map.put("companyCustomersId", companyCustomersId);
		return getSqlMapClientTemplate().update("companyCustomers.updateCustomersGroup", map);
	}

}
