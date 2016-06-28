package com.ast.feiliao91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyService;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.company.CompanyServiceDao;
@Component("companyServiceDao")
public class CompanyServiceDaoImpl extends BaseDaoSupport implements CompanyServiceDao {
	
	final static String SQL_PREFIX="companyService";
	/**
	 * 创建公司服务
	 * @param companyService
	 * @return
	 */
	@Override
	public Integer insertCompanyService(CompanyService companyService) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertCompanyService"), companyService);
	}
	/**
     * 根据ID查询服务
     * @param id
	 * @return CompanyService
     */
	@Override
	public CompanyService queryCompanyServiceById(Integer id) {
		return (CompanyService) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyServiceById"), id);
	}
	/**
     * 根据company_id查询服务(返回列表，一个公司可有多个服务)
     * @param company_id
	 * @return List<CompanyService>
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyService> queryCompanyServiceListByCompanyId(Integer companyId,Integer pageSize){
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("companyId", companyId);
		map.put("pageSize", pageSize);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyServiceListByCompanyId"),map);
	}
	/**
     * 根据companyId,service_code返回count数,判断改公司是否拥有该服务
     * @param companyId,serviceCode
	 * @return 
     */
	public Integer queryServiceCount(Integer companyId,String serviceCode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("serviceCode", serviceCode);
		return	(Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryServiceCount"),map);
	}
	@Override
	public Integer updateToOpen(Integer companyId, String serviceCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("serviceCode", serviceCode);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateToOpen"), map);
	}
	@Override
	public Integer updateToClose(Integer companyId, String serviceCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("serviceCode", serviceCode);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateToClose"), map);
	}
}
