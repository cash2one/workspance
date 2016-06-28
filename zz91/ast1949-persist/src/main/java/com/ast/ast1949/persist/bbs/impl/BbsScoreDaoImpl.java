package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsScoreDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsScoreDao;

@Component("bbsScoreDao")
public class BbsScoreDaoImpl extends BaseDaoSupport implements BbsScoreDao{

	final static String SQL_FIX = "bbsScore";
	
	@Override
	public Integer insert(BbsScore bbsScore) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), bbsScore);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsScore> query(BbsScoreDto bbsScore, PageDto<BbsScoreDto> page) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("bbsScore", bbsScore);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "query"), map);
	}

	@Override
	public Integer queryCount(BbsScoreDto bbsScore) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("bbsScore", bbsScore);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCount"), map);
	}

	@Override
	public Integer sumScore(BbsScore bbsScore) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("bbsScore", bbsScore);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "sumScore"), map);
	}
	@Override
	public BbsScore querybyId(BbsScore bbsScore){
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("bbsScore",bbsScore);
		return (BbsScore) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "querybyId"), map);
	}

}
