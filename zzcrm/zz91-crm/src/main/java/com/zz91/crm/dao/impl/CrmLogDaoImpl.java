package com.zz91.crm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.CrmLogDao;
import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.dto.CrmLogDto;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-13 
 */
@Component("crmLogDao")
public class CrmLogDaoImpl extends BaseDao implements CrmLogDao {
	
	final static String SQL_PRIFIX="crmLog";

	@Override
	public Integer createCrmLog(CrmLog crmLog) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PRIFIX, "createCrmLog"), crmLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmLog> queryCrmLogByCid(Integer cid,Short callType) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("callType", callType);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryCrmLogByCid"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmLog> queryAccountByDeptCode(String deptCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryAccountByDeptCode"), deptCode);
	}
	
	@Override
	public Integer querytomContactCount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "querytomContactCount"),account);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmLogDto> queryCrmLogByToday(String account, String tdate,PageDto<CrmLogDto> page,Short disable,Short star,Short type) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("account", account);
		root.put("tdate", tdate);
		root.put("page", page);
		root.put("disable", disable);
		root.put("star", star);
		root.put("type", type);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryCrmLogByToday"), root);
	}

	@Override
	public Integer queryCrmLogCountByToday(String account, String tdate,Short disable,Short star,Short type) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("account", account);
		root.put("tdate", tdate);
		root.put("disable", disable);
		root.put("star", star);
		root.put("type", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryCrmLogCountByToday"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmLog> queryTurnStarCompByStar(Short star, String tDate,
			String account) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("star", star);
		root.put("tDate", tDate);
		root.put("account", account);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryTurnStarCompByStar"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmLog> queryTurnFourOrFiveAccountByToday() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryTurnFourOrFiveAccountByToday"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmLog> queryFourOrFiveBySaleAccountToday(String saleAccount) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryFourOrFiveBySaleAccountToday"), saleAccount);
	}
}
