package com.ast.ast1949.persist.phone.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneLostCus;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneLostCusDao;
@Component("phoneLostCusDao")
public class PhoneLostCusDaoImpl  extends BaseDaoSupport implements PhoneLostCusDao{
	final static String SQL_FIX = "phoneLostCus";
	@Override
    public PhoneLostCus queryPhoneLostCusBycompanyId(Integer companyId){
    	return (PhoneLostCus) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryIdBycompanyId"), companyId);
    }
	@Override
	public Integer deletePhoneLostCusBycompanyId(Integer companyId) {
		
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "deletePhoneLostCusBycompanyId"),companyId);
	}
	
}
