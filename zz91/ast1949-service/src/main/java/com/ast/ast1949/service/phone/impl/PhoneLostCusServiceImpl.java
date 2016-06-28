package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneLostCus;
import com.ast.ast1949.persist.phone.PhoneLostCusDao;
import com.ast.ast1949.service.phone.PhoneLostCusService;
@Component("phoneLostCusService")
public class PhoneLostCusServiceImpl implements PhoneLostCusService {
	@Resource
    private PhoneLostCusDao phoneLostCusDao;
	@Override
	public PhoneLostCus queryPhoneLostCusBycompanyId(Integer companyId) {
		return phoneLostCusDao.queryPhoneLostCusBycompanyId(companyId);
	}
	@Override
	public Integer deletePhoneLostCusBycompanyId(Integer companyId) {
		return phoneLostCusDao.deletePhoneLostCusBycompanyId(companyId);
	}
	

}
