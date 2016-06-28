package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyLottery;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyLotteryDao;

@Component("companyLotteryDao")
public class CompanyLotteryDaoImpl extends BaseDaoSupport implements CompanyLotteryDao{
	
	final static String SQL_FIX = "companyLottery";

	@Override
	public Integer insert(CompanyLottery companyLottery) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"),companyLottery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyLottery> query(CompanyLottery companyLottery,
			PageDto<CompanyLottery> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyLottery", companyLottery);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "query"), map);
	}

	@Override
	public Integer queryCount(CompanyLottery companyLottery) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyLottery", companyLottery);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCount"), map);
	}

	@Override
	public Integer update(CompanyLottery companyLottery) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), companyLottery);
	}

	@Override
	public Integer updateStatus(Integer id, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateStatus"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyLottery> queryCompanyLotteryed(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryCompanyLotteryed"),size);
	}
	
	@Override
	public Integer queryCountLotteryByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountLotteryByCompanyId"),companyId);
	}

	@Override
	public CompanyLottery queryLotteryByCompanyId(Integer companyId) {
		return (CompanyLottery) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryLotteryByCompanyId"), companyId);
	}
	
}
