/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-26
 */
package com.ast.ast1949.service.company;


/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface CompanyAdminUserService {
	/**
	 * 添加记录
	 * @param companyMembershipDO
	 * @return 插入成功返回id
	 */
//	public Integer insertCompanyAdminUserDO(CompanyAdminUserDO companyAdminUserDO);
	/**
	 * 根据公司编号统计记录数
	 * @param id 公司编号
	 * @return 返回符合条件的记录数
	 */
//	public Integer queryCountByCompanyId(Integer id);
	/**
	 * 修改客户的所有者（客服）
	 * @param param 参数：<br/>
	 * 		adminUserId 客服编号
	 * 		companyId 公司编号
	 * @return
	 */
//	public Integer changeAdminUserIdByCompanyId(Map<String, Object> param);
	/**
	 * 分配客户 
	 * @param companyId 公司编号
	 * @param adminUserId 客服编号
	 * @return
	 */
	public Integer assignCustomers(Integer companyId,Integer adminUserId);
	/**
	 * 删除记录
	 * @param id 公司编号
	 * @return
	 */
	public Integer deleteByCompanyId(Integer id);
}
