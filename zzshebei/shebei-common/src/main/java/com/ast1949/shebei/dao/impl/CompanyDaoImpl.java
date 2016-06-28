package com.ast1949.shebei.dao.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast1949.shebei.dao.BaseDao;
import com.ast1949.shebei.dao.CompanyDao;
import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.dto.PageDto;
/**
 * 
 * @author 陈庆林
 * 2012-7-24 下午1:12:25
 */
@Component("companyDao")
public class CompanyDaoImpl extends BaseDao implements CompanyDao {
	
	final static String SQL_PREFIX="company";
	
	@Override
	public Integer insertCompany(Company company) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompany"), company);
	}

	@Override
	public Company queryCompanyById(Integer id) {
		return (Company)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompanyById"),id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompanys(PageDto<Company> page,String categoryCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("categoryCode", categoryCode);
		return (List<Company>)getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompanys"),map);
	}

	@Override
	public Integer queryCompanyCount(String categoryCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryCode", categoryCode);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX,"queryCompanyCount"),map);
	}

	@Override
	public String queryDeatilsById(Integer companyId) {
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDeatilsById"),companyId);
	}

	@Override
	public Company queryContactById(Integer companyId) {
		return (Company)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryContactById"),companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryNewestCompany(Integer size, String categoryCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryCode", categoryCode);
		map.put("size", size);
		return (List<Company>)getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestCompany"),map);
	}

	@Override
	public Date queryMaxGmtShow() {
		return (Date)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxGmtShow"));
	}

	@Override
	public String queryNameById(Integer companyId) {
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameById"),companyId);
	}

	@Override
	public Integer queryCompIdByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompIdByAccount"), account);
	}

}
