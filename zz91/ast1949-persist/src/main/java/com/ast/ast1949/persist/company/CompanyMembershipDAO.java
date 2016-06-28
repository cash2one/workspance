/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.persist.company;

import com.ast.ast1949.domain.company.CompanyMembershipDO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface CompanyMembershipDAO {
	
	/**
	 * 添加记录
	 * @param companyMembershipDO
	 * @return 插入成功返回id
	 */
	public Integer insertCompanyMembership(CompanyMembershipDO companyMembershipDO);
	
	/**
	 * 查询客户会员类型
	 * @param companyId为公司主键，不能为空，否则抛异常
	 * @return CompanyMembershipDO信息，没查找到数据返回空
	 */
	public CompanyMembershipDO queryCompanyMembershipByCompanyId(Integer companyId);
	/**
	 * 根据公司编号更新记录
	 * @param companyMembership
	 * @return 返回影响行数
	 */
	public Integer updateCompanyMembershipByCompanyId(CompanyMembershipDO companyMembership);
	
	public Integer queryCompanyIdByUrl(String url);
	
	public Integer queryCompanyIdByDomain(String domain);
	
	public Integer saveCustomDomain(Integer companyId, String domain);
	
}
