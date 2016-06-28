package com.ast.feiliao91.domain.goods;

import java.io.Serializable;
import java.util.Date;

public class OrderReturn implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orderId;// 订单id
	private String orderNo;// 订单编号
	private String logisticsNo;// 退货物流编号
	private Integer status;// 0申请退货待审核1卖家同意退货3退货中66要求再生网介入99退货完成
	private String returnReason;// 退款原因退款原因
	private Float reurnPrice;// 退款金额
	private String returnRemark;// 退款说明
	private String returnPic;// 退款图片凭证
	private String detailAll;//卖家对退货的详细说明
	private String refuseReason;//卖家拒绝退货原因
	private String recodeDetail; //协商记录
	private Integer companyId;
	private Integer targetId;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public Float getReurnPrice() {
		return reurnPrice;
	}

	public void setReurnPrice(Float reurnPrice) {
		this.reurnPrice = reurnPrice;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	public String getReturnPic() {
		return returnPic;
	}

	public void setReturnPic(String returnPic) {
		this.returnPic = returnPic;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	
	public String getDetailAll() {
		return detailAll;
	}

	public void setDetailAll(String detailAll) {
		this.detailAll = detailAll;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
   
	public String getRecodeDetail() {
		return recodeDetail;
	}

	public void setRecodeDetail(String recodeDetail) {
		this.recodeDetail = recodeDetail;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
}
