package com.ast.ast1949.service.company.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.persist.company.CrmSvrApplyDao;
import com.ast.ast1949.service.company.CrmSvrApplyService;
import com.zz91.util.lang.StringUtils;

@Component("crmSvrApplyService")
public class CrmSvrApplyServiceImpl implements CrmSvrApplyService {

	@Resource
	private CrmSvrApplyDao crmSvrApplyDao;
	
	@Override
	public CrmServiceApply queryApplyByGroup(String applyGroup) {
		if (StringUtils.isEmpty(applyGroup)) {
			return null;
		}
		return crmSvrApplyDao.queryApplyByGroup(applyGroup);
	}

	@Override
	public Integer updateApply(CrmServiceApply apply) {
		Assert.notNull(apply, "the apply can not be null");
		return crmSvrApplyDao.updateApply(apply);
	}

}
