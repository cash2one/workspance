package com.zz91.crm.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.CrmSaleCompDao;
import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.CrmStatistics;
import com.zz91.crm.domain.CrmTurnStarStatistics;
import com.zz91.crm.dto.CrmContactStatisticsDto;
import com.zz91.crm.dto.CrmSaleDataDto;
import com.zz91.crm.dto.CrmSaleStatisticsDto;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-13 
 */
@Component("crmSaleCompDao")
public class CrmSaleCompDaoImpl extends BaseDao implements CrmSaleCompDao {

	final static String SQL_PRIFIX="crmSaleComp";
	
	@Override
	public Integer createCrmSaleComp(CrmSaleComp crmSaleComp) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PRIFIX, "createCrmSaleComp"), crmSaleComp);
	}

	@Override
	public Integer updateStatus(Integer cid, Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("type", type);
		return getSqlMapClientTemplate().update(buildId(SQL_PRIFIX, "updateStatus"), root);
	}

	@Override
	public Integer updateCompanyType(Integer id, Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("type", type);
		return getSqlMapClientTemplate().update(buildId(SQL_PRIFIX, "updateCompanyType"), root);
	}

	@Override
	public Integer updateDisableStatus(Integer cid, Short status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("status", status);
		return getSqlMapClientTemplate().update(buildId(SQL_PRIFIX, "updateDisableStatus"), root);
	}

	@Override
	public Integer updateContactCount(Integer cid, Short situation,Integer logId,Date gmtNextContact,String saleAccount,String saleDept) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("situation", situation);
		root.put("logId", logId);
		root.put("gmtNextContact", gmtNextContact);
		root.put("saleAccount", saleAccount);
		root.put("saleDept", saleDept);
		return getSqlMapClientTemplate().update(buildId(SQL_PRIFIX, "updateContactCount"), root);
	}

	@Override
	public Integer queryCountByCidAndStatus(Integer cid, Short status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("status", status);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryCountByCidAndStatus"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmSaleComp> queryCrmSaleCompByCid(Integer cid) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryCrmSaleCompByCid"), cid);
	}

	@Override
	public Integer updateOrderCountById(Integer id, Short flag) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("flag", flag);
		return getSqlMapClientTemplate().update(buildId(SQL_PRIFIX, "updateOrderCountById"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmContactStatisticsDto> queryContactData(String account,
			String deptCode, String start, String end, Short group) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("deptCode", deptCode);
		root.put("group", group);
		root.put("start", start);
		root.put("end", end);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryContactData"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmSaleStatisticsDto> queryRegisterData(String start, String end,
			Short group) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("start", start);
		root.put("end", end);
		root.put("group", group);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryRegisterData"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmSaleDataDto> querySaleCompanyData(String account,
			String deptCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("deptCode", deptCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "querySaleCompanyData"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmSaleDataDto> querySales(String account, String deptCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("deptCode", deptCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "querySales"), root);
	}

	@Override
	public Integer queryTodContact(String saleAccount, String saleDept) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", saleAccount);
		root.put("deptCode", saleDept);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryTodContact"), root);
	}

	@Override
	public Integer queryTomContact(String saleAccount, String saleDept) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", saleAccount);
		root.put("deptCode", saleDept);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryTomContact"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmLog> queryContactDataByToday(String account, String deptCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("deptCode", deptCode);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryContactDataByToday"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmStatistics> queryCrmStatistics(PageDto<CrmStatistics> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryCrmStatistics"), root);
	}

	@Override
	public Integer queryCrmStatisticsCount() {
		Map<String, Object> root = new HashMap<String, Object>();
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryCrmStatisticsCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Date> queryGmtTarget() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryGmtTarget"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmSaleDataDto> queryCrmCount() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryCrmCount"));
	}

	@Override
	public Integer queryTodaySeaCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryTodaySeaCount"));
	}

	@Override
	public Integer queryTodayChooseSeaCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryTodayChooseSeaCount"));
	}

	@Override
	public Integer queryTodayAssignCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryTodayAssignCount"));
	}

	@Override
	public Integer queryTodayRepeatCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryTodayRepeatCount"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmTurnStarStatistics> queryFourOrFiveStar(String start,
			String end, PageDto<CrmTurnStarStatistics> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("start", start);
		root.put("end", end);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryFourOrFiveStar"), root);
	}

	@Override
	public Integer queryFourOrFiveStarCount(String start, String end) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("start", start);
		root.put("end", end);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryFourOrFiveStarCount"), root);
	}

	@Override
	public CrmSaleDataDto querySaleNameAndSaleDeptByAccount(String account) {
		return (CrmSaleDataDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "querySaleNameAndSaleDeptAccount"), account);
	}

	@Override
	public Integer updateSaleDeptByAccountAndDept(String account,
			String oldDept, String newDept) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("account", account);
		root.put("oldDept", oldDept);
		root.put("newDept", newDept);
		return getSqlMapClientTemplate().update(buildId(SQL_PRIFIX, "updateSaleDeptByAccountAndDept"), root);
	}

}