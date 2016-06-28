package com.ast.ast1949.service.sample.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Refund;
import com.ast.ast1949.persist.sample.RefundDAO;
import com.ast.ast1949.service.sample.RefundService;

@Component("refundService")
public class RefundServiceImpl implements RefundService {

	@Resource
	private RefundDAO refundDao;

	@Override
	public Integer insert(Refund refund) {
		return refundDao.insert(refund);
	}

	@Override
	public int updateByPrimaryKey(Refund record) {
		return refundDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Refund record) {
		return refundDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Refund selectByPrimaryKey(Integer id) {
		return refundDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return refundDao.deleteByPrimaryKey(id);
	}

	@Override
	public Refund selectByOrderBillId(Integer id) {
		return refundDao.selectByOrderBillId(id);
	}
}
