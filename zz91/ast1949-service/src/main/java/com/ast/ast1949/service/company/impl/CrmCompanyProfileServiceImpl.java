package com.ast.ast1949.service.company.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCompanyProfile;
import com.ast.ast1949.persist.company.CrmCompanyProfileDao;
import com.ast.ast1949.service.company.CrmCompanyProfileService;
import com.ast.ast1949.util.Assert;

@Component("crmCompanyProfileService")
public class CrmCompanyProfileServiceImpl implements CrmCompanyProfileService {

	@Resource
	private CrmCompanyProfileDao crmCompanyProfileDao;
	@Override
	public CrmCompanyProfile queryProfile(Integer companyId) {
//		Assert.notNull(companyId, "the companyId can not be null");
		if(companyId==null){
			return new CrmCompanyProfile();
		}
		CrmCompanyProfile profile= crmCompanyProfileDao.queryProfile(companyId);
		if(profile==null){
			profile = new CrmCompanyProfile();
		}
		return profile;
	}

	@Override
	public Integer createProfile(CrmCompanyProfile profile) {
		Assert.notNull(profile.getCompanyId(),"the CompanyId can not be null");
		return crmCompanyProfileDao.insertProfile(profile);
	}

	@Override
	public Integer updateProfile(CrmCompanyProfile profile) {
		Assert.notNull(profile,"the profile can not be null");
		return crmCompanyProfileDao.updateProfile(profile);
	}

	@Override
	public Integer updateOutbusiness(Integer companyId, String type) {
		Assert.notNull(type,"the type can not be null");
		Assert.notNull(companyId,"the companyId can not be null");
		return crmCompanyProfileDao.updateOutbusiness(companyId,type);
	}

}
