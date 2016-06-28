/**
 * @author kongsj
 * @date 2014年11月12日
 * 
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.PayOrder;

public interface PayOrderDao {
	public Integer insert(PayOrder payOrder);

	public PayOrder queryById(Integer id);

	public List<PayOrder> queryByCompanyId(Integer companyId);

	public PayOrder queryByNoOrder(String noOrder);
}
