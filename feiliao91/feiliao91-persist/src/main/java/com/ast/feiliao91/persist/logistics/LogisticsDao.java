package com.ast.feiliao91.persist.logistics;

import com.ast.feiliao91.domain.logistics.Logistics;

public interface LogisticsDao {
    /**
     * 根据物流单号查询物流详细信息
     */
	public Logistics selectLogisticsByCode(String code);
    /**
     * 插入物流信息
     * @param logistics
     * @return
     */
	public Integer insertLogistics(Logistics logistics);
	/**
     * 根据物流单号更新物流信息
     */
	public Integer updateLogisticsByCode(String code,String logisticsInfo);
}
