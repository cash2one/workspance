/**
 * @author kongsj
 * @date 2014年11月12日
 * 
 */
package com.ast.ast1949.persist.company.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.PayOrder;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.PayOrderDao;

@Component("payOrderDao")
public class PayOrderDaoImpl extends BaseDaoSupport implements PayOrderDao{

	final static String SQL_FIX = "payOrder";
	
	@Override
	public Integer insert(PayOrder payOrder) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), payOrder);
	}

	@Override
	public PayOrder queryById(Integer id) {
		return (PayOrder) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrder> queryByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}

	@Override
	public PayOrder queryByNoOrder(String noOrder) {
		return (PayOrder) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByNoOrder"), noOrder);
	}

}
