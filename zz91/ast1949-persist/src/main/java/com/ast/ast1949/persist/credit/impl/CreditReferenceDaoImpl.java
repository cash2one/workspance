package com.ast.ast1949.persist.credit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.credit.CreditReferenceDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditReferenceDTO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.credit.CreditReferenceDao;
import com.ast.ast1949.util.Assert;

@Component("creditReferenceDao")
public class CreditReferenceDaoImpl extends BaseDaoSupport implements CreditReferenceDao {

	final static String SQL_PREFIX = "creditReference";

	@Override
	public Integer countReferenceByCompany(Integer companyId, String checkStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("checkStatus", checkStatus);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countReferenceByCompany"), root);
	}

	@Override
	public Integer deleteReferenceByCompany(Integer id, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("id", id);
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteReferenceByCompany"), root);
	}

	@Override
	public Integer insertReferenceByCompany(CreditReferenceDo reference) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertReferenceByCompany"), reference);
	}

	@Override
	public List<CreditReferenceDo> queryReferenceByCompany(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryReferenceByCompany"), companyId);
	}

	@Override
	public Integer updateCheckStatusByAdmin(Integer id, String status, String person) {
		return null;
	}

	@Override
	public Integer updateReferenceByCompany(CreditReferenceDo reference) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateReferenceByCompany"), reference);
	}

	@Override
	public CreditReferenceDo queryReferenceById(Integer id) {

		return (CreditReferenceDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryReferenceById"), id);
	}

	@Override
	public Integer countReferenceByConditions(CreditReferenceDTO dto) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", dto);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countReferenceByConditions"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditReferenceDTO> queryReferenceByConditions(CreditReferenceDTO dto, PageDto<CreditReferenceDTO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryReferenceByConditions"), root);
	}

	@Override
	public Integer deleteReferenceById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteReferenceById"), id);
	}

	@Override
	public Integer updateReferenceCheckStatusById(Integer id, String checkStatus, String checkPerson) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(checkStatus, "the checkStatus must not be null");
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("checkStatus", checkStatus);
		param.put("checkPerson", checkPerson);
		
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateReferenceCheckStatusById"), param);
	}

	// public final static int DEFAULT_BATCH_SIZE = 20;
	//
	// public Integer deleteCreditCompany(Integer[] ids) {
	// Assert.notNull(ids, "The ids can not be null");
	// int impacted = 0;
	// int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1)
	// / DEFAULT_BATCH_SIZE;
	// try {
	// for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
	// getSqlMapClient().startBatch();
	// int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
	// int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
	// endIndex = endIndex > ids.length ? ids.length : endIndex;
	// for (int i = beginIndex; i < endIndex; i++) {
	// impacted += getSqlMapClientTemplate().delete(
	// "creditCompany.deleteCreditCompany", ids[i]);
	// }
	// getSqlMapClient().executeBatch();
	// }
	// } catch (Exception e) {
	// throw new PersistLayerException("batch deleteCreditCompany failed.", e);
	// }
	// return impacted;
	// }
	//
	// @SuppressWarnings("unchecked")
	// public List<CreditReferenceDo> selectCreditCompany(PageDto pageDto,
	// CreditReferenceDo creditCompany) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("PageDto", pageDto);
	// map.put("creditCompany", creditCompany);
	// return getSqlMapClientTemplate().queryForList(
	// "creditCompany.selectCreditCompany", map);
	// }
	//
	// public Integer selectCreditCompanyCount(PageDto pageDto,
	// CreditReferenceDo creditCompany) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("PageDto", pageDto);
	// map.put("creditCompany", creditCompany);
	// return Integer.valueOf(getSqlMapClientTemplate().queryForObject(
	// "creditCompany.selectCreditCompanyCount", map).toString());
	// }
	//
	// @SuppressWarnings("unchecked")
	// public List<CreditReferenceDo> selectCreditCompanyByCompanyId(
	// Integer companyId) {
	// Assert.notNull(companyId, "The companyId can not be null");
	// return getSqlMapClientTemplate().queryForList(
	// "creditCompany.selectCreditCompanyByCompanyId", companyId);
	// }
	//
	// public CreditReferenceDo selectCreditCompanyById(Integer id) {
	// Assert.notNull(id, "The id can not be null");
	// return (CreditReferenceDo) getSqlMapClientTemplate().queryForObject(
	// "creditCompany.selectCreditCompanyById", id);
	// }
	//
	// public Integer updateCreditCompany(CreditReferenceDo creditCompany) {
	// Assert.notNull(creditCompany, "The creditCompany can not be null");
	// return
	// getSqlMapClientTemplate().update("creditCompany.updateCreditCompany",
	// creditCompany);
	// }
	//
	// public Integer insertCreditCompany(CreditReferenceDo creditCompany) {
	// Assert.notNull(creditCompany, "The creditCompany can not be null");
	// return Integer.valueOf(getSqlMapClientTemplate().insert(
	// "creditCompany.insertCreditCompany", creditCompany).toString());
	// }

}
