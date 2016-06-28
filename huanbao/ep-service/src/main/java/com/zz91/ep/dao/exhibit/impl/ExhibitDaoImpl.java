/*
 * 文件名称：ExhibitDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.exhibit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.exhibit.ExhibitDao;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：展会信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("exhibitDao")
public class ExhibitDaoImpl extends BaseDao implements ExhibitDao {

	final static String SQL_PREFIX="exhibit";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibit> queryExhibitsByRecommend(String categoryCode, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", categoryCode);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitsByRecommend"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibit> queryExhibitsByCategory(String categoryCode, String industryCode,Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", categoryCode);
		root.put("industryCode", industryCode);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitsByCategory"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibit> queryByCategory(String categoryCode, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", categoryCode);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryByCategory"), root);
	}

	@Override
	public Exhibit queryExhibitDetailsById(Integer id) {
		return (Exhibit) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitDetailsById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibit> queryByPlateCategory(String categoryCode,
			PageDto<ExhibitDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("plateCategoryCode", categoryCode);
		root.put(PageDto.SQL_KEY, page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitByPlateCategory"), root);
	}

	@Override
	public Integer queryByPlateCategoryCount(String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("plateCategoryCode", categoryCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitByPlateCategoryCount"), root);
	}

}
