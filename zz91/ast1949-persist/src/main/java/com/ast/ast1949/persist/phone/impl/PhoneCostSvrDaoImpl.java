package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneCostSvrDao;

@Component("phoneCostSvrDao")
public class PhoneCostSvrDaoImpl extends BaseDaoSupport implements PhoneCostSvrDao{

	final static String SQL_FIX = "phoneCostSvr";
	
	@Override
	public Integer insert(PhoneCostSvr phoneCoseSvr) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), phoneCoseSvr);
	}
	
	@Override
	public PhoneCostSvr queryById(Integer id){
		return (PhoneCostSvr) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneCostSvr> queryListByAdmin(PhoneCostSvr phoneCostSvr,
			PageDto<PhoneCostSvr> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneCostSvr", phoneCostSvr);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryListByAdmin"), map );
	}

	@Override
	public Integer queryListCountByAdmin(PhoneCostSvr phoneCostSvr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneCostSvr", phoneCostSvr);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryListCountByAdmin"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneCostSvr> queryListByCost(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryListByCost"), companyId);
	}
	
	@Override
	public List<PhoneCostSvr> queryGmtZeroByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryGmtZeroByCompanyId"), companyId);
	}
	
	@Override
	public Integer updateSvr(PhoneCostSvr phoneCostSvr) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateSvr"), phoneCostSvr);
	}
	
	@Override
	public String countFeeByCompanyId(Integer companyId){
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countFeeByCompanyId"), companyId);
	}

	@Override
	public String sumLaveByCompanyId(Integer companyId) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "sumLaveByCompanyId"), companyId);
	}

	@Override
	public PhoneCostSvr queryPhoneService(Integer companyId) {
		return (PhoneCostSvr) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryPhoneService"), companyId);
	}
	
	@Override
	public Integer updateLaveFull(Integer companyId){
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateLaveFull"), companyId);
	}

}
