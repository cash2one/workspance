/**
 * @author shiqp
 * @date 2016-01-30
 */
package com.ast.feiliao91.dto.goods;

import com.ast.feiliao91.domain.DomainSupport;

public class OrdersSearchDto extends DomainSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1985412258645988959L;
	private Integer buyCompanyId; //买家id
	private Integer sellCompanyId; // 卖家id
	
	private Integer countThreeMonth; // 近三月订单
	/**
	 * 状态： 0等待卖家审核、买家已下单;
	 * 1等待付款、 等待买家付款、;
	 * 2等待卖家发货、 买家已付款、;
	 * 3物流运输中,等待买家确认收货、;
	 * 4货物已揽收、等待买家确认收货、;
	 * 50 退款中
	 * 66交易成功、 交易成功;
	 * 99交易关闭
	 */ 
	private Integer status; 
	private Integer hasNoJudge; // 是否有未评价的成功订单
	
	private Integer isBuyRead; // 读消息标志
	private Integer isSellRead; // 读消息标志
	
	private Integer buyIsDel; // 买家删除标志
	private Integer sellIsDel; // 买家删除标志
	
	private String orderNo; //订单编号
	
	//后台搜索栏目
	private String title;//产品名
	private Integer companyType;//公司类型
	private String companyName;//公司名
	private String dateType;// 时间类型
	private String from;//开始时间
	private String to;//结束时间

	public Integer getBuyCompanyId() {
		return buyCompanyId;
	}

	public void setBuyCompanyId(Integer buyCompanyId) {
		this.buyCompanyId = buyCompanyId;
	}

	public Integer getSellCompanyId() {
		return sellCompanyId;
	}

	public void setSellCompanyId(Integer sellCompanyId) {
		this.sellCompanyId = sellCompanyId;
	}

	public Integer getCountThreeMonth() {
		return countThreeMonth;
	}

	public void setCountThreeMonth(Integer countThreeMonth) {
		this.countThreeMonth = countThreeMonth;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsBuyRead() {
		return isBuyRead;
	}

	public void setIsBuyRead(Integer isBuyRead) {
		this.isBuyRead = isBuyRead;
	}

	public Integer getIsSellRead() {
		return isSellRead;
	}

	public void setIsSellRead(Integer isSellRead) {
		this.isSellRead = isSellRead;
	}

	public Integer getHasNoJudge() {
		return hasNoJudge;
	}

	public void setHasNoJudge(Integer hasNoJudge) {
		this.hasNoJudge = hasNoJudge;
	}

	public Integer getBuyIsDel() {
		return buyIsDel;
	}

	public void setBuyIsDel(Integer buyIsDel) {
		this.buyIsDel = buyIsDel;
	}

	public Integer getSellIsDel() {
		return sellIsDel;
	}

	public void setSellIsDel(Integer sellIsDel) {
		this.sellIsDel = sellIsDel;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
