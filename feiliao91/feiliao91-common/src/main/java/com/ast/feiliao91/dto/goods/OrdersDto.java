/**
 * @author shiqp
 * @date 2016-01-30
 */
package com.ast.feiliao91.dto.goods;

import java.util.List;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanyService;
import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.goods.Picture;

import net.sf.json.JSONObject;

public class OrdersDto {

	private Orders orders; // 订单
	private CompanyInfo info; // 买家公司信息
	private CompanyInfo sellCompany;// 卖家公司信息
	private CompanyInfo buyCompany;// 买家公司信息
	private String accountWithAsterisk;// 买家公司账号（成交记录带***号）
	private Goods goods;// 产品信息
	private Address Address;// 收获地址信息
	private Judge judge; // 评价
	private OrderReturn orderReturn; // 退货

	private Float orderTotalPay; // 实际付款
	private Float orderFreightPay; // 实际运费

	private Float jsTotalPay;// 如果没有实际付款 通过计算获取合计付款
	private Float yunfei;// 如果没有实际运费 通过计算获取合计运费

	private List<CompanyService> serlist;// 卖家现有的服务
	private List<Picture> piclist;// 产品图片

	private String format; // 规格
	private String tradeTime;// 交易时间
	private Integer bzjFlag;// 保证金服务
	private Integer sevenDayFlag; // 7天无理由退货服务
	private String picAddress; // 商品封面
	private String cancelReason;// 退货理由
	private String buyAddress; // 买家地址
	private Integer flagPaying;
	
	private Integer orderReturnStatus;//如果有退货,退货后的状态

	private JSONObject detailJson;

	public Orders getOrders() {
		return orders;
	}

	public CompanyInfo getInfo() {
		return info;
	}

	public String getFormat() {
		return format;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public void setInfo(CompanyInfo info) {
		this.info = info;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public CompanyInfo getSellCompany() {
		return sellCompany;
	}

	public void setSellCompany(CompanyInfo sellCompany) {
		this.sellCompany = sellCompany;
	}

	public CompanyInfo getBuyCompany() {
		return buyCompany;
	}

	public void setBuyCompany(CompanyInfo buyCompany) {
		this.buyCompany = buyCompany;
	}

	public String getAccountWithAsterisk() {
		return accountWithAsterisk;
	}

	public void setAccountWithAsterisk(String accountWithAsterisk) {
		this.accountWithAsterisk = accountWithAsterisk;
	}

	public List<CompanyService> getSerlist() {
		return serlist;
	}

	public void setSerlist(List<CompanyService> serlist) {
		this.serlist = serlist;
	}

	public List<Picture> getPiclist() {
		return piclist;
	}

	public void setPiclist(List<Picture> piclist) {
		this.piclist = piclist;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Address getAddress() {
		return Address;
	}

	public void setAddress(Address address) {
		Address = address;
	}

	public Integer getBzjFlag() {
		return bzjFlag;
	}

	public void setBzjFlag(Integer bzjFlag) {
		this.bzjFlag = bzjFlag;
	}

	public Integer getSevenDayFlag() {
		return sevenDayFlag;
	}

	public void setSevenDayFlag(Integer sevenDayFlag) {
		this.sevenDayFlag = sevenDayFlag;
	}

	public String getPicAddress() {
		return picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public OrderReturn getOrderReturn() {
		return orderReturn;
	}

	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Judge getJudge() {
		return judge;
	}

	public void setJudge(Judge judge) {
		this.judge = judge;
	}

	public JSONObject getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(JSONObject detailJson) {
		this.detailJson = detailJson;
	}

	public String getBuyAddress() {
		return buyAddress;
	}

	public void setBuyAddress(String buyAddress) {
		this.buyAddress = buyAddress;
	}

	public Integer getFlagPaying() {
		return flagPaying;
	}

	public void setFlagPaying(Integer flagPaying) {
		this.flagPaying = flagPaying;
	}

	public Float getOrderTotalPay() {
		return orderTotalPay;
	}

	public void setOrderTotalPay(Float orderTotalPay) {
		this.orderTotalPay = orderTotalPay;
	}

	public Float getJsTotalPay() {
		return jsTotalPay;
	}

	public void setJsTotalPay(Float jsTotalPay) {
		this.jsTotalPay = jsTotalPay;
	}

	public Float getOrderFreightPay() {
		return orderFreightPay;
	}

	public void setOrderFreightPay(Float orderFreightPay) {
		this.orderFreightPay = orderFreightPay;
	}

	public Float getYunfei() {
		return yunfei;
	}

	public void setYunfei(Float yunfei) {
		this.yunfei = yunfei;
	}

	public Integer getOrderReturnStatus() {
		return orderReturnStatus;
	}

	public void setOrderReturnStatus(Integer orderReturnStatus) {
		this.orderReturnStatus = orderReturnStatus;
	}
	
}
