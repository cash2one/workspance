package com.ast.ast1949.persist.phone.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneCsBs;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneCsBsDao;

@Component("phoneCsBsDao")
public class PhoneCsBsDaoImpl extends BaseDaoSupport implements PhoneCsBsDao {

	final static String SQL_FIX = "phoneCsBs";

	@Override
	public Integer insert(PhoneCsBs phoneCsBs) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), phoneCsBs);
	}

	@Override
	public PhoneCsBs queryByCompanyId(Integer companyId) {
		return (PhoneCsBs) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}

}
