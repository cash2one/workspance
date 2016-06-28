/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午03:30:21
 */
package com.ast.ast1949.persist.company.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanySearch;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.zz91.util.lang.StringUtils;

/**
 * 
 * @author liulei
 * 
 */
@Component("companyDAO")
public class CompanyDAOImpl extends BaseDaoSupport implements CompanyDAO {

	private static String SQL_PREFIX = "company";

	public Integer insertCompany(Company company) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertCompany"), company);
	}

	public Company queryCompanyById(Integer id) {
		return (Company) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyById"), id);
	}
	
	public Company querySimpleCompanyById(Integer id) {
		return (Company) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "querySimpleCompanyById"), id);
	}

	public String queryMembershipOfCompany(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryMembershipOfCompany"), id);
	}

	public String queryAreaCodeOfCompany(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryAreaCodeOfCompany"), id);
	}

	public Integer updateIntroduction(Integer id, String intro) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("introduction", intro);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateIntroduction"), root);
	}

	@SuppressWarnings("unchecked")
	public List<Company> queryCompanyByClassifiedCode(String code,
			Integer maxSize) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("classifiedCode", code);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByClassifiedCode"),
				root);
	}

	@SuppressWarnings("unchecked")
	public List<Company> queryCompanyByArea(String areaCode, Integer maxSize) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("areaCode", areaCode);
		root.put("maxSize", maxSize);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByArea"), root);
	}

	public Integer updateCompanyByUser(Company company) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateCompanyByUser"), company);
	}

	public Integer updateCompanyByAdmin(Company company) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateCompanyByAdmin"), company);
	}
	public Integer updateCompanyByAdminCheck(Company company) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateCompanyByAdminCheck"), company);
	}
	@SuppressWarnings("unchecked")
	public List<Company> queryCompanyBySearch(Company company, PageDto page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyBySearch"), root);
	}

	public Integer queryCompanyBySearchCount(Company company) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyBySearchCount"), root);
	}

	@SuppressWarnings("unchecked")
	public List<Company> queryCompanyByAdmin(Company company,
			CompanyAccount account, Date gmtRegisterStart, Date gmtRegisterEnd,
			String activeFlag, PageDto<CompanyDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("account", account);
		root.put("regfrom", gmtRegisterStart);
		root.put("regto", gmtRegisterEnd);
		root.put("activeFlag", activeFlag);
		root.put("page", page);
		if (StringUtils.isNotEmpty(account.getEmail())
				|| StringUtils.isNotEmpty(account.getAccount())
				|| StringUtils.isNotEmpty(account.getMobile())) {
			root.put("caFlag", "1");
		}
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByAdmin"), root);
	}

	public Integer queryCompanyByAdminCount(Company company,
			CompanyAccount account, Date gmtRegisterStart, Date gmtRegisterEnd,
			String activeFlag) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("account", account);
		root.put("regfrom", gmtRegisterStart);
		root.put("regto", gmtRegisterEnd);
		root.put("activeFlag", activeFlag);

		if (StringUtils.isNotEmpty(account.getEmail())
				|| StringUtils.isNotEmpty(account.getAccount())
				|| StringUtils.isNotEmpty(account.getMobile())) {
			root.put("caFlag", "1");
		}
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByAdminCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyDto> queryCompanyByEmail(String email, Integer max) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("email", email);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByEmail"), root);
	}

	@Override
	public Company queryDomainOfCompany(Integer companyId) {
		return (Company) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryDomainOfCompany"), companyId);
	}

	@Override
	public Integer updateCustomDomain(Integer companyId, String domain) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("domain", domain);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateCustomDomain"), root);
	}

	@Override
	public Integer updateStar(Integer companyId, Integer star) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("star", star);
		root.put("id", companyId);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateStar"), root);
	}

	@Override
	public Integer updateLastVisit(Integer companyId) {

		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateLastVisit"), companyId);
	}

	@Override
	public Integer updateMembershipCode(Integer companyId, String membershipCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("membershipCode", membershipCode);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateMembershipCode"), root);
	}

	@Override
	public Integer queryIdByDomain(String domain) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryIdByDomain"), domain);
	}

	@Override
	public CompanyDto queryCompanyByAccountEmail(String email,Boolean isVip) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("isVip", isVip);
		return (CompanyDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByAccountEmail"),
				map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompanyByLoginNum(Integer max) {
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByLoginNum"), max);
	}

	@Override
	public CompanyDto queryCompanyWithAccountById(Integer id) {

		return (CompanyDto) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyWithAccountById"), id);
	}

	@Override
	public String queryDetails(Integer cid) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryDetails"), cid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryRecentZst(Date start, Integer limit) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("start", start);
		root.put("limit", limit);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryRecentZst"), root);
	}

	@Override
	public String queryNameByAccount(String account) {
		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryNameByAccount"), account);
	}

	@Override
	public Integer queryCompanyIdByDomainZz91(String domainZz91) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyIdByDomainZz91"),
				domainZz91);
	}

	@Override
	public Integer updateZstOpenInfo(Integer companyId, Integer zstYear,
			String domainZz91, String membershipCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("zstYear", zstYear);
		root.put("domainZz91", domainZz91);
		root.put("membershipCode", membershipCode);

		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateZstOpenInfo"), root);
	}
	
	@Override
	public Integer updateDomainZz91(Integer companyId, String domainZz91) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("domainZz91", domainZz91);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateDomainZz91"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompany(Company company, Date gmtRegisterStart,
			Date gmtRegisterEnd, Integer cid, PageDto<Company> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("cid", cid);
		root.put("page", page);
		root.put("gmtRegisterStart", gmtRegisterStart);
		root.put("gmtRegisterEnd", gmtRegisterEnd);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompany"), root);
	}

	@Override
	public Integer queryCompanyCountByAdmin(Company company,
			Date gmtRegisterStart, Date gmtRegisterEnd, Integer cid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("cid", cid);
		root.put("gmtRegisterStart", gmtRegisterStart);
		root.put("gmtRegisterEnd", gmtRegisterEnd);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyCountByAdmin"), root);
	}

	@Override
	public String queryCompanyNameById(Integer companyId) {

		return (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyNameById"), companyId);
	}

	@Override
	public Integer updateIsBlock(Integer companyId, String isBlock) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("isBlock", isBlock);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateIsBlock"), root);
	}

	@Override
	public Integer queryCompanyCount(String membershipCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("membershipCode", membershipCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyCount"), map);
	}

	@Override
	public Integer isBlocked(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "isBlocked"), account);
	}

	@Override
	public Integer deleteActiveFlag(Integer companyId) {
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteActiveFlag"), companyId);
	}

	@Override
	public void createActiveFlag(String[] activeFlagCode, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("activeFlag", activeFlagCode);
		root.put("companyId", companyId);
		getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "createActiveFlag"), root);
	}

	@Override
	public Integer updateActive(String activeFlag, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("activeFlag", activeFlag);
		root.put("companyId", companyId);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateActive"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompanyZstMember(Integer maxSize,String productsTypeCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maxSize", maxSize);
		map.put("productsTypeCode", productsTypeCode);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyZstMember"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode, String keywords) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("page", page);
		root.put("areaCode", areaCode);
		root.put("keywords", keywords);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyVip"), root);
	}

	@Override
	public Integer countQueryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode, String keywords) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("company", company);
		root.put("page", page);
		root.put("areaCode", areaCode);
		root.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countQueryCompanyVip"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompanyZstMemberAsc(Integer maxSize) {
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyZstMemberAsc"),
				maxSize);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyDto> queryCompanyZstMemberByAreacode(
			String industryCode, String areaCode, String keywords,
			PageDto<ProductsDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaCode", areaCode);
		map.put("industryCode", industryCode);
		map.put("page", page);
		map.put("keywords", keywords);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyZstMemberByAreacode"),
				map);
	}

	@Override
	public Integer countQueryCompanyZstMemberByAreacode(String industryCode,
			String areaCode, String keywords) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaCode", areaCode);
		map.put("industryCode", industryCode);
		map.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX,
						"countQueryCompanyZstMemberByAreacode"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompanyZstMemberByLastLoginTime(Integer maxSize) {

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByLastLoginTime"),
				maxSize);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryZstMemberByIndustryCode(String industryCode,
			Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mainCode", industryCode);
		map.put("maxSize", size);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryCompanyZstMemberByMainCode"),
				map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryBlackList(Company company,CompanyAccount companyAccount,String reason,String crmCode,PageDto<CompanyDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company", company);
		map.put("companyAccount", companyAccount);
		map.put("reason", reason);
		map.put("crmCode", crmCode);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryBlackList"), map);
	}

	@Override
	public Integer queryCountBlackList(Company company,CompanyAccount companyAccount,String reason,String crmCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company", company);
		map.put("companyAccount", companyAccount);
		map.put("reason", reason);
		map.put("crmCode", crmCode);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCountBlackList"),map);
	}

	@Override
	public Integer queryCountBlackByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCountBlackByCompanyId"), companyId);
	}

	@Override
	public Integer updateRegFromCode(Integer id, String code) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("regfromCode", code);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateRegFromCode"), root);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompanyByAdminSearch(Company company,CompanyAccountSearchDto searchDto, PageDto<CompanyDto> page){
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("company", company);
		root.put("searchDto", searchDto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyByAdminSearch"),root);
	}
	
	public Integer  queryCompanyCountByAdminSearch(Company company,CompanyAccountSearchDto searchDto){
		Map<String,Object> root=new HashMap<String, Object>();
		root.put("company", company);
		root.put("searchDto", searchDto);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCompanyCountByAdminSearch"),root);
				
	}
	@Override
	public Integer CompanyCount(){
	     return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "CompanyCount"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryCompanySearch(CompanySearch search) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("search", search);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCompanySearch"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryAllDomain() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllDomain"));
	}
}
