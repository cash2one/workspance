/**
 * 
 */
package com.zz91.top.app.domain;

import java.util.Date;

/**
 * @author mays
 * 
 */
public class TbTrade extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4955666633043627904L;

	/**
	 * 卖家手工调整金额，精确到2位小数，单位：元。如：200.07，表示：200元7分。来源于订单价格修改，如果有多笔子订单的时候，这个为0，
	 * 单笔的话则跟[order].adjust_fee一样
	 */
	private String adjustFee;

	/**
	 * 买家的支付宝id号，在UIC中有记录，买家支付宝的唯一标示，不因为买家更换Email账号而改变。
	 */
	private String alipayId;

	/**
	 * 支付宝交易号，如：2009112081173831
	 */
	private String alipayNo;

	/**
	 * 创建交易接口成功后，返回的支付url
	 */
	private String alipayUrl;

	/**
	 * 淘宝下单成功了,但由于某种错误支付宝订单没有创建时返回的信息。taobao.trade.add接口专用
	 */
	private String alipayWarnMsg;

	/**
	 * 区域id，代表订单下单的区位码，区位码是通过省市区转换而来，通过区位码能精确到区内的划分，比如310012是杭州市西湖区华星路
	 */
	private String areaId;

	/**
	 * 交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零）。精确到2位小数;单位:元。如:200.07，表示:200
	 * 元7分
	 */
	private String availableConfirmFee;

	/**
	 * 买家支付宝账号
	 */
	private String buyerAlipayNo;

	/**
	 * 买家下单的地区
	 */
	private String buyerArea;

	/**
	 * 买家货到付款服务费。精确到2位小数;单位:元。如:12.07，表示:12元7分
	 */
	private String buyerCodFee;

	/**
	 * 买家邮件地址
	 */
	private String buyerEmail;

	/**
	 * 买家备注（与淘宝网上订单的买家备注对应，只有买家才能查看该字段）
	 */
	private String buyerMemo;

	/**
	 * 买家留言
	 */
	private String buyerMessage;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 买家获得积分,返点的积分。格式:100;单位:个。返点的积分要交易成功之后才能获得。
	 */
	private Long buyerObtainPointFee;

	/**
	 * 货到付款物流状态。 初始状态 NEW_CREATED, 接单成功 ACCEPTED_BY_COMPANY, 接单失败
	 * REJECTED_BY_COMPANY, 接单超时 RECIEVE_TIMEOUT, 揽收成功 TAKEN_IN_SUCCESS, 揽收失败
	 * TAKEN_IN_FAILED, 揽收超时 TAKEN_TIMEOUT, 签收成功 SIGN_IN, 签收失败
	 * REJECTED_BY_OTHER_SIDE, 订单等待发送给物流公司 WAITING_TO_BE_SENT, 用户取消物流订单 CANCELED
	 */
	private String codStatus;

	/**
	 * 卖家发货时间。格式:yyyy-MM-dd HH:mm:ss
	 */
	private Date consignTime;

	/**
	 * 交易创建时间。格式:yyyy-MM-dd HH:mm:ss
	 */
	private Date created;

	/**
	 * 使用信用卡支付金额数
	 */
	private String creditCardFee;

	/**
	 * 建议使用trade.promotion_details查询系统优惠
	 * 系统优惠金额（如打折，VIP，满就送等），精确到2位小数，单位：元。如：200.07，表示：200元7分
	 */
	private String discountFee;

	/**
	 * 交易结束时间。交易成功时间(更新交易状态为成功的同时更新)/确认收货时间或者交易关闭时间 。格式:yyyy-MM-dd HH:mm:ss
	 */
	private Date endTime;

	/**
	 * 快递代收款。精确到2位小数;单位:元。如:212.07，表示:212元7分
	 */
	private String expressAgencyFee;

	/**
	 * 是否包含邮费。与available_confirm_fee同时使用。可选值:true(包含),false(不包含)
	 */
	private Boolean hasPostFee;

	/**
	 * 订单出现异常问题的时候，给予用户的描述,没有异常的时候，此值为空
	 */
	private String markDesc;

	/**
	 * 交易修改时间(用户对订单的任何修改都会更新此字段)。格式:yyyy-MM-dd HH:mm:ss
	 */
	private Date modified;

	/**
	 * 商品购买数量。取值范围：大于零的整数,对于一个trade对应多个order的时候（一笔主订单，对应多笔子订单），num=0，
	 * num是一个跟商品关联的属性，一笔订单对应多比子订单的时候，主订单上的num无意义。
	 */
	private Long num;

	/**
	 * 商品数字编号
	 */
	private String numIid;

	/**
	 * 付款时间。格式:yyyy-MM-dd HH:mm:ss。订单的付款时间即为物流订单的创建时间。
	 */
	private Date payTime;

	/**
	 * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String payment;

	/**
	 * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String postFee;

	/**
	 * 商品价格。精确到2位小数；单位：元。如：200.07，表示：200元7分
	 */
	private String price;

	/**
	 * 卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额）。
	 * 精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String receivedPayment;

	/**
	 * 收货人的所在城市<br>
	 * 注：因为国家对于城市和地区的划分的有：省直辖市和省直辖县级行政区（区级别的）划分的，淘宝这边根据这个差异保存在不同字段里面
	 * 比如：广东广州：广州属于一个直辖市是放在的receiver_city的字段里面
	 * ；而河南济源：济源属于省直辖县级行政区划分，是区级别的，放在了receiver_district里面 <br>
	 * 建议：程序依赖于城市字段做物流等判断的操作，最好加一个判断逻辑：如果返回值里面只有receiver_district参数，该参数作为城市
	 */
	private String receiverCity;

	/**
	 * 收货人的所在地区<br>
	 * 注：因为国家对于城市和地区的划分的有：省直辖市和省直辖县级行政区（区级别的）划分的，淘宝这边根据这个差异保存在不同字段里面
	 * 比如：广东广州：广州属于一个直辖市是放在的receiver_city的字段里面
	 * ；而河南济源：济源属于省直辖县级行政区划分，是区级别的，放在了receiver_district里面 <br>
	 * 建议：程序依赖于城市字段做物流等判断的操作，最好加一个判断逻辑：如果返回值里面只有receiver_district参数，该参数作为城市
	 */
	private String receiverDistrict;

	/**
	 * 收货人的手机号码
	 */
	private String receiverMobile;

	/**
	 * 收货人的电话号码
	 */
	private String receiverPhone;

	/**
	 * 卖家支付宝账号
	 */
	private String sellerAlipayNo;

	/**
	 * 卖家货到付款服务费。精确到2位小数;单位:元。如:12.07，表示:12元7分。卖家不承担服务费的订单：未发货的订单获取服务费为0，
	 * 发货后就能获取到正确值。
	 */
	private String sellerCodFee;

	/**
	 * 卖家邮件地址
	 */
	private String sellerEmail;

	/**
	 * 卖家备注（与淘宝网上订单的卖家备注对应，只有卖家才能查看该字段）
	 */
	private String sellerMemo;

	/**
	 * 卖家手机
	 */
	private String sellerMobile;

	/**
	 * 卖家姓名
	 */
	private String sellerName;

	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 卖家电话
	 */
	private String sellerPhone;

	/**
	 * 订单将在此时间前发出，主要用于预售订单
	 */
	private String sendTime;

	/**
	 * 创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。可选值：free(卖家包邮),post(平邮),express
	 * (快递),ems(EMS),virtual(虚拟发货)，25(次日必达)，26(预约配送)。
	 */
	private String shippingType;

	/**
	 * 交易快照地址
	 */
	private String snapshotUrl;

	/**
	 * 交易状态。可选值: TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款)
	 * SELLER_CONSIGNED_PART(卖家部分发货) WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功)
	 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
	 */
	private String status;

	/**
	 * 分阶段付款的已付金额（万人团订单已付金额）
	 */
	private String stepPaidFee;

	/**
	 * 分阶段付款的订单状态（例如万人团订单等），目前有三返回状态
	 * FRONT_NOPAID_FINAL_NOPAID(定金未付尾款未付)，FRONT_PAID_FINAL_NOPAID
	 * (定金已付尾款未付)，FRONT_PAID_FINAL_PAID(定金和尾款都付)
	 */
	private String stepTradeStatus;

	/**
	 * 交易编号 (父订单的交易编号)
	 */
	private String tid;

	/**
	 * 交易标题，以店铺名作为此标题的值。注:taobao.trades.get接口返回的Trade中的title是商品名称
	 */
	private String title;

	/**
	 * 商品金额（商品价格乘以数量的总金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private String totalFee;

	/**
	 * 交易内部来源。 WAP(手机);HITAO(嗨淘);TOP(TOP平台);TAOBAO(普通淘宝);JHS(聚划算)
	 * 一笔订单可能同时有以上多个标记，则以逗号分隔
	 */
	private String tradeFrom;

	/**
	 * 交易类型列表，同时查询多种交易类型可用逗号分隔。默认同时查询guarantee_trade, auto_delivery, ec,
	 * cod的4种交易类型的数据 可选值 fixed(一口价) auction(拍卖) guarantee_trade(一口价、拍卖)
	 * auto_delivery(自动发货) independent_simple_trade(旺店入门版交易)
	 * independent_shop_trade(旺店标准版交易) ec(直冲) cod(货到付款) fenxiao(分销)
	 * game_equipment(游戏装备) shopex_trade(ShopEX交易) netcn_trade(万网交易)
	 * external_trade(统一外部交易) step (万人团) nopaid(无付款订单)
	 */
	private String type;

	public String getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	public String getAlipayUrl() {
		return alipayUrl;
	}

	public void setAlipayUrl(String alipayUrl) {
		this.alipayUrl = alipayUrl;
	}

	public String getAlipayWarnMsg() {
		return alipayWarnMsg;
	}

	public void setAlipayWarnMsg(String alipayWarnMsg) {
		this.alipayWarnMsg = alipayWarnMsg;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAvailableConfirmFee() {
		return availableConfirmFee;
	}

	public void setAvailableConfirmFee(String availableConfirmFee) {
		this.availableConfirmFee = availableConfirmFee;
	}

	public String getBuyerAlipayNo() {
		return buyerAlipayNo;
	}

	public void setBuyerAlipayNo(String buyerAlipayNo) {
		this.buyerAlipayNo = buyerAlipayNo;
	}

	public String getBuyerArea() {
		return buyerArea;
	}

	public void setBuyerArea(String buyerArea) {
		this.buyerArea = buyerArea;
	}

	public String getBuyerCodFee() {
		return buyerCodFee;
	}

	public void setBuyerCodFee(String buyerCodFee) {
		this.buyerCodFee = buyerCodFee;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public Long getBuyerObtainPointFee() {
		return buyerObtainPointFee;
	}

	public void setBuyerObtainPointFee(Long buyerObtainPointFee) {
		this.buyerObtainPointFee = buyerObtainPointFee;
	}

	public String getCodStatus() {
		return codStatus;
	}

	public void setCodStatus(String codStatus) {
		this.codStatus = codStatus;
	}

	public Date getConsignTime() {
		return consignTime;
	}

	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreditCardFee() {
		return creditCardFee;
	}

	public void setCreditCardFee(String creditCardFee) {
		this.creditCardFee = creditCardFee;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getExpressAgencyFee() {
		return expressAgencyFee;
	}

	public void setExpressAgencyFee(String expressAgencyFee) {
		this.expressAgencyFee = expressAgencyFee;
	}

	public Boolean getHasPostFee() {
		return hasPostFee;
	}

	public void setHasPostFee(Boolean hasPostFee) {
		this.hasPostFee = hasPostFee;
	}

	public String getMarkDesc() {
		return markDesc;
	}

	public void setMarkDesc(String markDesc) {
		this.markDesc = markDesc;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReceivedPayment() {
		return receivedPayment;
	}

	public void setReceivedPayment(String receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getSellerAlipayNo() {
		return sellerAlipayNo;
	}

	public void setSellerAlipayNo(String sellerAlipayNo) {
		this.sellerAlipayNo = sellerAlipayNo;
	}

	public String getSellerCodFee() {
		return sellerCodFee;
	}

	public void setSellerCodFee(String sellerCodFee) {
		this.sellerCodFee = sellerCodFee;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

	public String getSellerMobile() {
		return sellerMobile;
	}

	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getSnapshotUrl() {
		return snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStepPaidFee() {
		return stepPaidFee;
	}

	public void setStepPaidFee(String stepPaidFee) {
		this.stepPaidFee = stepPaidFee;
	}

	public String getStepTradeStatus() {
		return stepTradeStatus;
	}

	public void setStepTradeStatus(String stepTradeStatus) {
		this.stepTradeStatus = stepTradeStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeFrom() {
		return tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}
	
	
//	/**
//	 * 买家备注旗帜（与淘宝网上订单的买家备注旗帜对应，只有买家才能查看该字段） 红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
//	 */
//	@ApiField("buyer_flag")
//	private Long buyerFlag;
//	/**
//	 * 买家是否已评价。可选值:true(已评价),false(未评价)。如买家只评价未打分，此字段仍返回false
//	 */
//	@ApiField("buyer_rate")
//	private Boolean buyerRate;
//	/**
//	 * 买家可以通过此字段查询是否当前交易可以评论，can_rate=true可以评价，false则不能评价。
//	 */
//	@ApiField("can_rate")
//	private Boolean canRate;
//
//	/**
//	 * 货到付款服务费。精确到2位小数;单位:元。如:12.07，表示:12元7分。
//	 */
//	@ApiField("cod_fee")
//	private String codFee;
//
//	/**
//	 * 交易佣金。精确到2位小数;单位:元。如:200.07，表示:200元7分
//	 */
//	@ApiField("commission_fee")
//	private String commissionFee;
//
//	/**
//	 * 电子凭证的垂直信息
//	 */
//	@ApiField("eticket_ext")
//	private String eticketExt;
//
//	/**
//	 * 判断订单是否有买家留言，有买家留言返回true，否则返回false
//	 */
//	@ApiField("has_buyer_message")
//	private Boolean hasBuyerMessage;
//
//
//	/**
//	 * 订单中是否包含运费险订单，如果包含运费险订单返回true，不包含运费险订单，返回false
//	 */
//	@ApiField("has_yfx")
//	private Boolean hasYfx;
//
//	/**
//	 * 商品字符串编号(注意：iid近期即将废弃，请用num_iid参数)
//	 */
//	@ApiField("iid")
//	private String iid;
//
//	/**
//	 * 发票抬头
//	 */
//	@ApiField("invoice_name")
//	private String invoiceName;
//
//	/**
//	 * 是否是3D淘宝交易
//	 */
//	@ApiField("is_3D")
//	private Boolean is3D;
//
//	/**
//	 * 表示是否是品牌特卖（常规特卖，不包括特卖惠和特实惠）订单，如果是返回true，如果不是返回false。
//	 * 当此字段与is_force_wlb均为true时，订单强制物流宝发货。
//	 */
//	@ApiField("is_brand_sale")
//	private Boolean isBrandSale;
//
//	/**
//	 * 表示订单交易是否含有对应的代销采购单。 如果该订单中存在一个对应的代销采购单，那么该值为true；反之，该值为false。
//	 */
//	@ApiField("is_daixiao")
//	private Boolean isDaixiao;
//
//	/**
//	 * 订单是否强制使用物流宝发货。当此字段与is_brand_sale均为true时，订单强制物流宝发货。此字段为false时，
//	 * 该订单根据流转规则设置可以使用物流宝或者常规方式发货
//	 */
//	@ApiField("is_force_wlb")
//	private Boolean isForceWlb;
//
//	/**
//	 * 是否保障速递，如果为true，则为保障速递订单，使用线下联系发货接口发货，如果未false，则该订单非保障速递，
//	 * 根据卖家设置的订单流转规则可使用物流宝或者常规物流发货。
//	 */
//	@ApiField("is_lgtype")
//	private Boolean isLgtype;
//
//	/**
//	 * 是否是多次发货的订单 如果卖家对订单进行多次发货，则为true 否则为false
//	 */
//	@ApiField("is_part_consign")
//	private Boolean isPartConsign;
//
//	/**
//	 * 表示订单交易是否网厅订单。 如果该订单是网厅订单，那么该值为true；反之，该值为false。
//	 */
//	@ApiField("is_wt")
//	private Boolean isWt;
//
//	/**
//	 * 次日达订单送达时间
//	 */
//	@ApiField("lg_aging")
//	private String lgAging;
//
//	/**
//	 * 次日达，三日达等送达类型
//	 */
//	@ApiField("lg_aging_type")
//	private String lgAgingType;
//
//	/**
//	 * 卡易售垂直表信息，去除下单ip之后的结果
//	 */
//	@ApiField("nut_feature")
//	private String nutFeature;
//
//	/**
//	 * 订单列表
//	 */
//	@ApiListField("orders")
//	@ApiField("order")
//	private List<Order> orders;
//	/**
//	 * 商品图片绝对途径
//	 */
//	@ApiField("pic_path")
//	private String picPath;
//
//	/**
//	 * 买家使用积分,下单时生成，且一直不变。格式:100;单位:个.
//	 */
//	@ApiField("point_fee")
//	private Long pointFee;
//
//	/**
//	 * 交易促销详细信息
//	 */
//	@ApiField("promotion")
//	private String promotion;
//
//	/**
//	 * 优惠详情
//	 */
//	@ApiListField("promotion_details")
//	@ApiField("promotion_detail")
//	private List<PromotionDetail> promotionDetails;
//
//	/**
//	 * 买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0。格式:100;单位:个
//	 */
//	@ApiField("real_point_fee")
//	private Long realPointFee;
//
//	/**
//	 * 收货人的详细地址
//	 */
//	@ApiField("receiver_address")
//	private String receiverAddress;
//
//	/**
//	 * 收货人的姓名
//	 */
//	@ApiField("receiver_name")
//	private String receiverName;
//
//	/**
//	 * 收货人的所在省份
//	 */
//	@ApiField("receiver_state")
//	private String receiverState;
//
//	/**
//	 * 收货人的邮编
//	 */
//	@ApiField("receiver_zip")
//	private String receiverZip;
//
//	/**
//	 * 卖家是否可以对订单进行评价
//	 */
//	@ApiField("seller_can_rate")
//	private Boolean sellerCanRate;
//
//	/**
//	 * 卖家备注旗帜（与淘宝网上订单的卖家备注旗帜对应，只有卖家才能查看该字段） 红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
//	 */
//	@ApiField("seller_flag")
//	private Long sellerFlag;
//
//	/**
//	 * 卖家是否已评价。可选值:true(已评价),false(未评价)
//	 */
//	@ApiField("seller_rate")
//	private Boolean sellerRate;
//
//	/**
//	 * 服务子订单列表
//	 */
//	@ApiListField("service_orders")
//	@ApiField("service_order")
//	private List<ServiceOrder> serviceOrders;
//
//	/**
//	 * 交易快照详细信息
//	 */
//	@ApiField("snapshot")
//	private String snapshot;
//
//	/**
//	 * 超时到期时间。格式:yyyy-MM-dd HH:mm:ss。业务规则： 前提条件：只有在买家已付款，卖家已发货的情况下才有效
//	 * 如果申请了退款，那么超时会落在子订单上；比如说3笔ABC，A申请了，那么返回的是BC的列表, 主定单不存在
//	 * 如果没有申请过退款，那么超时会挂在主定单上；比如ABC，返回主定单，ABC的超时和主定单相同
//	 */
//	@ApiField("timeout_action_time")
//	private Date timeoutActionTime;
//
//	/**
//	 * 交易备注。
//	 */
//	@ApiField("trade_memo")
//	private String tradeMemo;
//
//	/**
//	 * 交易外部来源：ownshop(商家官网)
//	 */
//	@ApiField("trade_source")
//	private String tradeSource;
//
//	/**
//	 * 订单的运费险，单位为元
//	 */
//	@ApiField("yfx_fee")
//	private String yfxFee;
//
//	/**
//	 * 运费险支付号
//	 */
//	@ApiField("yfx_id")
//	private String yfxId;
//
//	/**
//	 * 运费险类型
//	 */
//	@ApiField("yfx_type")
//	private String yfxType;


}
