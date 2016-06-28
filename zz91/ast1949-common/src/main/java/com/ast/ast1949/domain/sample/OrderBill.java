package com.ast.ast1949.domain.sample;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBill {
    private Integer id;

    private Integer srcId;

    private Integer sampleId;

    private Integer buyerId;

    private Integer sellerId;

    private String buyerName;

    private String sellerName;

    private Integer number;

    private BigDecimal unitPrice;

    private Float unitWeight;

    private Float weight;

    private BigDecimal trafficFee;

    private BigDecimal amount;

    private BigDecimal channelAmount;

    private BigDecimal virtualAmount;

    private String buyerAcc;

    private String sellerAcc;

    private String tranType;

    private String orderid;

    private String ordernote;

    private Date createTime;

    private Date expireTime;

    private String channelType;

    private Date channelSendTime;

    private Date channelRecvTime;

    private String channelRecvSn;

    private String channelSendSn;

    private String channelRtncode;

    private String channelRtnnote;

    private String state;

    private Date updateTime;

    private String closeReason;

    private String sellerMemo;

    private String buyerMemo;

    private String buyerLeavemsg;

    private Integer buyerAddrId;
    
    private String buyerAddr;

    private Integer sellerAddrId;

    private Integer custominfoId;

    private String snapTitle;

    private String snapUrl;

    private String snapPic;

    private Integer snapProductId;
    
    private String buyerCompanyAccount;

    private Integer isCashDelivery;  // 1:货到付款
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName == null ? null : buyerName.trim();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Float unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public BigDecimal getTrafficFee() {
        return trafficFee;
    }

    public void setTrafficFee(BigDecimal trafficFee) {
        this.trafficFee = trafficFee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getChannelAmount() {
        return channelAmount;
    }

    public void setChannelAmount(BigDecimal channelAmount) {
        this.channelAmount = channelAmount;
    }

    public BigDecimal getVirtualAmount() {
        return virtualAmount;
    }

    public void setVirtualAmount(BigDecimal virtualAmount) {
        this.virtualAmount = virtualAmount;
    }

    public String getBuyerAcc() {
        return buyerAcc;
    }

    public void setBuyerAcc(String buyerAcc) {
        this.buyerAcc = buyerAcc == null ? null : buyerAcc.trim();
    }

    public String getSellerAcc() {
        return sellerAcc;
    }

    public void setSellerAcc(String sellerAcc) {
        this.sellerAcc = sellerAcc == null ? null : sellerAcc.trim();
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType == null ? null : tranType.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getOrdernote() {
        return ordernote;
    }

    public void setOrdernote(String ordernote) {
        this.ordernote = ordernote == null ? null : ordernote.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public Date getChannelSendTime() {
        return channelSendTime;
    }

    public void setChannelSendTime(Date channelSendTime) {
        this.channelSendTime = channelSendTime;
    }

    public Date getChannelRecvTime() {
        return channelRecvTime;
    }

    public void setChannelRecvTime(Date channelRecvTime) {
        this.channelRecvTime = channelRecvTime;
    }

    public String getChannelRecvSn() {
        return channelRecvSn;
    }

    public void setChannelRecvSn(String channelRecvSn) {
        this.channelRecvSn = channelRecvSn == null ? null : channelRecvSn.trim();
    }

    public String getChannelSendSn() {
        return channelSendSn;
    }

    public void setChannelSendSn(String channelSendSn) {
        this.channelSendSn = channelSendSn == null ? null : channelSendSn.trim();
    }

    public String getChannelRtncode() {
        return channelRtncode;
    }

    public void setChannelRtncode(String channelRtncode) {
        this.channelRtncode = channelRtncode == null ? null : channelRtncode.trim();
    }

    public String getChannelRtnnote() {
        return channelRtnnote;
    }

    public void setChannelRtnnote(String channelRtnnote) {
        this.channelRtnnote = channelRtnnote == null ? null : channelRtnnote.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason == null ? null : closeReason.trim();
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo == null ? null : sellerMemo.trim();
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo == null ? null : buyerMemo.trim();
    }

    public String getBuyerLeavemsg() {
        return buyerLeavemsg;
    }

    public void setBuyerLeavemsg(String buyerLeavemsg) {
        this.buyerLeavemsg = buyerLeavemsg == null ? null : buyerLeavemsg.trim();
    }
    
    public String getBuyerAddr() {
		return buyerAddr;
	}

	public void setBuyerAddr(String buyerAddr) {
		this.buyerAddr = buyerAddr;
	}

	public Integer getBuyerAddrId() {
        return buyerAddrId;
    }

    public void setBuyerAddrId(Integer buyerAddrId) {
        this.buyerAddrId = buyerAddrId;
    }

    public Integer getSellerAddrId() {
        return sellerAddrId;
    }

    public void setSellerAddrId(Integer sellerAddrId) {
        this.sellerAddrId = sellerAddrId;
    }

    public Integer getCustominfoId() {
        return custominfoId;
    }

    public void setCustominfoId(Integer custominfoId) {
        this.custominfoId = custominfoId;
    }

    public String getSnapTitle() {
        return snapTitle;
    }

    public void setSnapTitle(String snapTitle) {
        this.snapTitle = snapTitle == null ? null : snapTitle.trim();
    }

    public String getSnapUrl() {
        return snapUrl;
    }

    public void setSnapUrl(String snapUrl) {
        this.snapUrl = snapUrl == null ? null : snapUrl.trim();
    }

    public String getSnapPic() {
        return snapPic;
    }

    public void setSnapPic(String snapPic) {
        this.snapPic = snapPic == null ? null : snapPic.trim();
    }

    public Integer getSnapProductId() {
        return snapProductId;
    }

    public void setSnapProductId(Integer snapProductId) {
        this.snapProductId = snapProductId;
    }

	public String getBuyerCompanyAccount() {
		return buyerCompanyAccount;
	}

	public void setBuyerCompanyAccount(String buyerCompanyAccount) {
		this.buyerCompanyAccount = buyerCompanyAccount;
	}

	public Integer getIsCashDelivery() {
		return isCashDelivery;
	}

	public void setIsCashDelivery(Integer isCashDelivery) {
		this.isCashDelivery = isCashDelivery;
	}
}