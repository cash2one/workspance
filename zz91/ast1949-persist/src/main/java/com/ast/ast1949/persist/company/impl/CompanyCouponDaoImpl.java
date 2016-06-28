/**
 * @author kongsj
 * @date 2014年11月4日
 * 
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyCoupon;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCouponDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyCouponDao;

@Component("companyCouponDao")
public class CompanyCouponDaoImpl extends BaseDaoSupport implements
		CompanyCouponDao {

	final static String SQL_FIX = "companyCoupon";

	@Override
	public Integer insert(CompanyCoupon companyCoupon) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), companyCoupon);
	}

	@Override
	public Integer updateStatus(CompanyCoupon companyCoupon) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateStatus"), companyCoupon);
	}

	@Override
	public Integer updateCouponInfo(CompanyCoupon companyCoupon) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateCouponInfo"), companyCoupon);
	}

	@Override
	public CompanyCoupon selectByCode(Integer companyId, String code,
			Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("code", code);
		map.put("type", type);
		return (CompanyCoupon) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "selectByCode"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyCoupon> selectByCompanyId(Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "selectByCompanyId"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyCoupon> selectByCompanyId(Integer companyId,Integer status,String from ,String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		map.put("status", status);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "selectByCompanyId"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyCouponDto> queryCompanyCoupon(String email,PageDto<CompanyCouponDto> page) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("email", email);
		return  (List<CompanyCouponDto>) getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryCompanyCoupon"), map);
	}

	@Override
	public Integer queryCompanyCouponCount(String email) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("email", email);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCompanyCouponCount"),map);
	}

	@Override
	public CompanyCoupon selectById(Integer id) {
		return (CompanyCoupon) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "selectById"), id);
	}

	@Override
	public CompanyCoupon selectByServiceName(Integer companyId,String serviceName) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("serviceName", serviceName);
		return (CompanyCoupon) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "selectByServiceName"), map);
	}

	@Override
	public CompanyCoupon queryByNameCode(Integer companyId, String serviceName,String code) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("serviceName", serviceName);
		map.put("code", code);
		return (CompanyCoupon) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByNameCode"), map);
	
	}
	
	@Override
	public CompanyCoupon getCouponByCode(Integer companyId, String code) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("code", code);
		map.put("companyId", companyId);
		return (CompanyCoupon) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countCodeByCondition"), map);
	}

}
