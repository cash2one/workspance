package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhonePpcVisit;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhonePpcVisitDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhonePpcVisitDao;
@Component("phonePpcVisitDao")
public class PhonePpcVisitDaoImpl extends BaseDaoSupport implements PhonePpcVisitDao {
	final static String SQL_FIX = "phonePpcVisit";
	@Override
	public Integer countVisitByTargetId(Integer targetId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countVisitByTargetId"), targetId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhonePpcVisitDto> queryVisitByTargetId(PhonePpcVisit phonePpcVisit,PageDto<PhonePpcVisitDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phonePpcVisit", phonePpcVisit);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryVisitByTargetId"), map);
	}
	@Override
	public Integer countVisitByBoth(Integer targetId,Integer cid){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("targetId", targetId);
		map.put("cid", cid);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countVisitByBoth"), map);
	}

}
