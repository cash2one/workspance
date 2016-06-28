/**
 * 
 */
package com.ast.ast1949.persist.dataindex.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.dataindex.DataIndexDao;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyh
 * 
 */
@Component("dataIndexDao")
public class DataIndexDaoImpl extends BaseDaoSupport implements DataIndexDao {

	final static String SQL_PREFIX = "dataIndex";

	@Override
	public Integer deleteDataIndexById(Integer id) {
		Assert.notNull(id, "id is not null");
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteDataIndexById"), id);
	}

	@Override
	public Integer insertDataIndex(DataIndexDO dataIndexDO) {
		Assert.notNull(dataIndexDO, "dataIndexdataIndexDO is not null");
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertDataIndex"), dataIndexDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataIndexDO> queryDataIndex(DataIndexDO index, PageDto page) {
		Assert.notNull(index, "the object dataIndex can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("index", index);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryDataIndex"), root);
	}

	@Override
	public DataIndexDO queryDataIndexById(Integer id) {

		return (DataIndexDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryDataIndexById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataIndexDO> queryDataIndexByParentCode(String parentCode,
			String path, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("parentCode", parentCode);
		root.put("path", path);
		root.put("size", size);
		return getSqlMapClientTemplate()
				.queryForList(
						addSqlKeyPreFix(SQL_PREFIX,
								"queryDataIndexByParentCode"), root);
	}

	@Override
	public Integer queryDataIndexCount(DataIndexDO index) {
		Assert.notNull(index, "the object dataIndex can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("index", index);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryDataIndexCount"), root);
	}

	@Override
	public Integer updateDataIndex(DataIndexDO dataIndexDO) {
		Assert.notNull(dataIndexDO, "dataIndexDO is not null");
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateDataIndex"), dataIndexDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataIndexDO> queryDataIndexByCode(String code,Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", code);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryDataIndexByCode"),root);
	}

}
