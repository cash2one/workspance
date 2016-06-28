package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneCsBs;
import com.ast.ast1949.persist.phone.PhoneCsBsDao;
import com.ast.ast1949.service.phone.PhoneCsBsService;

@Component("phoneCsBsService")
public class PhoneCsBsServiceImpl implements PhoneCsBsService {

	@Resource
	private PhoneCsBsDao phoneCsBsDao;

	@Override
	public PhoneCsBs queryByCompanyId(Integer companyId) {
		if (companyId == null) {
			return null;
		}
		return phoneCsBsDao.queryByCompanyId(companyId);
	}

}
