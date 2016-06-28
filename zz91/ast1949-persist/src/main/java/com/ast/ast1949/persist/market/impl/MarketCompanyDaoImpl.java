/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.persist.market.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.market.MarketCompany;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.market.MarketCompanyDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.market.MarketCompanyDao;

@Component("marketCompanyDao")
public class MarketCompanyDaoImpl extends BaseDaoSupport implements
		MarketCompanyDao {
	final static String SQL_PREFIX = "marketCompany";

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketCompany> queryNewComapny(Integer marketId, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marketId", marketId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNewCompany"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketCompany> queryNewCompany2(Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNewCompany2"), map);
	}

	@Override
	public Integer insertMarketCompany(Integer marketId, Integer companyId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		if (marketId!=null) {
			map.put("marketId", marketId);
		}
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertMarketCompany"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryMarketByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryMarketByCompanyId"), companyId);
	}
	
	@Override
	public Integer queryFirstMarketByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryFirstMarketByCompanyId"), companyId);
	}
	

	@Override
	public MarketCompany queryMarketCompanyById(Integer id) {
		return (MarketCompany) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMarketCompanyById"), id);
	}
    @SuppressWarnings("unchecked")
	@Override
	public List<MarketCompany> pageCompanyByAdminMarketId(PageDto<CompanyDto> page, Integer marketId,String membershipCode,Integer isPerson) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("marketId", marketId);
		map.put("membershipCode", membershipCode);
		map.put("isPerson", isPerson);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "pageCompanyByAdminMarketId"), map);
	}

	@Override
	public Integer pageCompanyByAdminMarketIdCount(Integer marketId,String membershipCode,Integer isPerson) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("marketId", marketId);
		map.put("membershipCode", membershipCode);
		map.put("isPerson", isPerson);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "pageCompanyByAdminMarketIdCount"), map);
	}

	@Override
	public MarketCompany queryMarketCompanyByBothId(Integer marketId, Integer companyId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("marketId", marketId);
		map.put("companyId", companyId);
		return (MarketCompany) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMarketCompanyByBothId"), map);
	}

	@Override
	public Integer updateIsQuitByBothId(Integer marketId, Integer companyId, Integer isQuit) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("marketId", marketId);
		map.put("companyId", companyId);
		map.put("isQuit", isQuit);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsQuitByBothId"), map);
	}

	@Override
	public Integer countMarketCompany() {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countMarketCompany"));
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketCompany> queryListMarketByadmin(PageDto<MarketCompanyDto> page, CompanyAccount companyAccount,String companyName) {
		
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("companyAccount", companyAccount);
		map.put("companyName", companyName);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListMarketByadmin"), map);
	}

	@Override
	public Integer queryListMarketByadminCount(CompanyAccount companyAccount, String companyName) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("companyAccount", companyAccount);
		map.put("companyName", companyName);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryListMarketByadminCount"), map);
	}

}
