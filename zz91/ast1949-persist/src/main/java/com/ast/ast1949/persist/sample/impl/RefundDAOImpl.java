package com.ast.ast1949.persist.sample.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Refund;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.RefundDAO;

@Component("refundDao")
public class RefundDAOImpl extends BaseDaoSupport implements RefundDAO {

	public Integer insert(Refund record) {
		return (Integer)getSqlMapClientTemplate().insert("sample_refund.insert", record);
	}

	public int updateByPrimaryKey(Refund record) {
		int rows = getSqlMapClientTemplate().update("sample_refund.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(Refund record) {
		int rows = getSqlMapClientTemplate().update("sample_refund.updateByPrimaryKeySelective", record);
		return rows;
	}

	public Refund selectByPrimaryKey(Integer id) {
		Refund key = new Refund();
		key.setId(id);
		Refund record = (Refund) getSqlMapClientTemplate().queryForObject("sample_refund.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		Refund key = new Refund();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("sample_refund.deleteByPrimaryKey", key);
		return rows;
	}

	@Override
	public Refund selectByOrderBillId(Integer id) {
		Refund key = new Refund();
		key.setOrderbillId(id);
		Refund record = (Refund) getSqlMapClientTemplate().queryForObject("sample_refund.selectByOrderBillId", key);
		return record;
	}
}