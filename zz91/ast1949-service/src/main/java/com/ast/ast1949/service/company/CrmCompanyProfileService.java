package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CrmCompanyProfile;


public interface CrmCompanyProfileService {
 
	/**
	 * 查找公司的profile信息，如果库中没有记录，则返回new CrmCompanyProfile
	 *
	 *  
	 */
	public CrmCompanyProfile queryProfile(Integer companyId);
	/**
	 * 创建新的profile，其中profile.companyId必需有值
	 */
	public Integer createProfile(CrmCompanyProfile profile);
	/**
	 * 更新profile
	 */
	public Integer updateProfile(CrmCompanyProfile profile);
	
	public Integer updateOutbusiness(Integer companyId,String type);
	
}
 
