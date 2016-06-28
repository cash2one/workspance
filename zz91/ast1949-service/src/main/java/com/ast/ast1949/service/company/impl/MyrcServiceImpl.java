/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23 by liulei.
 */
package com.ast.ast1949.service.company.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.MyrcService;
import com.ast.ast1949.service.phone.PhoneService;

@Component("myrcService")
public class MyrcServiceImpl implements MyrcService {

	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private CompanyService companyService;

	@Override
	public void initMyrc(Map<String, Object> out, Integer companyId) {
		
		// 判断用户是否拥有了商铺服务
		out.put("isEsite", false);
		if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.ESITE_CODE)){
			out.put("isEsite", true);
		}else{
			if(crmCompanySvrService.validatePeriod(companyId, CrmCompanySvrService.BAIDU_CODE)){
				out.put("isEsite", true);
				out.put("mark", 1);
				out.put("DomainZz91", companyService
						.queryDomainOfCompany(companyId).getDomainZz91());
			}
		}
		// 首页来电宝客户链接
		Phone phone = phoneService.queryByCompanyId(companyId);
		if(phone!=null){
			out.put("phone", phone);
		}
	}
}
