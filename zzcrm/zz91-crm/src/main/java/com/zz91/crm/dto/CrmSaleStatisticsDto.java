package com.zz91.crm.dto;

import com.zz91.crm.domain.CrmSaleStatistics;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-5 
 */
public class CrmSaleStatisticsDto {
	
	private CrmSaleStatistics crmSaleStatistics;
	private String dateStartEnd;//时间间隔
	private Integer totals;//合计
	
	public void setCrmSaleStatistics(CrmSaleStatistics crmSaleStatistics) {
		this.crmSaleStatistics = crmSaleStatistics;
	}
	public CrmSaleStatistics getCrmSaleStatistics() {
		return crmSaleStatistics;
	}
	public void setDateStartEnd(String dateStartEnd) {
		this.dateStartEnd = dateStartEnd;
	}
	public String getDateStartEnd() {
		return dateStartEnd;
	}
	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	public Integer getTotals() {
		return totals;
	}
}
