package com.zz91.crm.dto;

import java.io.Serializable;
import com.zz91.crm.domain.CrmContactStatistics;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-4 
 */
public class CrmContactStatisticsDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private CrmContactStatistics statistics;
	private Double ableTotal;//有效总计
	private Double disableTotal;//无效总计
	private Double ableRate;//有效联系率
	private String dateStartEnd;//日期间隔
	private Integer tomContactCount;//明天联系数量
	private Double total;//总联系次数
	
	public CrmContactStatistics getStatistics() {
		return statistics;
	}
	public void setStatistics(CrmContactStatistics statistics) {
		this.statistics = statistics;
	}
	public void setDisableTotal(Double disableTotal) {
		this.disableTotal = disableTotal;
	}
	public Double getDisableTotal() {
		return disableTotal;
	}
	public void setAbleTotal(Double ableTotal) {
		this.ableTotal = ableTotal;
	}
	public Double getAbleTotal() {
		return ableTotal;
	}
	public void setAbleRate(Double ableRate) {
		this.ableRate = ableRate;
	}
	public Double getAbleRate() {
		return ableRate;
	}
	public void setDateStartEnd(String dateStartEnd) {
		this.dateStartEnd = dateStartEnd;
	}
	public String getDateStartEnd() {
		return dateStartEnd;
	}
	public void setTomContactCount(Integer tomContactCount) {
		this.tomContactCount = tomContactCount;
	}
	public Integer getTomContactCount() {
		return tomContactCount;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getTotal() {
		return total;
	}
}
