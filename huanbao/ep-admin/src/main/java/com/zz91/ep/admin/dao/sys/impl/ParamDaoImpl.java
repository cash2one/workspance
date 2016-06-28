package com.zz91.ep.admin.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.sys.ParamDao;
import com.zz91.util.Assert;
import com.zz91.util.domain.Param;
import com.zz91.util.domain.ParamType;

@Repository("ParamDao")
public class ParamDaoImpl extends BaseDao implements ParamDao {

	final static String SQL_PREFIX="param";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Param> queryAllParam() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryAllParam"));
	}
	
//	@Override
//	public String queryValueByKey(String recommendType) {
//		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryValueByKey"),recommendType);
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Param> queryParamByType(String type) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryParamByType"),type);
	}
	public Integer deleteParamById(Integer id) {
		Assert.notNull(id, "id can not be null");
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteParamById"), id);
	}

	public Integer deleteParamByTypes(String types) {
		Assert.notNull(types, "types can not be null");
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteParamByTypes"), types);
	}

	public Integer insertParam(Param param) {
		Assert.notNull(param, "param can not be null");
		Assert.notNull(param.getKey(), "attribute key can not be null");
		Assert.notNull(param.getTypes(), "attribute types can not be null");
		if(param.getIsuse()==null){
			//param.setIsuse(AstConst.ISUSE_TRUE);
		}
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertParam"), param);
	}

	@SuppressWarnings("unchecked")
	public List<ParamType> listAllParamType() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "listAllParamType"));
	}

	@SuppressWarnings("unchecked")
	public List<Param> listParam(Param param) {
		Assert.notNull(param, "param can not be null");
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "listParam"),param);
	}

	public Integer updateParam(Param param) {
		Assert.notNull(param, "param can not be null");
		Assert.notNull(param.getId(), "attribute id can not be null");
		Assert.notNull(param.getKey(), "attribute key can not be null");
		if(param.getIsuse()==null){
			//param.setIsuse(AstConst.ISUSE_FALSE);
		}
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateParam"), param);
	}

	public Param listOneParamById(Integer id) {
		Assert.notNull(id, "id can not be null");
		return (Param) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "listOneParamById"), id);
	}
	
//	@Override
//	public String queryValueByTypeAndKey(String type, String key) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("type", type);
//		root.put("key", key);
//		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryValueByTypeAndKey"), root);
//	}
	
	@Override
	public String queryNameByTypeAndValue(String type, String value) {
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("type", type);
		root.put("value", value);
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByTypeAndValue"), root);
	}
}
