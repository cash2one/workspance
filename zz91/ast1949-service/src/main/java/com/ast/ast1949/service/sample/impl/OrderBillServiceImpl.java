package com.ast.ast1949.service.sample.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.sample.Account;
import com.ast.ast1949.domain.sample.Accountseq;
import com.ast.ast1949.domain.sample.OrderBill;
import com.ast.ast1949.domain.sample.Refund;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.domain.sample.SampleMsgset;
import com.ast.ast1949.domain.sample.WeixinScore;
import com.ast.ast1949.domain.sample.WeixinScoresall;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.paychannel.AmountUtils;
import com.ast.ast1949.paychannel.ChannelConst;
import com.ast.ast1949.paychannel.OrderDto;
import com.ast.ast1949.paychannel.OrderState;
import com.ast.ast1949.paychannel.ReturnState;
import com.ast.ast1949.paychannel.TransType;
import com.ast.ast1949.paychannel.exception.BizException;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.sample.AccountDAO;
import com.ast.ast1949.persist.sample.AccountseqDAO;
import com.ast.ast1949.persist.sample.OrderBillDAO;
import com.ast.ast1949.persist.sample.RefundDAO;
import com.ast.ast1949.persist.sample.SampleDao;
import com.ast.ast1949.persist.sample.SampleMsgsetDao;
import com.ast.ast1949.persist.sample.WeixinScoreDao;
import com.ast.ast1949.persist.sample.WeixinScoresallDao;
import com.ast.ast1949.service.sample.OrderBillService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.sms.SmsUtil;

@Component("orderBillService")
public class OrderBillServiceImpl implements OrderBillService {
	private static Logger log = Logger.getLogger(OrderBillServiceImpl.class);

	@Resource
	private OrderBillDAO orderBillDao;

	@Resource
	private AccountDAO accountDao;

	@Resource
	private AccountseqDAO accountseqDao;

	@Resource
	private RefundDAO refundDao;

	@Resource
	private SampleDao sampleDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private SampleMsgsetDao sampleMsgsetDao;
	
	@Resource
	private WeixinScoreDao weixinScoreDao;
	
	@Resource
	private WeixinScoresallDao weixinScoresallDao;

	@Override
	public Integer insert(OrderBill orderBill) {
		return orderBillDao.insert(orderBill);
	}

	@Override
	public int updateByPrimaryKey(OrderBill record) {
		return orderBillDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderBill record) {
		return orderBillDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderBill selectByPrimaryKey(Integer id) {
		return orderBillDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return orderBillDao.deleteByPrimaryKey(id);
	}

	@Override
	public void saveOrderBill(OrderBill orderBill) {
		// TODO Auto-generated method stub

	}

	@Override
	public int dealCallback(ReturnState state) throws BizException {
		/**
		 * 订单修改交易状态
		 */
		String errStr = "";
		OrderBill bill = orderBillDao.selectByOrderSeq(state.getOrderSeq());
		if (bill == null) {
			errStr = "原订单不存在[订单流水:" + state.getOrderSeq() + "]";
			log.info(errStr);
			throw new BizException(errStr);
		}

		if (state.getChannelRetCode().equals("0000")) {
			if (bill.getState().equals(OrderState.STATE_00)
					|| bill.getState().equals(OrderState.STATE_01)) {
				// 如果金额不相等
				if (AmountUtils.ne(state.getRelTranAmount(), bill
						.getChannelAmount().doubleValue())
						|| AmountUtils
								.ne(AmountUtils.add(state.getRelTranAmount(),
										bill.getVirtualAmount().doubleValue()),
										bill.getAmount().doubleValue())) {
					String log_Msg = "渠道返回成功，原交易成功，金额被修改.原交易成功金额被修改,等对账完后做退款处理，订单号="
							+ bill.getOrderid();
					log.info(log_Msg);
					return -1;
				}
			} else if (bill.getState().equals(OrderState.STATE_20)) {
				String log_Msg = "渠道返回成功，原交易关闭.等对账完后做退款处理 .订单号="
						+ bill.getOrderid();
				log.info(log_Msg);
				return -1;
			} else if (bill.getState().equals(OrderState.STATE_03)
					|| OrderState.STATE_06.equals(bill.getState())) {
				String log_Msg = "渠道返回成功，原交易成功,等对账完后做退款处理.订单号="
						+ bill.getOrderid();
				log.info(log_Msg);
				return -1;
			}

		}

		updateOrderBill(state, bill);

		if (bill.getState().equals(OrderState.STATE_04)) {
			orderBillDao.updateByPrimaryKey(bill);
			return -1;
		}

		/**
		 * 增加到-- 平台内部资金虚拟账户
		 */
		Account acc = accountDao
				.selectByPrimaryKey(ChannelConst.INNER_ACCOUNT_ID);
		if (acc == null) {
			acc = new Account(ChannelConst.INNER_ACCOUNT_ID, new BigDecimal(0),
					"00", new Date(), new Date());
			accountDao.insert(acc);
		}

		/**
		 * 内部资金虚拟账户 金额++
		 */
		increaseCustBalance(acc, bill.getTranType(), bill.getChannelAmount()
				.doubleValue(), bill.getId(), bill.getOrderid(),
				"平台账户--支付渠道--来账--支付成功");
		increaseCustBalance(acc, bill.getTranType(), bill.getVirtualAmount()
				.doubleValue(), bill.getId(), bill.getOrderid(),
				"平台账户--虚拟账户--来账--支付成功");

		/**
		 * 虚拟金额 增/减
		 */
		// 买家++
		Account acc2 = accountDao.selectByCompanyId(bill.getBuyerId());
		if (acc2 != null
				&& acc2.getAmount().compareTo(bill.getVirtualAmount()) == -1) {
			throw new BizException("客户的账户的金额不足");
		}
		decreaseCustBalance(acc2, bill.getTranType(), bill.getVirtualAmount()
				.doubleValue(), bill.getId(), bill.getOrderid(),
				"客户账户--虚拟账户--往账--支付成功");

		/**
		 * 更新orderBill
		 */
		orderBillDao.updateByPrimaryKey(bill);

		/**
		 * 发送短信信息
		 */
		String str = "已付款，请您尽快安排发货";

		CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(bill.getSellerId());
		do {
			if (ca==null) {
				break;
			}
			
			SampleMsgset msgset = sampleMsgsetDao.queryByCompanyId(bill.getSellerId());
			if (msgset == null || msgset.getSms()==0){
				if (StringUtils.isNotEmpty(ca.getMobile())) {
					String orderTitle="";
					if(bill!=null&&StringUtils.isNotEmpty(bill.getSnapTitle())){
						if (bill.getSnapTitle().length()<8) {
							 orderTitle = bill.getSnapTitle();
							
						}else {
							 orderTitle = bill.getSnapTitle().substring(0, 8);
						}
					}
					String orderId = bill.getOrderid();
					CompanyAccount caBuy = companyAccountDao.queryAccountByCompanyId(bill.getBuyerId());
					String contact = caBuy.getContact();
					// 发送短信
					// s1.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已付款，请您尽快安排发货
					// 您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
					SmsUtil.getInstance().sendSms("sms_sample_seller",ca.getMobile(), null, null,new String[] { contact, orderTitle, orderId, str });
				}
			}

			/**
			 * 发送邮件提醒信息 link-s1
			 */
			if (msgset == null || msgset.getEmail()==0){
				if (StringUtils.isNotEmpty(ca.getUseEmail())) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("bill", bill);
					paramMap.put("createTime", DateUtil.toString(bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					paramMap.put("stateName", "买家已付款，等待卖家发货");
					paramMap.put("stateAndDeal", "买家已付款，请您尽快安排发货");
					MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒",ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap,MailUtil.PRIORITY_HEIGHT);
				}
			}
		} while (false);
		
		/**
		 * 付款成功，增加积分,  增加客户积分总额 
		 */
		CompanyAccount caBuy = companyAccountDao.queryAccountByCompanyId(bill.getBuyerId());
		this.increaseScore(caBuy);
		
		return 0;
	}

	/**
	 * 
	 * 付款成功，增加积分,  增加客户积分总额 
	 * @param ca
	 */
	public  void increaseScore(CompanyAccount ca) {
		WeixinScore ws = new WeixinScore();
		ws.setAccount(ca.getAccount());
		ws.setRulesCode(ChannelConst.ORDER_SCORE_CODE);
		ws.setScore(ChannelConst.ORDER_SCORE_NUM);
		ws.setValidity(DateUtil.getDateAfterDays(new Date(), ChannelConst.ORDER_SCORE_VALIDITY));
		weixinScoreDao.insert(ws);

		WeixinScoresall wss = weixinScoresallDao.selectByAccount(ca.getAccount());
		if(wss == null ){
			wss = new WeixinScoresall();
			wss.setAccount(ca.getAccount());
			wss.setScore(ChannelConst.ORDER_SCORE_NUM);
			weixinScoresallDao.insert(wss);
		}else{
			wss.setScore(wss.getScore()+ChannelConst.ORDER_SCORE_NUM);
			weixinScoresallDao.updateByPrimaryKeySelective(wss);
		}
	}
	

	/**
	 * 账户基本减函数
	 * 
	 * @param acc
	 *            账户
	 * @param changeType
	 *            业务类型
	 * @param amount
	 *            变动金额
	 * @param tranSn
	 *            对应业务ID
	 * @param seqflag
	 *            金额来往标识
	 * @param orderId
	 *            订单交易业务时必填
	 * @param note
	 *            备注
	 * @throws BizException
	 */
	public Accountseq decreaseCustBalance(Account acc, String changeType,
			double amount, Integer tranSn, String orderId, String note)
			throws BizException {
		Accountseq accseq = new Accountseq();
		accseq.setAccountId(acc.getId());
		accseq.setChangeType(changeType);
		accseq.setCreateTime(new Date());
		accseq.setNote(note);
		accseq.setPreamount(acc.getAmount());// 变动前
		accseq.setAmount(acc.getAmount().subtract(new BigDecimal(amount)));// 变动后
		accseq.setChangeAmount(new BigDecimal(amount));
		accseq.setRefsn(tranSn);
		accseq.setSeqflag("1");// 往账
		accseq.setOrderid(orderId);
		accountseqDao.insert(accseq);

		// 修改账户余额
		acc.setAmount(accseq.getAmount());
		accountDao.updateByPrimaryKey(acc);

		return accseq;
	}

	/**
	 * 账户基本增函数
	 * 
	 * @param acc
	 *            账户
	 * @param changeType
	 *            业务类型
	 * @param amount
	 *            变动金额
	 * @param tranSn
	 *            对应业务ID
	 * @param seqflag
	 *            金额来往标识
	 * @param orderId
	 *            订单交易业务时必填
	 * @param note
	 *            备注
	 * @throws BizException
	 */
	public Accountseq increaseCustBalance(Account acc, String changeType,
			double amount, Integer tranSn, String orderId, String note)
			throws BizException {
		Accountseq accseq = new Accountseq();
		accseq.setAccountId(acc.getId());
		accseq.setChangeType(changeType);
		accseq.setCreateTime(new Date());
		accseq.setNote(note);
		accseq.setPreamount(acc.getAmount());// 变动前
		accseq.setAmount(acc.getAmount().add(new BigDecimal(amount)));// 变动后
		accseq.setChangeAmount(new BigDecimal(amount));
		accseq.setRefsn(tranSn);
		accseq.setSeqflag("0");// 来账
		accseq.setOrderid(orderId);
		accountseqDao.insert(accseq);

		// 修改账户余额
		acc.setAmount(accseq.getAmount());
		accountDao.updateByPrimaryKey(acc);

		return accseq;
	}

	/**
	 * 
	 * 检查及异常处理
	 * 
	 * @param returnState
	 * @param bill
	 * @return
	 * @throws BizException
	 */
	public static OrderBill updateOrderBill(ReturnState returnState,
			OrderBill bill) throws BizException {
		String errStr = "";
		bill.setChannelRecvTime(new Date());
		bill.setChannelRecvSn(returnState.getChannelRecvSn());
		bill.setChannelRtncode(returnState.getChannelRetCode());
		bill.setChannelRtnnote(returnState.getChannelRetMsg());
		bill.setBuyerAcc(returnState.getBuyerAcc());
		bill.setSellerAcc(returnState.getSellerAcc());

		// 要检查状态，如果已经超时关闭(失败)，则不能修改为成功。
		if (!bill.getState().equals(OrderState.STATE_01)
				&& !bill.getState().equals(OrderState.STATE_00))
			throw new BizException("原支付交易不是支付中,忽略渠道的回执[流水号="
					+ returnState.getOrderSeq() + "]");

		if (returnState.getChannelRetCode().equals("0000")) {
			// 如果支付成功则判断金额是否一致
			if (!AmountUtils.et(bill.getChannelAmount().doubleValue(),
					returnState.getRelTranAmount())) {
				// 如果不一致暂时全部抛异常，以后做退款处理
				errStr = "原交易金额不一致，渠道返回金额[" + returnState.getRelTranAmount()
						+ "]原交易渠道金额[" + bill.getChannelAmount() + "][交易流水:"
						+ returnState.getOrderSeq() + "]";
				log.info(errStr);
				throw new BizException(errStr);
			}
			bill.setState(OrderState.STATE_03);
			bill.setUpdateTime(new Date());
		} else {
			bill.setState(OrderState.STATE_04);
			bill.setUpdateTime(new Date());
		}
		return bill;
	}

	@Override
	public void confirmReceiveGoodsBybuyer(OrderDto dto, String confirmPasswd)
			throws BizException {
		OrderBill bill = dto.getOrderBill();
		if (OrderState.STATE_11.equals(bill.getState())) {

			// 确认收货
			bill.setState(OrderState.STATE_12);
			bill.setUpdateTime(new Date());
			orderBillDao.updateByPrimaryKey(bill);

			/**
			 * 买家/卖家 记账处理
			 */
			try {

				/**
				 * 平台内部资金虚拟账户
				 */
				Account acc = accountDao
						.selectByPrimaryKey(ChannelConst.INNER_ACCOUNT_ID);
				decreaseCustBalance(acc, bill.getTranType(), bill.getAmount()
						.doubleValue(), bill.getId(), bill.getOrderid(),
						"平台账户--虚拟户--往账--交易成功");

				// 卖家
				Account acc2 = accountDao.selectByCompanyId(bill.getSellerId());
				if (acc2 == null) {
					acc2 = new Account(bill.getSellerId(), new BigDecimal(0),
							"00", new Date());
					acc2.setLastupdateTime(new Date());
					accountDao.insert(acc2);

					acc2 = accountDao.selectByCompanyId(bill.getSellerId());// 获得ID
				}

				increaseCustBalance(acc2, bill.getTranType(), bill.getAmount()
						.doubleValue(), bill.getId(), bill.getOrderid(),
						"客户账户--虚拟户--来账--交易成功");

			} catch (BizException e) {
				e.printStackTrace();
			}

			// 交易成功
			bill.setState(OrderState.STATE_13);
			bill.setUpdateTime(new Date());
			orderBillDao.updateByPrimaryKey(bill);

			/**
			 * 发送短信信息
			 */
			String str = "已确认收货，交易成功";

			CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(bill.getSellerId());
			SampleMsgset sampleMsgset = sampleMsgsetDao.queryByCompanyId(bill.getSellerId());
			do {
				if (ca==null) {
					break;
				}
				
			if (sampleMsgset == null || sampleMsgset.getSms()==0){
				if (StringUtils.isNotEmpty(ca.getMobile())) {
					String orderTitle="";
					if(bill!=null&&StringUtils.isNotEmpty(bill.getSnapTitle())){
						if (bill.getSnapTitle().length()<8) {
							 orderTitle = bill.getSnapTitle();
							
						}else {
							 orderTitle = bill.getSnapTitle().substring(0, 8);
						}
					}
					String orderId = bill.getOrderid();
					CompanyAccount caBuy = companyAccountDao.queryAccountByCompanyId(bill.getBuyerId());
					String contact = caBuy.getContact();
					// 发送短信
					// s4.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已确认收货，交易成功
					// 您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
					SmsUtil.getInstance().sendSms("sms_sample_seller",ca.getMobile(), null, null,new String[] { contact, orderTitle, orderId, str });
				}
			}
			/**
			 * 发送邮件提醒信息 link-s4
			 */
			if (sampleMsgset == null || sampleMsgset.getEmail()==0){
				if (StringUtils.isNotEmpty(ca.getUseEmail())) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("bill", bill);
					paramMap.put("createTime", DateUtil.toString(bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					paramMap.put("stateName", "交易成功");
					paramMap.put("stateAndDeal", "买家已确认收货，交易成功");
					MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒",ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap,MailUtil.PRIORITY_HEIGHT);
				}
			}
			
			} while (false);

		} else {
			throw new BizException("当前订单状态不可做此操作！");
		}
	}

	@Override
	public void confirmRefundGoodRecvByseller(Refund refund,
			String confirmPasswd) throws BizException {
		if (OrderState.STATE_16.equals(refund.getState())) {
			// 确认收货
			refund.setState(OrderState.STATE_17);
			refundDao.updateByPrimaryKey(refund);

			try {
				OrderBill bill = orderBillDao.selectByPrimaryKey(refund
						.getOrderbillId());

				/**
				 * 平台内部资金虚拟账户 --
				 */
				Account acc = accountDao
						.selectByPrimaryKey(ChannelConst.INNER_ACCOUNT_ID);
				decreaseCustBalance(acc, TransType.Direct_Refund, bill
						.getAmount().doubleValue(), bill.getId(),
						bill.getOrderid(), "平台账户--虚拟户--往账--退货交易");

				// 买家++
				Account acc2 = accountDao.selectByCompanyId(bill.getBuyerId());
				if (acc2 == null) {
					acc2 = new Account(bill.getSellerId(), new BigDecimal(0),
							"00", new Date());
					acc2.setLastupdateTime(new Date());
					accountDao.insert(acc2);

					acc2 = accountDao.selectByCompanyId(bill.getBuyerId());
				}

				increaseCustBalance(acc2, TransType.Direct_Refund, bill
						.getAmount().doubleValue(), bill.getId(),
						bill.getOrderid(), "客户账户--虚拟户--来账--退货交易");

				/**
				 * update
				 */
				bill.setState(OrderState.STATE_17);
				bill.setUpdateTime(new Date());
				orderBillDao.updateByPrimaryKey(bill);

			} catch (BizException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 没有收到货的情况 --卖家同意退货 后将存在账务处理
	 */
	@Override
	public void confirmRefundNoGoodByseller(Refund refund) {
		if (OrderState.STATE_14.equals(refund.getState())) {

			refund.setState(OrderState.STATE_17);
			refundDao.updateByPrimaryKey(refund);

			try {
				OrderBill bill = orderBillDao.selectByPrimaryKey(refund
						.getOrderbillId());

				/**
				 * 平台内部资金虚拟账户 --
				 */
				Account acc = accountDao
						.selectByPrimaryKey(ChannelConst.INNER_ACCOUNT_ID);
				decreaseCustBalance(acc, TransType.Direct_Refund, bill
						.getAmount().doubleValue(), bill.getId(),
						bill.getOrderid(), "平台账户--虚拟户--往账--退款交易");

				// 买家++
				Account acc2 = accountDao.selectByCompanyId(bill.getBuyerId());
				increaseCustBalance(acc2, TransType.Direct_Refund, bill
						.getAmount().doubleValue(), bill.getId(),
						bill.getOrderid(), "客户账户--虚拟户--来账--退款交易");

			} catch (BizException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void dealTradeNotChannel(OrderBill bill) throws BizException {
		if (bill.getChannelAmount().compareTo(new BigDecimal(0)) == 0
				&& bill.getVirtualAmount().compareTo(new BigDecimal(0)) == 1) {
			bill.setState(OrderState.STATE_03);
			bill.setUpdateTime(new Date());

			/**
			 * 增加到-- 平台内部资金虚拟账户
			 */
			Account acc = accountDao
					.selectByPrimaryKey(ChannelConst.INNER_ACCOUNT_ID);
			if (acc == null) {
				acc = new Account(ChannelConst.INNER_ACCOUNT_ID,
						new BigDecimal(0), "00", new Date(), new Date());
				accountDao.insert(acc);
			}

			/**
			 * 内部资金虚拟账户 金额++
			 */
			increaseCustBalance(acc, bill.getTranType(), bill
					.getVirtualAmount().doubleValue(), bill.getId(),
					bill.getOrderid(), "平台账户--虚拟账户--来账--支付成功");

			/**
			 * 虚拟金额 增/减
			 */
			// 买家++
			Account acc2 = accountDao.selectByCompanyId(bill.getBuyerId());
			if (acc2 != null
					&& acc2.getAmount().compareTo(bill.getVirtualAmount()) == -1) {
				throw new BizException("客户的账户的金额不足");
			}
			decreaseCustBalance(acc2, bill.getTranType(), bill
					.getVirtualAmount().doubleValue(), bill.getId(),
					bill.getOrderid(), "客户账户--虚拟账户--往账--支付成功");

			/**
			 * 更新orderBill
			 */
			orderBillDao.updateByPrimaryKey(bill);

			/**
			 * 发送短信信息
			 */
			String str = "已付款，请您尽快安排发货";

			CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(bill.getSellerId());
			do {
				if (ca==null) {
					break;
				}
				
				// 发短信
				SampleMsgset sampleMsgset = sampleMsgsetDao.queryByCompanyId(bill.getSellerId());
				
				if (sampleMsgset == null || 0==sampleMsgset.getSms()){
					if (StringUtils.isNotEmpty(ca.getMobile())) {
						String orderTitle="";
						if(bill!=null&&StringUtils.isNotEmpty(bill.getSnapTitle())){
							if (bill.getSnapTitle().length()<8) {
								 orderTitle = bill.getSnapTitle();
								
							}else {
								 orderTitle = bill.getSnapTitle().substring(0, 8);
							}
						}
						
						String orderId = bill.getOrderid();
						CompanyAccount caBuy = companyAccountDao.queryAccountByCompanyId(bill.getBuyerId());
						String contact = caBuy.getContact();
						// 发送短信
						// s1.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已付款，请您尽快安排发货
						// 您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
						SmsUtil.getInstance().sendSms("sms_sample_seller",ca.getMobile(), null, null,new String[] { contact, orderTitle, orderId, str });
					}
				}
			
				// 发送邮件提醒信息 
				if (sampleMsgset == null || 0==sampleMsgset.getEmail()){
					if (StringUtils.isNotEmpty(ca.getUseEmail())) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("bill", bill);
						paramMap.put("createTime", DateUtil.toString(bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						paramMap.put("stateName", "买家已付款，等待卖家发货");
						paramMap.put("stateAndDeal", "买家已付款，请您尽快安排发货");
						MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒",ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap,MailUtil.PRIORITY_HEIGHT);
					}
				}
			
			} while (false);
			/**
			 * 付款成功，增加积分  增加积分总额 
			 */
			CompanyAccount caBuy = companyAccountDao.queryAccountByCompanyId(bill.getBuyerId());
			this.increaseScore(caBuy);
		}
	}

	@Override
	public OrderBill selectByOrderSeq(String orderSeq) {
		return orderBillDao.selectByOrderSeq(orderSeq);
	}

	@Override
	public void closeOrderByseller(OrderBill bill, String closeInfo)
			throws BizException {
		if (OrderState.STATE_03.equals(bill.getState())
				|| OrderState.STATE_06.equals(bill.getState())) {
			bill.setState(OrderState.STATE_20);
			bill.setUpdateTime(new Date());
			bill.setCloseReason(closeInfo);
			orderBillDao.updateByPrimaryKey(bill);

			// 库存的增加
			Sample sample = sampleDao.queryById(bill.getSampleId());
			sample.setAmount(sample.getAmount() + bill.getNumber());
			sampleDao.update(sample);

			/**
			 * 平台内部资金虚拟账户 --
			 */
			Account acc = accountDao
					.selectByPrimaryKey(ChannelConst.INNER_ACCOUNT_ID);
			decreaseCustBalance(acc, TransType.Direct_Refund, bill.getAmount()
					.doubleValue(), bill.getId(), bill.getOrderid(),
					"平台账户--虚拟户--往账--关闭交易");

			// 买家++
			Account acc2 = accountDao.selectByCompanyId(bill.getBuyerId());
			if (acc2 == null) {
				acc2 = new Account(bill.getSellerId(), new BigDecimal(0), "00",
						new Date());
				acc2.setLastupdateTime(new Date());
				accountDao.insert(acc2);

				acc2 = accountDao.selectByCompanyId(bill.getBuyerId());
			}
			increaseCustBalance(acc2, TransType.Direct_Refund, bill.getAmount()
					.doubleValue(), bill.getId(), bill.getOrderid(),
					"客户账户--虚拟户--来账--关闭交易");

		} else {
			throw new BizException("当前订单状态不可做此操作！");
		}

	}

	@Override
	public void applayRefundBybuyer(OrderBill bill, Integer companyId,
			String refundAmount, String refundType, String isflag,
			String refundReson, String refundDes, Integer refundNum)
			throws BizException {
		/**
		 * 只有 (卖方确认发货) 状态才能申请退货
		 */
		if (OrderState.STATE_11.equals(bill.getState())) {
			Refund rb = new Refund();
			rb.setCompanyId(companyId);
			rb.setOrderbillId(bill.getId());
			rb.setOrderId(bill.getOrderid());
			rb.setState(OrderState.STATE_14);
			rb.setRefundAmount(new BigDecimal(refundAmount));
			rb.setRefundType(refundType);
			rb.setIsflag(isflag);
			rb.setRefundReson(refundReson);
			rb.setRefundDes(refundDes);
			rb.setRefundNum(refundNum);
			rb.setCreateTime(new Date());
			rb.setUpdateTime(new Date());
			refundDao.insert(rb);

			/**
			 * 更新订单状态
			 */
			bill.setState(OrderState.STATE_14);
			bill.setUpdateTime(new Date());
			orderBillDao.updateByPrimaryKey(bill);

		} else {
			throw new BizException("此状态订单不能做退货操作！");
		}

	}

	@Override
	public void agreeRefundByseller(OrderBill bill, Refund refund,
			String isAgree, Integer addressId) throws BizException {
		if (OrderState.STATE_14.equals(refund.getState())) {
			/**
			 * 同意退货
			 */
			if ("0".equalsIgnoreCase(isAgree)) {
				if ("0".equals(refund.getIsflag())) {
					// 没有收到货的情况 --卖家同意退货 后将存在账务处理
					this.confirmRefundNoGoodByseller(refund);

					bill.setState(OrderState.STATE_17);
					bill.setUpdateTime(new Date());
					orderBillDao.updateByPrimaryKey(bill);
				} else {
					// 收到货的情况
					refund.setState(OrderState.STATE_15);
					refund.setRefundAddrId(addressId);
					refundDao.updateByPrimaryKey(refund);

					bill.setState(OrderState.STATE_15);
					bill.setUpdateTime(new Date());
					orderBillDao.updateByPrimaryKey(bill);
				}

				/**
				 * 发送短信信息
				 */
				String str = "已同意退货，请注意及时发货";

				CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(bill.getBuyerId());
				do {
					if (ca==null) {
						break;
					}
					SampleMsgset sampleMsgset = sampleMsgsetDao.queryByCompanyId(bill.getBuyerId());
					// 发短信
					if (sampleMsgset == null || 0==sampleMsgset.getSms()){
						if (StringUtils.isNotEmpty(ca.getMobile())) {
							String orderTitle="";
							if(bill!=null&&StringUtils.isNotEmpty(bill.getSnapTitle())){
								if (bill.getSnapTitle().length()<8) {
									 orderTitle = bill.getSnapTitle();
									
								}else {
									 orderTitle = bill.getSnapTitle().substring(0, 8);
								}
							}
							String orderId = bill.getOrderid();
							// 发送短信
							// 4.您在ZZ91再生网的样品订单（PP再生颗粒，订单号：14072314124676）卖家已同意退货，请注意及时发货
							SmsUtil.getInstance().sendSms("sms_sample_buyer",
									ca.getMobile(), null, null,
									new String[] { orderTitle, orderId, str });
						}
					}
	
					// 发送邮件提醒信息 link-b4
					if (sampleMsgset == null || 0==sampleMsgset.getEmail()){
						if (ca != null && StringUtils.isNotEmpty(ca.getUseEmail())) {
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("bill", bill);
							paramMap.put("createTime", DateUtil.toString(bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
							paramMap.put("stateName", "卖家同意退货");
							paramMap.put("stateAndDeal", "卖家已同意退货，请注意及时发货");
							MailUtil.getInstance().sendMail(null, null,"ZZ91样品交易订单提醒", ca.getUseEmail(), null,new Date(), "zz91", "sample_order_buyer",paramMap, MailUtil.PRIORITY_HEIGHT);
						}
					}
				} while (false);

				/**
				 * 不同意退货
				 */
			} else {
				refund.setState(OrderState.STATE_20);
				refundDao.updateByPrimaryKey(refund);
				// TODO
				bill.setState(OrderState.STATE_11); // 回退到之前的状态
				bill.setUpdateTime(new Date());
				orderBillDao.updateByPrimaryKey(bill);
			}
		} else {
			throw new BizException("此状态订单不能做此操作！");
		}

	}

	@Override
	public void refundGoodSentBybuyer(OrderBill bill, Refund refund)
			throws BizException {
		if (OrderState.STATE_15.equals(refund.getState())) {
			refund.setState(OrderState.STATE_16);
			refundDao.updateByPrimaryKey(refund);
		}
		if (OrderState.STATE_15.equals(bill.getState())) {
			bill.setState(OrderState.STATE_16);
			bill.setUpdateTime(new Date());
			orderBillDao.updateByPrimaryKey(bill);
		}
	}

	@Override
	public PageDto<OrderBill> queryBuyListByCompanyId(Integer companyId,
			String state, PageDto<OrderBill> page, String from, String to,
			String keyword) {
		if (page.getSort() == null) {
			page.setSort("id");
		}
		page.setTotalRecords(orderBillDao.queryBuyListByCompanyIdCount(
				companyId, state, from, to, keyword,null));
		page.setRecords(orderBillDao.queryBuyListByCompanyId(companyId, state,
				page, from, to, keyword,null));
		return page;
	}

	@Override
	public PageDto<OrderBill> querySellListByCompanyId(Integer companyId,
			String state, PageDto<OrderBill> page, String from, String to,
			String keyword) {
		if (page.getSort() == null) {
			page.setSort("id");
		}
		page.setTotalRecords(orderBillDao.querySellListByCompanyIdCount(
				companyId, state, from, to, keyword));
		page.setRecords(orderBillDao.querySellListByCompanyId(companyId, state,
				page, from, to, keyword));
		return page;
	}

	@Override
	public PageDto<OrderBill> queryListByCompanyId(Integer companyId,
			String state, String rangeState, PageDto<OrderBill> page,
			String from, String to, String type) {
		if (page.getSort() == null) {
			page.setSort("id");
		}
		page.setTotalRecords(orderBillDao.queryListByCompanyIdCount(companyId,
				state, rangeState, from, to, type));
		page.setRecords(orderBillDao.queryListByCompanyId(companyId, state,
				rangeState, page, from, to, type));
		return page;
	}

	@Override
	public PageDto<OrderBill> queryListBySampleId(Integer companyId,
			String state, PageDto<OrderBill> page, String from, String to,
			Integer sampleId, String isfront) {
		if (page.getSort() == null) {
			page.setSort("id");
		}
		page.setTotalRecords(orderBillDao.queryListBySampleIdCount(companyId,
				state, from, to, sampleId, isfront));
		List<OrderBill> list = orderBillDao.queryListBySampleId(companyId,
				state, page, from, to, sampleId, isfront);
		for (OrderBill obj : list) {
			CompanyAccount ac = companyAccountDao.queryAccountByCompanyId(obj
					.getBuyerId());
			if (ac != null) {
				obj.setBuyerCompanyAccount(ac.getAccount());
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public PageDto<OrderBill> queryListByFilter(PageDto<OrderBill> page,
			Map<String, Object> filterMap) {
		if (page.getSort() == null) {
			page.setSort("id");
		}

		filterMap.put("page", page);
		page.setTotalRecords(orderBillDao.queryListByFilterCount(filterMap));
		List<OrderBill> list = orderBillDao.queryListByFilter(filterMap);
		for (OrderBill obj : list) {
			String buyName = companyDAO.queryCompanyNameById(obj.getBuyerId());
			String sellName = companyDAO.queryCompanyNameById(obj.getSellerId());
			if (StringUtils.isNotEmpty(buyName)) {
				obj.setBuyerName(buyName);
			}
			if (StringUtils.isNotEmpty(sellName)) {
				obj.setSellerName(sellName);
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public Map<String, Object> queryBuyListByCompanyIdCount(Integer companyId,
			String from, String to, String keyword, Map<String, Object> out) {
		out.put("bc_", orderBillDao.queryBuyListByCompanyIdCount(companyId,
				null, from, to, keyword,null));
		out.put("bc_00", orderBillDao.queryBuyListByCompanyIdCount(companyId,
				OrderState.STATE_00, from, to, keyword,null));
		out.put("bc_11", orderBillDao.queryBuyListByCompanyIdCount(companyId,
				OrderState.STATE_11, from, to, keyword,null));
		out.put("bc_13", orderBillDao.queryBuyListByCompanyIdCount(companyId,
				OrderState.STATE_13, from, to, keyword,null));
		return out;
	}

	@Override
	public Map<String, Object> querySellListByCompanyIdCount(Integer companyId,
			String from, String to, String keyword, Map<String, Object> out) {
		out.put("sc_", orderBillDao.querySellListByCompanyIdCount(companyId,
				null, from, to, keyword));
		out.put("sc_03", orderBillDao.querySellListByCompanyIdCount(companyId,
				OrderState.STATE_03, from, to, keyword));
		out.put("sc_06", orderBillDao.querySellListByCompanyIdCount(companyId,
				OrderState.STATE_06, from, to, keyword));
		out.put("sc_14", orderBillDao.querySellListByCompanyIdCount(companyId,
				OrderState.STATE_14, from, to, keyword));
		out.put("sc_11", orderBillDao.querySellListByCompanyIdCount(companyId,
				OrderState.STATE_11, from, to, keyword));
		out.put("sc_13", orderBillDao.querySellListByCompanyIdCount(companyId,
				OrderState.STATE_13, from, to, keyword));
		return out;
	}

	@Override
	public Integer sumSampleBySampleId(Integer sampleId) {
		return orderBillDao.sumSampleBySampleId(sampleId);
	}

	@Override
	public Integer countBuyerIdByTime(String from, String to) {
		return orderBillDao.countBuyerIdByTime(from, to);
	}

	@Override
	public Integer countBuyerIdBySampleId(Integer sampleId) {
		return orderBillDao.countBuyerIdBySampleId(sampleId);
	}

	@Override
	public Integer countNotFinishBySampleId(Integer sampleId) {
		return orderBillDao.countNotFinishBySampleId(sampleId);
	}

	@Override
	public OrderBill queryOrderExistByUser(Integer companyId, Integer sampleId) {
		List<OrderBill> list = orderBillDao.queryBuyListByCompanyId(companyId, OrderState.STATE_02,null, null, null, null, sampleId);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}