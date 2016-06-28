package com.ast1949.shebei.service;

import java.util.Date;
import java.util.List;

import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.dto.PageDto;

public interface CompanyService {
	
	/**
	 * 数据的导入
	 * @author 陈庆林
	 * @param company(公司信息)
	 * @return
	 */
	public Integer createCompany(Company company);

	/**
	 * 查询公司信息
	 * @author 陈庆林
	 * @param id
	 * @return
	 */
	public Company queryCompanyById(Integer companyId);

	/**
	 * 查询公司列表
	 * @author 陈庆林
	 * @param page
	 * @return
	 */
	public PageDto<Company> pageCompanys(PageDto<Company> page,String categoryCode);

	/**
	 * 查询简介
	 * @author 陈庆林
	 * @param companyId
	 * @return
	 */
	public String queryDeatilsById(Integer companyId);

	/**
	 * 查询联系方式
	 * @author 陈庆林
	 * @param  companyId(公司id)
	 * @return
	 */
	public Company queryContactById(Integer companyId);

	/**
	 * 查询最新公司(可能感兴趣的公司)
	 * @author 陈庆林
	 * @param size
	 * @param categoryCode
	 * @return
	 */
	public List<Company> queryNewestCompany(Integer size,String categoryCode);

	
	  /**
	   * 查询公司名称
	   * @author 陈庆林
	   * @return String
	   */
	 
	public String queryNameById(Integer companyId);

	/**
	 * (查询最大展示时间)
	 * @author 陈庆林
	 * @return
	 */
	public Date queryMaxGmtShow();

	/**
	 * 根据账户查看公司id(数据导入判断用)
	 * @author 齐振杰
	 * @param account
	 * @return
	 */
	public Integer queryCompIdByAccount(String account);
}
