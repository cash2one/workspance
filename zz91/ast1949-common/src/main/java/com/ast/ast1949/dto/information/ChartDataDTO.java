/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-9
 */
package com.ast.ast1949.dto.information;

import java.util.Date;

import com.ast.ast1949.domain.information.ChartDataDO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class ChartDataDTO implements java.io.Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private ChartDataDO chartData;
	private Date gmtDate;
	
	/**
	 * @return the chartData
	 */
	public ChartDataDO getChartData() {
		return chartData;
	}
	/**
	 * @param chartData the chartData to set
	 */
	public void setChartData(ChartDataDO chartData) {
		this.chartData = chartData;
	}
	/**
	 * @return the gmtDate
	 */
	public Date getGmtDate() {
		return gmtDate;
	}
	/**
	 * @param gmtDate the gmtDate to set
	 */
	public void setGmtDate(Date gmtDate) {
		this.gmtDate = gmtDate;
	}
}
