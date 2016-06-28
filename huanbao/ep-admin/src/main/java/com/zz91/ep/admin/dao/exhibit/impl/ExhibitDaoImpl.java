package com.zz91.ep.admin.dao.exhibit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.exhibit.ExhibitDao;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;

@Component("exhibitDao")
public class ExhibitDaoImpl extends BaseDao implements ExhibitDao {
	
	final static String SQL_PREFIX = "exhibit";
	
//	public List<Exhibit> queryExhibitBySearch(Exhibit exhibit, PageDto<ExhibitDto> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("exhibit", exhibit);
//		root.put(PageDto.SQL_KEY, page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitBySearch"), root);
//	}

//	@Override
//	public Integer queryExhibitBySearchCount(Exhibit exhibit) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("exhibit", exhibit);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitBySearchCount"), root);
//	}

	@Override
	public Exhibit queryExhibitDetailsById(Integer id) {
		
		return (Exhibit) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitDetailsById"), id);
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Exhibit> queryExhibitByPlateCategory(String plateCategoryCode, PageDto<ExhibitDto> page) {
//		
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("plateCategoryCode", plateCategoryCode);
//		root.put(PageDto.SQL_KEY, page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitByPlateCategory"), root);
//	}

//	@Override
//	public Integer queryExhibitByPlateCategoryCount(String plateCategoryCode) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("plateCategoryCode", plateCategoryCode);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitByPlateCategoryCount"), root);
//	}

	@Override
	public Integer deleteExhibit(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteExhibit"), id);
	}

	@Override
	public Integer insertExhibit(Exhibit exhibit) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertExhibit"), exhibit);
	}

	@Override
	public Integer updateExhibit(Exhibit exhibit) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateExhibit"), exhibit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitDto> queryExhibitByAdmin(String name, Integer status,
			String plateCategoryCode,String recommendType,PageDto<ExhibitDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("name", name);
		root.put("plateCategoryCode", plateCategoryCode);
		root.put("status", status);
		root.put("page", page);
		root.put("recommendType", recommendType);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitByAdmin"), root);
	}

	@Override
	public Integer updateStatus(Integer id,Integer status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("status", status);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatus"), root);
	}

	@Override
	public Integer queryExhibitCountByAdmin(String name, Integer status,
			String plateCategoryCode,String recommendType) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("name", name);
		root.put("plateCategoryCode", plateCategoryCode);
		root.put("status", status);
		root.put("recommendType", recommendType);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitCountByAdmin"), root);
	}

//	@Override
//	public Integer queryExhibitById(Integer id) {
//		
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitById"), id);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Exhibit> queryExhibitByCategory(String plateCategoryCode,Integer max) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("plateCategoryCode", plateCategoryCode);
//		root.put("max", max);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitByCategory"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Exhibit> queryExhibitByIndustryCode(String industryCode,
//			Integer size) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("industryCode", industryCode);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitByIndustryCode"), root);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibit> queryExhibitByRecommend(String type, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("type", type);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitByRecommend"), root);
	}
}
