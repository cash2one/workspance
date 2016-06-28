/**
 * @author shiqp 日期:2014-11-11
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsPostTagsDao;

@Component("bbsPostTagsDao")
public class BbsPostTagsDaoImpl extends BaseDaoSupport implements BbsPostTagsDao {
	
	final static String SQL_PREFIX="bbsPostTags";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostTags> queryTagsByArticleC(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryTagsByArticleC"), size);
	}
	@Override
	public BbsPostTags queryTagByName(String tagName,Integer category,Integer isDel) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("category", category);
		map.put("tagName", tagName);
		return (BbsPostTags) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryTagByName"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostTags> queryTagByCategory(Integer category, Integer size) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("category", category);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryTagByCategory"), map);
	}
	@Override
	public Integer insertTag(BbsPostTags bbsPostTags) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertTag"), bbsPostTags);
	}
	@Override
	public Integer updateTag(BbsPostTags bbsPostTags) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateTag"), bbsPostTags);
	}
	@Override
	public BbsPostTags queryTagById(Integer id) {
		return (BbsPostTags) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryTagById"), id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostTags> queryTagsByMark(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryTagsByMark"), size);
	}
	
	@Override
	public Integer updateNoticeCountById(Integer id,Integer noticeCount){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("noticeCount", noticeCount);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateNoticeCountById"), map);
	}
	@Override
	public Integer updateIsDelById(Integer id, Integer isDel) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("isDel", isDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsdelById"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostTags> queryAllTagsByCategory(Integer category,PageDto<BbsPostTags> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("category", category);
		map.put("page", page);
		map.put("isDel", null);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllTagsByCategory"), map);
	}
	@Override
	public Integer countAllTagsByCategory(Integer category) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("category", category);
		map.put("isDel", null);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countAllTagsByCategory"), map);
	}
	@Override
	public Integer updateNameAndCategory(BbsPostTags tags) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("tagName", tags.getTagName());
		map.put("category", tags.getCategory());
		map.put("id", tags.getId());
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateNameAndCategory"), map);
	}

}
