package com.ast.feiliao91.service.trade;

import java.util.List;

import com.ast.feiliao91.domain.trade.YfbLog;

public interface YfbLogService {
		/**
		 * 添加记录流水信息
		 */
	public Integer insert(YfbLog yfbLog);
	/**
	 * 根据公司id查询所有的YFB订单记录
	 * @param companyId
	 * @return
	 */
	public List<YfbLog> queryByCompanyId(Integer companyId);
	/**
	 * 根据payOrder删除订单记录
	 */
	public Integer update(String payOrder);
}
