package com.ast.ast1949.persist.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsTech;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.news.NewsTechDTO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.news.NewsTechDao;

@Component("newsTechDao")
public class NewsTechDaoImpl extends BaseDaoSupport implements NewsTechDao {

	final static String SQL_FIX= "newsTech";
	@Override
	public Integer insert(NewsTech newsTech) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), newsTech);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsTech> queryByCondition(NewsTechDTO newsTechDTO,
			PageDto<NewsTech> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newsTech", newsTechDTO);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public NewsTech queryById(Integer id) {
		return (NewsTech) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Integer queryCountCondition(NewsTechDTO newsTechDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newsTech", newsTechDTO);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountCondition"), map);
	}

	@Override
	public Integer update(NewsTech newsTech) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), newsTech);
	}

	@Override
	public NewsTech queryDownNewsTechById(Integer id) {
		return (NewsTech)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryDownNewsTechById"),id);
	}

	@Override
	public NewsTech queryOnNewsTechById(Integer id) {
		return (NewsTech)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryOnNewsTechById"),id);
	}

	@Override
	public Integer updateTechViewCount(Integer id,Integer viewCount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("viewCount", viewCount);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateTechViewCount"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsTech> queryTechForIndex(NewsTechDTO newsTechDTO,
			PageDto<NewsTech> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newsTech", newsTechDTO);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryTechForIndex"), map);
	}

	@Override
	public Integer delete(Integer id) {
		return (Integer)getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "delete"),id);
	}

	@Override
	public Integer updateContent(Integer id, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("content", content);
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateContent"),map);
	}

}
