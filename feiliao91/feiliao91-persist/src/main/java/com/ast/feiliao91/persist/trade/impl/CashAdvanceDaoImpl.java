package com.ast.feiliao91.persist.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.domain.trade.CashAdvanceSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.CashAdvanceDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.trade.CashAdvanceDao;

@Component("cashAdvanceDao")
public class CashAdvanceDaoImpl extends BaseDaoSupport implements CashAdvanceDao {

	final static String SQL_PREFIX = "cashAdvance";
	
	@Override
	public Integer insertCashAdvance(CashAdvance cashAdvance) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertCashAdvance"), cashAdvance);
	}
	
	public CashAdvance queryCashAdvanceById(Integer id){
		return (CashAdvance) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCashAdvanceById"),id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CashAdvance> queryByCondition(CashAdvance cashAdvance){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("cashAdvance", cashAdvance);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryByCondition"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CashAdvance> queryCashAdvanceList(PageDto<CashAdvanceDto> page,
			CashAdvanceSearch search){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("search", search);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCashAdvanceByCondition"), map);
	}
	
	@Override
	public Integer countCashAdvanceList(CashAdvanceSearch search){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countCashAdvanceByCondition"), map);
	}
	
	@Override
	public Integer updateStatus(Integer valueOf, Integer checkStatus,String checkPerson){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", valueOf);
		map.put("checkStatus", checkStatus);
		map.put("checkPerson", checkPerson);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatus"),map);
	}
}
