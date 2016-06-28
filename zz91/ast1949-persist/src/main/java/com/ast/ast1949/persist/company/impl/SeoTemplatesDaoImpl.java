package com.ast.ast1949.persist.company.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.SeoTemplatesDao;

@Component("seoTemplatesDao")
public class SeoTemplatesDaoImpl extends BaseDaoSupport implements
		SeoTemplatesDao {

	final static String SQL_FIX = "seoTemplates";

	@Override
	public Integer queryByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}

}
