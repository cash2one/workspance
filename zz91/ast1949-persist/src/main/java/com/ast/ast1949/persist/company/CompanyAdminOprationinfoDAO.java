/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-3
 */
package com.ast.ast1949.persist.company;

import com.ast.ast1949.domain.company.CompanyAdminOprationinfoDO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface CompanyAdminOprationinfoDAO {
	/**
	 * 添加记录
	 * @param companyAdminOprationinfo
	 * @return 返回影响行数
	 */
	public Integer insertCompanyAdminOprationinfo(CompanyAdminOprationinfoDO companyAdminOprationinfo);
	/**
	 * 根据公司ID更新记录
	 * @param companyAdminOprationinfo
	 * @return
	 */
	public Integer updateSimplePropertyByCompanyId(CompanyAdminOprationinfoDO companyAdminOprationinfo);
	/**
	 * 根据公司ID统计记录数
	 * @param id
	 * @return
	 */
	public Integer countByCompanyId(Integer id);
	/**
	 * 根据公司ID读取信息
	 * @param id
	 * @return
	 */
	public CompanyAdminOprationinfoDO queryCompanyAdminOprationinfoByCompanyId(Integer id);
}
