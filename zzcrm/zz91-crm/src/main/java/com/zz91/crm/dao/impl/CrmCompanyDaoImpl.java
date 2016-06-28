package com.zz91.crm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.dto.CommCompanyDto;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmCompanyDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;

@Repository("crmCompanyDao")
public class CrmCompanyDaoImpl extends BaseDao implements CrmCompanyDao {

	final static String SQL_PREFIX = "crmCompany";

	@Override
	public Integer createCompany(CrmCompany company) {
		return (Integer)getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createCompany"),company);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommCompanyDto> queryCommCompany(CompanySearchDto search,
			PageDto<CommCompanyDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCommCompany"),root);
	}

	@Override
	public Integer queryCommCompanyCount(CompanySearchDto search) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCommCompanyCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompany> queryCommCompanyByConditions(String cname, String name,
			String email, String mobile, String phone, String fax,Integer limit) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cname", cname);
		root.put("name", name);
		root.put("email", email);
		root.put("mobile", mobile);
		root.put("phone", phone);
		root.put("fax", fax);
		root.put("limit", limit);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCommCompanyByConditions"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SaleCompanyDto> queryCommCompanyByContact(String mobile, String phone,Integer id,
			Integer limit) {
		Map<String, Object> root = new HashMap<String, Object>();
		if (mobile.length() > 11) {
			root.put("mobile", mobile.substring(mobile.length()-11, mobile.length()));
		} else {
			root.put("mobile", mobile);
		}
		if (phone.length() > 7) {
			root.put("phone", phone.substring(phone.length()-7, phone.length()));
		} else {
			root.put("phone", phone);
		}
		root.put("id", id);
		root.put("limit", limit);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCommCompanyByContact"),root);
	}


	@Override
	public CrmCompany queryCompanyById(Integer id,Short ctype) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("ctype", ctype);
		return (CrmCompany) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompanyById"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SaleCompanyDto> queryMyCompany(CompanySearchDto search,
			PageDto<SaleCompanyDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryMyCompany"),root);
	}

	@Override
	public Integer queryMyCompanyCount(CompanySearchDto search) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("search", search);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMyCompanyCount"),root);
	}

	@Override
	public Integer updateCompany(CrmCompany crmCompany) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompany"),crmCompany);
	}

	@Override
	public Integer updateCtypeById(Integer id, Short newType) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("newType", newType);
		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCtypeByid"),root);
	}

	@Override
	public Integer updateDisableContactById(Integer id, Short status) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("status", status);
		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDisableContactByid"),root);
	}

	@Override
	public Integer updateRegistStatus(Integer id, Short status,String memberCode) {
		Map<String, Object> root = new HashMap<String,Object>();
		root.put("id", id);
		root.put("status", status);
		root.put("memberCode", memberCode);
		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRegistStatus"),root);
	}

	@Override
	public Integer updateRepeatId(Integer id, Integer newId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("newId", newId);
		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRepeatId"),root);
	}

	@Override
	public Integer queryIdByCid(Integer cid) {
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryIdByCid"),cid);
	}

	@Override
	public Integer updateSaleCompIdById(Integer cid, Integer saleCompId) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("saleCompId", saleCompId);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSaleCompIdById"), root);
	}

	@Override
	public CrmCompany queryContactById(Integer cid) {
		return (CrmCompany) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryContactById"), cid);
	}

	@Override
	public Integer queryCountByMobileAndEmail(String mobile, String email) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("mobile", mobile);
		root.put("email", email);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountByMobileAndEmail"), root);
	}

	@Override
	public Integer queryCountById(Integer id, Short ctype) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("ctype", ctype);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountById"), root);
	}

	@Override
	public CrmCompany queryCompById(Integer id) {
		return (CrmCompany) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompById"), id);
	}

	@Override
	public Integer updateStarByCid(Integer id, Short star,Short source,Short maturity,
			Short promote,String saleAccount,String saleName,Short kp,Short callType) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("newStar", star);
		root.put("source", source);
		root.put("maturity", maturity);
		root.put("promote", promote);
		root.put("saleAccount", saleAccount);
		root.put("saleName", saleName);
		root.put("kp", kp);
		root.put("callType", callType);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStarByCid"), root);
	}
	
	@Override
	public CrmCompany queryStarById(Integer id) {
		return (CrmCompany) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryStarById"),id);
	}

	@Override
	public Integer updateSaleStatusById(Integer id, Short saleStatus) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("saleStatus", saleStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSaleStatusById"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommCompanyDto> queryOnceFourOrFive(CompanySearchDto search,
			PageDto<CommCompanyDto> page) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("search", search);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryOnceFourOrFive"), root);
	}

	@Override
	public Integer queryOnceFourOrFiveCount(CompanySearchDto search) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOnceFourOrFiveCount"), root);
	}

	@Override
	public CrmCompany querySimplyCompById(Integer id) {
		return (CrmCompany) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySimplyCompById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompanyDto> querySimplyComp() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySimplyComp"));
	}

	@Override
	public String queryCnameById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCnameById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SaleCompanyDto> queryBaseComp(PageDto<SaleCompanyDto> page,
			CrmCompany company) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		root.put("company", company);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBaseComp"), root);
	}

	@Override
	public Integer queryBaseCompCount(CrmCompany company) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBaseCompCount"), root);
	}
}