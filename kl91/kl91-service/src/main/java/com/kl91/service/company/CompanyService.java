package com.kl91.service.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kl91.domain.company.Company;
import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.company.CompanySearchDto;

public interface CompanyService {

	/**
	 * 创建公司信息： 使用于注册页面第一步
	 */
	public Integer createCompany(Company company);

	/**
	 * 更新公司信息 使用于注册第二部页面
	 */
	public Integer editCompany(Company company);

	/**
	 * 根据供求ID,搜索供求、公司详细信息。 使用于旺铺detail页面
	 */
	public Company queryById(Integer id);

	/**
	 * 搜索公司域名 适用与客户进入旺铺的时候, 如果客户域名为空, 则自动调用帐号作为域名。
	 */
	public String queryDomainById(Integer id);

	/**
	 * 搜索公司ID搜索公司详细介绍 使用于修改公司详细页面
	 */
	public String queryIntroductionById(Integer id);
	
	/**
	 * 统计发布信息最多的公司 公司黄页首页使用
	 */
	public List<Company> queryMostPublish(Integer size);

	/**
	 * 从solr搜索引擎，搜索数据。 适用与list页面的搜索
	 */
	public PageDto<Company> queryCompanyFromSolr(PageDto<Company> page,
			CompanySearchDto searchDto);

	/**
	 * 更新公司发布供求数量
	 */
	public Integer updateNumPassById(Integer companyId, Integer count);
	
	/**
	 * 验证用户信息
	 * 用于登录
	 */
	public Integer validateAccount(HttpServletRequest request,String account, String pwd);
	
	/**
	 * 根据email搜索公司信息
	 * 使用于密码找回
	 */
	public Company queryByEmail(String email);
	
	/**
	 * 更改密码
	 * 用户重置密码使用
	 */
	public Integer updatePwdById(String pwd, Integer id);
	
	public Integer countUserByAccount(String account);
	
	public Company queryByMobile(String mobile);
	
	public Company queryByAccount(String account);
	
	public Integer updateCompanyByMyrc(String contact,String mobile,Integer sex,String email,Integer id);
}

