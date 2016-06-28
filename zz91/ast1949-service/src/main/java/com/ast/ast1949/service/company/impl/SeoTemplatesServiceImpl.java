package com.ast.ast1949.service.company.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.company.SeoTemplatesDao;
import com.ast.ast1949.service.company.SeoTemplatesService;

@Component("seoTemplatesService")
public class SeoTemplatesServiceImpl implements SeoTemplatesService {

	@Resource
	private SeoTemplatesDao seoTemplatesDao;

	@Override
	public boolean validate(Integer companyId) {
		if (companyId == null) {
			return false;
		}
		
		Integer i = seoTemplatesDao.queryByCompanyId(companyId);
		
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

}
