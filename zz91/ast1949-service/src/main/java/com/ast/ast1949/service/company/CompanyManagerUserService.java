/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-28
 */
package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CompanyManagerUserDO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface CompanyManagerUserService {
	/**
	 * 添加记录
	 * @param companyManagerUser
	 * @return
	 */
//	public Integer insertCompanyManagerUser(CompanyManagerUserDO companyManagerUser);
	/**
	 * 删除记录
	 * @param id 公司ID
	 * @return
	 */
//	public Integer deleteCompanyManagerUserByCompanyId(Integer id);
	/**
	 * 统计记录总数
	 * @param id 公司ID
	 * @return
	 */
//	public Integer countCompanyManagerUserByCompanyId(Integer id);
	/**
	 * 根据公司Id更新记录
	 * @param companyManagerUser
	 * @return
	 */
//	public Integer updateAdminUserIdByCompanyId(CompanyManagerUserDO companyManagerUser);
	/**
	 * 放入我的客户
	 * @param companyManagerUser
	 */
	public boolean join(CompanyManagerUserDO companyManagerUser);
	/**
	 * 取消放入我的
	 * @param id 公司ID
	 * @return
	 */
	public boolean cancelJoin(Integer id);
	/**
	 * 根据公司Id读取信息
	 * @param id
	 * @return
	 */
	public CompanyManagerUserDO queryCompanyManagerUserByCompanyId(Integer id);
}
