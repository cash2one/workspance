/**
 * 
 */
package com.ast.ast1949.persist.dataindex.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.dataindex.DataIndexCategoryDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.dataindex.DataIndexCategoryDao;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyh
 * 
 */
@Component("dataIndexCategoryDao")
public class DataIndexCategoryDaoImpl extends BaseDaoSupport implements
		DataIndexCategoryDao {

	final static String SQL_PREFIX = "dataIndexCategory";

	public Integer insertDataIndexCategory(
			DataIndexCategoryDO dataIndexCategoryDO) {
		Assert.notNull(dataIndexCategoryDO, "dataIndexCategoryDO is not null");
		if (dataIndexCategoryDO.getIsDelete() == null) {
			dataIndexCategoryDO.setIsDelete("0");
		}
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertDataIndexCategory"),
				dataIndexCategoryDO);
	}

//	public DataIndexCategoryDO queryDataIndexCategoryById(Integer id) {
//		Assert.notNull(id, "id is not null");
//		return (DataIndexCategoryDO) getSqlMapClientTemplate().queryForObject(
//				addSqlKeyPreFix(SQL_PREFIX, "queryDataIndexCategoryById"), id);
//	}

	@SuppressWarnings("unchecked")
	public List<DataIndexCategoryDO> queryDataIndexCategoryByPreCode(
			String preCode) {
		Assert.notNull(preCode, "preCode is not null");
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryDataIndexCategoryByPreCode"),
				preCode);
	}

	public String selectMaxCodeByPreCode(String preCode) {
		Assert.notNull(preCode, "preCode is not null ");
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "selectMaxCodeByPreCode"), preCode);
	}

	public Integer updateDataIndexCategory(
			DataIndexCategoryDO dataIndexCategoryDO) {
		Assert.notNull(dataIndexCategoryDO, "dataIndexCategoryDO is not null");
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateDataIndexCategory"),
				dataIndexCategoryDO);
	}

//	@Override
//	public Integer updateIsDelete(String isDelete, Integer id) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("isDelete", isDelete);
//		root.put("id", id);
//		return getSqlMapClientTemplate().update(
//				addSqlKeyPreFix(SQL_PREFIX, "updateIsDelete"), root);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryDataIndexCode() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryDataIndexCode"));
	}

	@Override
	public DataIndexCategoryDO queryDataIndexCategoryByCode(String code) {
		Assert.notNull(code, "code is not null");
		return (DataIndexCategoryDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryDataIndexCategoryByCode"), code);
	}

	@Override
	public Integer deleteCategoryByCode(String code) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteCategoryByCode"), code);
	}

}
