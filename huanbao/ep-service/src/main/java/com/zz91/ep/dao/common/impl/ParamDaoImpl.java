/*
 * 文件名称：ParamDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午6:02:28
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.ParamDao;
import com.zz91.util.domain.Param;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：系统参数信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("paramDao")
public class ParamDaoImpl extends BaseDao implements ParamDao {

	final static String SQL_PREFIX="param";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Param> queryUsefulParam() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryUsefulParam"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Param> queryParamsByType(String code) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryParamsByType"),code);
	}
	
	@Override
	public Param queryParamByKey(String key) {
		return (Param)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryParamByKey"),key);
	}

	@Override
	public String queryNameByTypeAndValue(String category, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("val", value);
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByTypeAndValue"),map);
	}

}