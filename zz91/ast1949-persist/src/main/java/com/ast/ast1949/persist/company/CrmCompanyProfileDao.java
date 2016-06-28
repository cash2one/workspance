package com.ast.ast1949.persist.company;

import com.ast.ast1949.domain.company.CrmCompanyProfile;

public interface CrmCompanyProfileDao {
	/**
	 * 查找公司的profile信息
	 *
	 *  
	 */
	public CrmCompanyProfile queryProfile(Integer companyId);
	/**
	 * 创建新的profile，其中profile.companyId必需有值
	 */
	public Integer insertProfile(CrmCompanyProfile profile);
	/**
	 * 更新profile
	 */
	public Integer updateProfile(CrmCompanyProfile profile);
	
	/**
	 * 更新客户库状态
	 * @param companyId
	 * @param type
	 * @return
	 */
	public Integer updateOutbusiness(Integer companyId, String type);
}
 
