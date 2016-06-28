package com.ast.ast1949.persist.phone.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneLogDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneLogDao;
import com.zz91.util.datetime.DateUtil;

/**
 * author:kongsj date:2013-7-13
 */
@Component("phoneLogDao")
public class PhoneLogDaoImpl extends BaseDaoSupport implements PhoneLogDao {

	final static String SQL_FIX = "phoneLog";

	@Override
	public Integer insert(PhoneLog phoneLog) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), phoneLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLog> queryList(PhoneLog phoneLog, PageDto<PhoneLog> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(PhoneLog phoneLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}
	
	@Override
	public Integer queryDtoListCount(PhoneLog phoneLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryDtoListCount"), map);
	}
	@Override
	public Integer queryDtoListCounts(PhoneLog phoneLog,String mobile){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		map.put("mobile", mobile);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryDtoListCounts"), map);
	}

	@Override
	public PhoneLog queryByCallSn(String callSn) {
		return (PhoneLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCallSn"), callSn);
	}

	@Override
	public String countCallFee(String tel,Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tel", tel);
		map.put("companyId", companyId);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countCallFee"), map);
	}
	
	@Override
	public String countCallFeeWithOutToday(String tel,Integer companyId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tel", tel);
		map.put("companyId", companyId+"");
		map.put("date", DateUtil.toString(new Date(), "yyyy-MM-dd"));
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countCallFeeWithOutToday"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLogDto> queryDtoList(PhoneLog phoneLog,
			PageDto<PhoneLogDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryDtoListByAdmin"), map);
	}

	@Override
	public String countCallFeeByTime(PhoneLog phoneLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countCallFeeByTime"),map);
	}
	@Override
	public String countEveCallFee(PhoneLog phoneLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countEveCallFee"),map);
	}

	@Override
	public Integer queryPhoneLogListCount(PhoneLog phoneLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryPhoneLogListCount"), map);
	}

	@Override
	public String countAllCallFee() {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countAllCallFee"));
	}

	@Override
	public String countCallFeeByCallSn(PhoneLog phoneLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countCallFeeByCallSn"),map);
	}

	@Override
	public Integer queryPhoneLogIsCount(PhoneLog phoneLog) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryPhoneLogIsCount"),map);
	}

	@Override
	public Integer queryCountTelRentByPhoneLog(PhoneLog phoneLog) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountTelRentByPhoneLog"),map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLog> queryListByTel(String tel,PageDto<PhoneLog> page){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tel", tel);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryListByTel"),map);
	}
	@Override
	public Integer countListByTel(String tel){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countListByTel"),tel);
	}
	@Override
	public PhoneLog queryPhoneLogByCallSn(String callSn){
		return (PhoneLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryPhoneLogByCallSn"),callSn);
	}
	@Override
	public Integer queryYJCompanyBytime(String from,String to){
		Map<String, String> map=new HashMap<String, String>();
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryYJCompanyBytime"),map);
	}
	@Override
	public Integer queryWJCompanyBytime(String from,String to){
		Map<String, String> map=new HashMap<String, String>();
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryWJCompanyBytime"),map);
	}
	@Override
	public Integer countYJCompanyBytime(Integer companyId,String from,String to){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countYJCompanyBytime"),map);
	}
	@Override
	public Integer countWJCompanyBytime(Integer companyId,String from,String to){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countWJCompanyBytime"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLogDto> exportPhoneLog(String tel,Date from,Date to,PageDto<PhoneLogDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tel", tel);
		map.put("from", from);
		map.put("to", to);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "exportPhoneLog"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLogDto> exportAllPhoneLog(Date from, Date to, PageDto<PhoneLogDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "exportAllPhoneLog"), map);
	}
	
	@Override
	public Integer updateCallFeeById(Integer id){
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateCallFeeById"), id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneLogDto> queryDtoListForFront(PhoneLog phoneLog,PageDto<PhoneLogDto> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneLog", phoneLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryDtoListForFront"), map);
	}
}
