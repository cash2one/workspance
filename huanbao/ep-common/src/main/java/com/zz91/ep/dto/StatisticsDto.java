/*
 * 文件名称：StatisticsDto.java
 * 创建者　：陈庆林
 * 创建时间：2012-7-6 下午4:41:28
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层[数据操作DAO层\业务逻辑Service层\业务控制Controller层]
 * 模块描述：[对此类的描述，....................
 * 　　　　　.............可以引用系统设计中的描述]
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　陈庆林　　　　　　　1.0.0　　　　　创建类文件
 * 　　　　　2012-10-10　　　黄怀清　　　　　　　1.0.1　　　　　增加审核后供应数量
 */
@SuppressWarnings("serial")
public class StatisticsDto implements Serializable {
	private Integer Member_time; //会员年限
	private Integer altogetherBuyMessage;//总多少条求购信息
	private Integer altogetherSupplyMessage;//共多少条供应信息
	private Integer supplyCheckedTotal;//审核后供应信息数
	
	public StatisticsDto (){
		
	}
	
	public Integer getMember_time() {
		return Member_time;
	}
	public Integer getAltogetherBuyMessage() {
		return altogetherBuyMessage;
	}
	public Integer getAltogetherSupplyMessage() {
		return altogetherSupplyMessage;
	}
	public void setMember_time(Integer member_time) {
		Member_time = member_time;
	}
	public void setAltogetherBuyMessage(Integer altogetherBuyMessage) {
		this.altogetherBuyMessage = altogetherBuyMessage;
	}
	public void setAltogetherSupplyMessage(Integer altogetherSupplyMessage) {
		this.altogetherSupplyMessage = altogetherSupplyMessage;
	}

	public Integer getSupplyCheckedTotal() {
		return supplyCheckedTotal;
	}

	public void setSupplyCheckedTotal(Integer supplyCheckedTotal) {
		this.supplyCheckedTotal = supplyCheckedTotal;
	}
	
	
}
