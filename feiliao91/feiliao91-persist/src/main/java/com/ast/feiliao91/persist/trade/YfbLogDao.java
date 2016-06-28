package com.ast.feiliao91.persist.trade;

import java.util.List;

import com.ast.feiliao91.domain.trade.YfbLog;

public interface YfbLogDao {
	/**
	 * 添加易付宝流水账号
	 */
	public Integer insert(YfbLog yfbLog);
	/**
	 * 根据公司id查询所有流水订单
	 * @param companyId
	 * @return
	 */
	public List<YfbLog> queryByCompanyId(Integer companyId);
	/**
	 * 根据payOrder删除流水订单
	 * @param payOrder
	 * @return
	 */
	public Integer update(String payOrder);
}
