/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午03:19:59
 */
package com.ast.ast1949.persist.company;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanySearch;
import com.ast.ast1949.dto.products.ProductsDto;

/**
 * 公司信息DAO操作类
 * 
 * @author Ryan
 * 
 */

public interface CompanyDAO {

	final static String BLOCK_FALSE = "0";
	final static String ZST_FLAG_FALSE = "0";
	final static String DEFAULT_MEMBERSHIP = "10051000";
	final static String DEFAULT_CLASSIFIED = "10101002";
	final static String DEFAULT_REGFROM = "10031000";

	/*******************
	 * 查询再生通新会员
	 * 
	 * @return
	 */
	public List<Company> queryCompanyZstMember(Integer maxSize,String productsTypeCode);

	/*******************
	 * 查询再生通会员 asc 排序
	 * 
	 * @return
	 */
	public List<Company> queryCompanyZstMemberAsc(Integer maxSize);

	/*******************
	 * 查询再生通会员 最后一次登陆时间 排序
	 * 
	 * @return
	 */
	public List<Company> queryCompanyZstMemberByLastLoginTime(Integer maxSize);

	/**
	 * 添加公司信息
	 * 
	 * @param companyDO信息
	 * @return 插入成功返回id
	 */
	public Integer insertCompany(Company company);

	public Company queryCompanyById(Integer id);

	public Company querySimpleCompanyById(Integer id);

	public String queryMembershipOfCompany(Integer id);

	public String queryAreaCodeOfCompany(Integer id);

	public Integer updateIntroduction(Integer id, String intro);

	public List<Company> queryCompanyByClassifiedCode(String code,Integer maxSize);

	public List<Company> queryCompanyByArea(String areaCode, Integer maxSize);

	public Integer updateCompanyByUser(Company company);

	public Integer updateCompanyByAdmin(Company company);
	
	public Integer updateCompanyByAdminCheck(Company company);
	
	public List<Company> queryCompanyBySearch(Company company, PageDto page);

	public Integer queryCompanyBySearchCount(Company company);

	public List<Company> queryCompanyByAdmin(Company company,
			CompanyAccount account, Date gmtRegisterStart, Date gmtRegisterEnd,
			String activeFlag, PageDto<CompanyDto> page);

	public Integer queryCompanyByAdminCount(Company company,
			CompanyAccount account, Date gmtRegisterStart, Date gmtRegisterEnd,
			String activeFlag);

	public List<CompanyDto> queryCompanyByEmail(String email, Integer max);

	public Integer updateCustomDomain(Integer companyId, String domain);

	public Company queryDomainOfCompany(Integer companyId);

	public Integer updateStar(Integer companyId, Integer star);

	public Integer updateLastVisit(Integer companyId);

	public Integer updateMembershipCode(Integer companyId, String membershipCode);

	public Integer queryIdByDomain(String domain);

	public CompanyDto queryCompanyByAccountEmail(String email,Boolean isVip);

	public List<Company> queryCompanyByLoginNum(Integer max);

	public CompanyDto queryCompanyWithAccountById(Integer id);

	public String queryDetails(Integer cid);

	public List<Company> queryRecentZst(Date start, Integer limit);

	public String queryNameByAccount(String account);

	public Integer queryCompanyIdByDomainZz91(String domainZz91);

	public Integer updateZstOpenInfo(Integer companyId, Integer zstYear,
			String domainZz91, String membershipCode);

	@Deprecated
	public List<Company> queryCompany(Company company, Date gmtRegisterStart,
			Date gmtRegisterEnd, Integer cid, PageDto<Company> page);

	@Deprecated
	public Integer queryCompanyCountByAdmin(Company company,
			Date gmtRegisterStart, Date gmtRegisterEnd, Integer cid);

	public String queryCompanyNameById(Integer companyId);

	public Integer updateIsBlock(Integer companyId, String isBlock);

	public Integer queryCompanyCount(String membershipCode);

	public Integer isBlocked(String account);

	public Integer deleteActiveFlag(Integer companyId);

	public void createActiveFlag(String[] activeFlagCode, Integer companyId);

	public Integer updateActive(String activeFlag, Integer companyId);

	public Integer updateDomainZz91(Integer companyId, String domainZz91);

	public List<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode, String keywords);

	public Integer countQueryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode, String keywords);

	/**
	 * 按照地区搜索 高会
	 * 
	 * @return list
	 */
	public List<CompanyDto> queryCompanyZstMemberByAreacode(
			String industryCode, String areaCode, String keywords,
			PageDto<ProductsDto> page);

	/**
	 * 按照地区搜索 高会
	 * 
	 * @return 总条数
	 */
	public Integer countQueryCompanyZstMemberByAreacode(String industryCode,
			String areaCode, String keywords);

	/**
	 * 根据主营类别查询高会：用在trade 普会门市部化
	 * 
	 * @param industryCode
	 *            ：主营编号
	 * @param size
	 *            ：返回的记录条数
	 * @return
	 */
	public List<Company> queryZstMemberByIndustryCode(String industryCode,
			Integer size);

	public List<Company> queryBlackList(Company company,CompanyAccount companyAccount,String reason,String crmCode,PageDto<CompanyDto> page);

	public Integer queryCountBlackList(Company company,CompanyAccount companyAccount,String reason,String crmCode);
	
	/**
	 * 验证公司黑名单
	 * @param companyId 公司id
	 * @return
	 */
	public Integer queryCountBlackByCompanyId(Integer companyId);
	
	public Integer updateRegFromCode(Integer id, String code);
	/**
	 * 按条件查找公司
	 * @param company
	 * @param searchDto
	 * @param page
	 * @return
	 */
	public List<Company> queryCompanyByAdminSearch(Company company,CompanyAccountSearchDto searchDto, PageDto<CompanyDto> page);
	/**
	 * 查询公司的数量
	 * @param company
	 * @param searchDto
	 * @return
	 */
	public Integer queryCompanyCountByAdminSearch(Company company,CompanyAccountSearchDto searchDto);
	/**
	 * 统计公司数量
	 * @return
	 */
	
	public Integer CompanyCount();

	public List<Company> queryCompanySearch(CompanySearch search);
	
	public List<Map<String, Object>> queryAllDomain();
}
