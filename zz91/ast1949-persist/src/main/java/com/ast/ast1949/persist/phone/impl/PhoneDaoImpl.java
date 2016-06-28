package com.ast.ast1949.persist.phone.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneDao;

/**
 * author:kongsj date:2013-7-3
 */
@Component("phoneDao")
public class PhoneDaoImpl extends BaseDaoSupport implements PhoneDao {

	final static String SQL_FIX = "phone";

	@Override
	public Integer insert(Phone phone) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), phone);
	}

	@Override
	public Phone queryById(Integer id) {
		return (Phone) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Integer update(Phone phone) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), phone);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Phone> queryList(Phone phone, PageDto<Phone> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(Phone phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@Override
	public Integer updateAmountAndBalance(Integer id, String amount,
			String balance) {
		Map<String , Object>map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("amount", amount);
		map.put("balance", balance);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateAmountAndBalance"), map);
	}

	@Override
	public Integer countByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByAccount"), account);
	}

	@Override
	public Integer countByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyId"), companyId);
	}

	@Override
	public Integer deleteById(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "deleteById"),id);
	}

	@Override
	public Phone queryByCompanyId(Integer companyId) {
		return (Phone) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}

	@Override
	public Phone queryByTel(String tel) {
		return (Phone) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByTel"), tel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneDto> queryAllList(Phone phone,CompanyAccount companyAccount,PhoneCostSvr phoneCostSvr,PageDto<PhoneDto> page,Company company,float laveFrom,float laveTo,String csAccount,Date svrFroms,Date svrTos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("page", page);
		map.put("companyAccount", companyAccount);
		map.put("phoneCostSvr", phoneCostSvr);
		map.put("company", company);
		map.put("laveFrom", laveFrom);
		map.put("laveTo", laveTo);
		map.put("csAccount", csAccount);
		map.put("svrFroms", svrFroms);
		map.put("svrTos", svrTos);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryAllList"), map);
	}

	@Override
	public String queryAllPhoneAmount() {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryAllPhoneAmount"));
	}

	@Override
	public Integer queryListCountByAdmin(Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,Company company) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("companyAccount", companyAccount);
		map.put("phoneCostSvr", phoneCostSvr);
		map.put("company", company);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryListCountByAdmin"),map);
	}
	@Override
	public Integer queryAllListCount(Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,Company company,float laveFrom,float laveTo,String csAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("companyAccount", companyAccount);
		map.put("phoneCostSvr", phoneCostSvr);
		map.put("company", company);
		map.put("laveFrom", laveFrom);
		map.put("laveTo", laveTo);
		map.put("csAccount", csAccount);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryAllListCount"),map);
	}
	@Override
	public Integer updateSmsFee(String smsFee, Integer companyId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smsFee", smsFee);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateSmsFee"), map);
	}
	
	@Override
	public Integer updateClose(String frontTel,Integer companyId){
		Map<String, String> map = new HashMap<String, String>();
		map.put("frontTel", frontTel);
		map.put("companyId",""+companyId);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateClose"), map);
	}
	
	@Override
	public Integer updateAmountByCompanyId(String amount,Integer companyId ){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("amount", amount);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateAmountByCompanyId"), map);
	}
	
	@Override
	public Integer queryCompanyIdByTel(String tel){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCompanyIdByTel"), tel);
	}
	@Override
	public String querytelByCompanyId(Integer companyId){
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "querytelByCompanyId"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneDto> queryAllListl(Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, Company company, float laveFrom,
			float laveTo, String csAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("page", page);
		map.put("companyAccount", companyAccount);
		map.put("phoneCostSvr", phoneCostSvr);
		map.put("company", company);
		map.put("laveFrom", laveFrom);
		map.put("laveTo", laveTo);
		map.put("csAccount", csAccount);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryAllListl"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneDto> queryAllBsList(Phone phone,CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			PageDto<PhoneDto> page, Company company, float laveFrom,
			float laveTo, String csAccount, String from, String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("page", page);
		map.put("companyAccount", companyAccount);
		map.put("phoneCostSvr", phoneCostSvr);
		map.put("company", company);
		map.put("laveFrom", laveFrom);
		map.put("laveTo", laveTo);
		map.put("csAccount", csAccount);
		map.put("from", from);
		map.put("to", to);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryAllBsList"), map);
	}

	@Override
	public Integer queryAllBsListCount(Phone phone,
			CompanyAccount companyAccount, PhoneCostSvr phoneCostSvr,
			Company company, float laveFrom, float laveTo, String csAccount,
			String from, String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("companyAccount", companyAccount);
		map.put("phoneCostSvr", phoneCostSvr);
		map.put("company", company);
		map.put("laveFrom", laveFrom);
		map.put("laveTo", laveTo);
		map.put("csAccount", csAccount);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryAllBsListCount"),map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneDto> pagePhoneCallFee(PageDto<PhoneDto> page, String from,String  to) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("from", from);
		map.put("to", to);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "pagePhoneCallFee"), map);
	}
}
