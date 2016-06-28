package com.ast1949.shebei.dao;

import java.util.Date;
import java.util.List;

import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.dto.PageDto;

/**
 * 
 * @author 陈庆林
 * 2012-7-24 下午3:21:27
 */
public interface CompanyDao {
	/**
	 * 数据的导入
	 * @author 陈庆林
	 * @param company(公司信息)
	 * @return
	 */
	
	public Integer insertCompany(Company company);
	
	/**
	 * 查询公司信息
	 * @author 陈庆林
	 * @param id(公司id)
	 * @return
	 */
	public Company queryCompanyById(Integer id);
	
	/**
	 * 查询公司列表
	 * @author 陈庆林
	 * @param page
	 * @return
	 */
	public List<Company> queryCompanys(PageDto<Company> page,String categoryCode);
	
	/**
	 * 统计公司数量
	 * @author 陈庆林
	 * @param companyId
	 * @return
	 */
	public Integer queryCompanyCount(String categoryCode);
	
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
	 * (查询最大展示时间)
	 * @author 陈庆林
	 * @return
	 */
	public Date queryMaxGmtShow();
	
	/**
	   * 查询公司名称
	   * @author 陈庆林
	   * @return String
	   */
	 
	public String queryNameById(Integer companyId);

	/**
	 * 根据账户查看公司id(数据导入判断用)
	 * @author 齐振杰
	 * @param account
	 * @return
	 */
	public Integer queryCompIdByAccount(String account);
}
