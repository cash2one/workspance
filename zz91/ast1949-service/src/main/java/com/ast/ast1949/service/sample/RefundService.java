package com.ast.ast1949.service.sample;

import com.ast.ast1949.domain.sample.Refund;

public interface RefundService {
	Integer insert(Refund record);

	int updateByPrimaryKey(Refund record);

	int updateByPrimaryKeySelective(Refund record);

	Refund selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	Refund selectByOrderBillId(Integer id);
}
