package com.zz91.ep.admin.dao.news.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.news.NewsRecommendDao;
import com.zz91.ep.domain.news.NewsRecommend;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-10-13 
 */
@Component("newsRecommendDao")
public class NewsRecommendDaoImpl extends BaseDao implements NewsRecommendDao {

	final static String SQL_PREFIX="news_recommend";
	@Override
	public Integer insertRecommend(NewsRecommend recommend) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertRecommend"), recommend);
	}

	@Override
	public Integer deleteRecommendById(Integer id,Integer rid) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("rid", rid);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRecommend"), root);
	}

//	@Override
//	public NewsRecommend queryRecommendByNewsId(Integer id) {
//		return (NewsRecommend) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryRecommendByNewsId"), id);
//	}

	@Override
	public NewsRecommend queryRecommendByNewsIdAndType(Integer id, String type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("type", type);
		return (NewsRecommend) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryRecommendByNewsIdAndType"), root);
	}

//	@Override
//	public Integer deleteRecommendById(Integer id, String type) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("id", id);
//		root.put("type", type);
//		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteRecommendById"), root);
//	}

}
