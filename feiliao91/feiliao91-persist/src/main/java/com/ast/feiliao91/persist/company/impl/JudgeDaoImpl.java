package com.ast.feiliao91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.JudgeDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.company.JudgeDao;

@Component("judgeDao")
public class JudgeDaoImpl extends BaseDaoSupport implements JudgeDao {

	final static String SQL_FIX = "judge";

	@Override
	public Judge queryById(Integer id) {
		return (Judge) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}
	
	@Override
	public Judge queryByOrderId(Integer orderId) {
		return (Judge) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByOrderId"), orderId);
	}

	@Override
	public Integer insert(Judge judge) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), judge);
	}

	@Override
	public Integer countTradeNum(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countTradeNum"), companyId);
	}

	@Override
	public Integer countJudgeByGoodId(Integer goodId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countJudgeByGoodId"), goodId);
	}

	@Override
	public String avgGoodStar(Integer companyId) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "avgGoodStar"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> avgByCondition(Integer companyId, Integer month) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("month", month);
		return (Map<String, String>) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "avgByCondition"), map);
	}

	@Override
	public Integer countJudgeNumByCondition(Integer goodId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodId", goodId);
		map.put("type", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countJudgeNumByCondition"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Judge> queryJudgeByCondition(PageDto<JudgeDto> page,
			Integer type, Integer companyId, Integer targetId, Integer goodId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		map.put("goodId", goodId);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryJudgeByCondition"), map);
	}

	@Override
	public Integer countJudgeNumByCondition(Integer type, Integer companyId,
			Integer targetId, Integer goodId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		map.put("goodId", goodId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countJudgeNumByCondition"), map);
	}

	@Override
	public Integer getcount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "getcount"), companyId);
	}

	@Override
	public Integer getEvaluation(Integer month, Integer level, Integer companyId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("month", month);
		map.put("level", level);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "getEvaluation"), map);
	}

	@Override
	public Integer getEvaluationTwo(Integer month, Integer level,
			Integer companyId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("month", month);
		map.put("level", level);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "getEvaluationTwo"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Judge> pageJudgeByAll(PageDto<JudgeDto> page, Integer type,
			Integer companyId, Integer type2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("type2", type2);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "pageJudgeByAll"), map);
	}

	@Override
	public Integer pageJudgeByAllcount(Integer type, Integer companyId,
			Integer type2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("type2", type2);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "pageJudgeByAllcount"), map);
	}

	@Override
	public Integer countSellNum(Integer compangyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countSellNum"), compangyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Judge> pageJudgeBySellAll(PageDto<JudgeDto> page, Integer type,
			Integer companyId, Integer type2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("type2", type2);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "pageJudgeBySellAll"), map);
	}

	@Override
	public Integer pageJudgeBySellAllCount(Integer type, Integer companyId,
			Integer type2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("companyId", companyId);
		map.put("type2", type2);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "pageJudgeBySellAllCount"), map);
	}

}
