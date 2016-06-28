package com.ast.ast1949.persist.credit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.credit.CreditFileDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.credit.CreditFileDTO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.credit.CreditFileDao;
import com.ast.ast1949.util.Assert;

@Component("creditFileDao")
public class CreditFileDaoImpl extends BaseDaoSupport implements CreditFileDao {

	final static String SQL_PREFIX = "creditFile";

	@Override
	public Integer deleteFileById(Integer id) {

		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteFileById"), id);
	}

	@Override
	public Integer insertFileByCompany(CreditFileDo creditFile) {

		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertFileByCompany"), creditFile);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditFileDo> queryFileByCompany(Integer companyId) {

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryFileByCompany"), companyId);
	}

	@Override
	public Integer updateCreditFileCheckStatusById(Integer id, String checkStatus,
			String checkPerson) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(checkStatus, "the checkStatus must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("checkStatus", checkStatus);
		param.put("checkPerson", checkPerson);

		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateCreditFileCheckStatusById"), param);
	}

	@Override
	public Integer updateFileById(CreditFileDo creditFile) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateFileById"),
				creditFile);
	}

	@Override
	public CreditFileDo queryFileById(Integer id) {
		return (CreditFileDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryFileById"), id);
	}

	@Override
	public Integer countCreditFileByConditions(CreditFileDTO dto) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countCreditFileByConditions"), dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditFileDTO> queryCreditFileByConditions(CreditFileDTO dto, PageDto<CreditFileDTO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCreditFileByConditions"), root);
	}
	
	@Override
	public Integer updateFileByAdmin(CreditFileDo creditFile) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateFileByAdmin"),
				creditFile);
	}
	// public final static int DEFAULT_BATCH_SIZE = 20;
	//	
	// public Integer insertCreditFile(CreditFileDo creditFile) {
	// Assert.notNull(creditFile, "The creditFile can not be null");
	// return Integer.valueOf(getSqlMapClientTemplate().insert(
	// "creditFile.insertCreditFile", creditFile).toString());
	// }
	//
	// @SuppressWarnings("unchecked")
	// public List<CreditFileDo> selectCreditFile(PageDto pageDto,
	// CreditFileDo creditFile) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("pageDto", pageDto);
	// map.put("creditFile", creditFile);
	// return
	// getSqlMapClientTemplate().queryForList("creditFile.selectCreditFile",
	// map);
	// }
	// @SuppressWarnings("unchecked")
	// public List<CreditFileDo> selectCreditFile(CreditFileDo creditFile){
	// Map<String,Object> map = new HashMap<String, Object>();
	// map.put("creditFile", creditFile);
	// return
	// getSqlMapClientTemplate().queryForList("creditFile.selectCreditFile",map);
	// }
	//
	// public Integer selectCreditFileCount(PageDto pageDto,
	// CreditFileDo creditFile) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("creditFile", creditFile);
	// return Integer.valueOf(getSqlMapClientTemplate().queryForObject(
	// "creditFile.selectCreditFileCount", map).toString());
	// }
	//
	// public Integer updateCreditFileCheckStatus(Integer[] ids, String
	// checkStatus) {
	// Assert.notNull(ids, "The ids can not be null");
	// Assert.notNull(checkStatus, "The checkStatus can not be null");
	// int impacted = 0;
	// Map<String, Object> map = new HashMap<String, Object>();
	// int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1)
	// / DEFAULT_BATCH_SIZE;
	// try {
	// for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
	// getSqlMapClient().startBatch();
	// int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
	// int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
	// endIndex = endIndex > ids.length ? ids.length : endIndex;
	// for (int i = beginIndex; i < endIndex; i++) {
	// map.put("id", ids[i]);
	// map.put("checkStatus", checkStatus);
	// impacted += getSqlMapClientTemplate().update(
	// "creditFile.updateCreditFileCheckStatus", map);
	// }
	// getSqlMapClient().executeBatch();
	// }
	// } catch (Exception e) {
	// throw new
	// PersistLayerException("batch updateCreditFileCheckStatus failed.", e);
	// }
	// return impacted;
	// }
	//
	// public CreditFileDo selectCreditFileById(Integer id) {
	// Assert.notNull(id, "The id can not be null");
	// return (CreditFileDo) getSqlMapClientTemplate().queryForObject(
	// "creditFile.selectCreditFileById", id);
	// }
	//
	// public CreditFileDo selectCreditFileByCompanyId(Integer company_id){
	// Assert.notNull(company_id, "The id can not be null");
	// return (CreditFileDo) getSqlMapClientTemplate().queryForObject(
	// "creditFile.selectCreditFileByCompanyId", company_id);
	// }
	//	
	// public Integer deleteCreditFileById(Integer id){
	// Assert.notNull(id, "The id can not be null");
	// return
	// Integer.valueOf(getSqlMapClientTemplate().delete("creditFile.deleteCreditFile",id));
	// }
	//
	// public Integer updateCreditFileDO(CreditFileDo creditFileDO) {
	// return
	// getSqlMapClientTemplate().update("creditFile.updateCreditFileDO",creditFileDO);
	// }

	@Override
	public String selectpicNameByCompanyId(CreditFileDo creditFile) {	
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectpicNameByCompanyId"), creditFile);
	}

}
