/**
 * @author shiqp 日期：2014-11-10
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsPostNoticeRecommendDao;
@Component("bbsPostNoticeRecommendDao")
public class BbsPostNoticeRecommendDaoImpl extends BaseDaoSupport implements BbsPostNoticeRecommendDao {
	final static String SQL_PREFIX="bbsPostNoticeRecommend";
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostNoticeRecommend> queryZuiXinRecommendByCondition(BbsPostNoticeRecommend bbs, Integer size) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("type", bbs.getType());
        map.put("state", bbs.getState());
        map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryZuiXinRecommendByCondition"), map);
	}
	@Override
	public Integer countNumbyCompanyId(BbsPostNoticeRecommend bbs) {
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put("companyId", bbs.getCompanyId());
		 map.put("type", bbs.getType());
		 map.put("state", bbs.getState());
		 map.put("category", bbs.getCategory());
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countNumbyCompanyId"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostNoticeRecommend> queryNoticeByUser(String account, Integer size, Integer category) {
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put("account", account);
		 map.put("size", size);
		 map.put("category", category);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNoticeByUser"), map);
	}
	@Override
	public Integer insertNoticeOrRecomend(BbsPostNoticeRecommend bbs) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertNoticeOrRecomend"), bbs);
	}
	@Override
	public Integer querySimpleNoOrRem(BbsPostNoticeRecommend bbs) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", bbs.getType());
		map.put("account", bbs.getAccount());
		map.put("contentId", bbs.getContentId());
		map.put("category", bbs.getCategory());
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "querySimpleNoOrRem"), map);
	}
	@Override
	public Integer countNumByContentId(BbsPostNoticeRecommend bbs) {
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put("companyId", bbs.getCompanyId());
		 map.put("type", bbs.getType());
		 map.put("state", bbs.getState());
		 map.put("category", bbs.getCategory());
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countNumByContentId"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostNoticeRecommend> queryListNotice(String account,Integer category, PageDto<PostDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("category", category);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListNotice"), map);
	}
	@Override
	public Integer queryListNoticeCount(String account, Integer category) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("category", category);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryListNoticeCount"), map);
	}
	
	@Override
	public Integer updateStateToDel(String account ,Integer contentId,Integer category){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("contentId", contentId);
		map.put("category", category);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStateToDel"), map);
	}

}
