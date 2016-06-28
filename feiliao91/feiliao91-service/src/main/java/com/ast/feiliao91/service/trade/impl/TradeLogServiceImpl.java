package com.ast.feiliao91.service.trade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.domain.trade.TradeLog;
import com.ast.feiliao91.domain.trade.TradeLogSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.trade.TradeLogDto;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.goods.GoodsDao;
import com.ast.feiliao91.persist.goods.OrdersDao;
import com.ast.feiliao91.persist.trade.CashAdvanceDao;
import com.ast.feiliao91.persist.trade.TradeLogDao;
import com.ast.feiliao91.service.trade.TradeLogService;
import com.zz91.util.lang.StringUtils;

import net.sf.json.JSONObject;

@Component("tradeLogService")
public class TradeLogServiceImpl implements TradeLogService {

	@Resource
	private TradeLogDao tradeLogDao;
	@Resource
	private OrdersDao ordersDao;
	@Resource
	private CompanyInfoDao companyInfoDao;
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private CashAdvanceDao cashAdvanceDao;
	
	@Override
	public Integer payOrder(Integer companyId, String orderNo, Float money) {
		TradeLog tradeLog = new TradeLog();
		tradeLog.setCompanyId(companyId);
		tradeLog.setMoney(money);
		tradeLog.setStatus(STATUS_0); // 出帐
		JSONObject js = new JSONObject();
		js.put("orderNo", orderNo);
		tradeLog.setRemark(js.toString());
		tradeLog.setType(1);//设置流水的交易类型为1（订单流水）
		return tradeLogDao.insert(tradeLog);
	}
	
	@Override
	public Integer paySucForSell(Integer companyId, String orderNo, Float money) {
		TradeLog tradeLog = new TradeLog();
		tradeLog.setCompanyId(companyId);
		tradeLog.setMoney(money);
		tradeLog.setStatus(STATUS_1); // 进帐
		JSONObject js = new JSONObject();
		js.put("orderNo", orderNo);
		tradeLog.setRemark(js.toString());
		tradeLog.setType(1);//设置流水的交易类型为1（订单流水）
		return tradeLogDao.insert(tradeLog);
	}
	
	@Override
	public Integer insertCashAdvance(Integer companyId,Integer cashAdvanceId,Float money){
		TradeLog tradeLog = new TradeLog();
		tradeLog.setCompanyId(companyId);
		tradeLog.setMoney(money);
		tradeLog.setStatus(STATUS_0); // 出帐
		JSONObject js = new JSONObject();
		js.put("cashAdvanceId", cashAdvanceId);
		tradeLog.setRemark(js.toString());
		tradeLog.setType(3);//设置流水的交易类型为3（提现流水）
		return tradeLogDao.insert(tradeLog);
	}
	
	
	@Override
	public PageDto<TradeLogDto> pageMyBill(Integer companyId,PageDto<TradeLogDto> page){
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("gmt_modified");
		}
		page.setDir("desc");
		TradeLogSearch search = new TradeLogSearch();
		search.setCompanyId(companyId);
		List<TradeLog> list = tradeLogDao.queryTradeLogList(page,search);
		List<TradeLogDto> resultList = new ArrayList<TradeLogDto>();
		for (TradeLog tradeLog : list) {
			TradeLogDto dto = new TradeLogDto();
			if(tradeLog.getType() == null){
				continue;
			}
			if(tradeLog.getType().equals(1)){
				//若类型为1则取出remark中的orderNo信息
				JSONObject remarkObj = JSONObject.fromObject(tradeLog.getRemark());
				if(remarkObj!= null && remarkObj.get("orderNo")!=null){
					String orderNo = remarkObj.get("orderNo").toString();
					//根据orderNo获得订单(只取一条订单)
					List<Orders> orderList = ordersDao.queryOrdersByOrderNo(orderNo);
					if(orderList.size()<1){
						continue;
					}
					Orders order = orderList.get(0);//取一条
					dto.setOrders(order);
					// 卖家信息填充
					if (order.getSellCompanyId() != null) {
						CompanyInfo info = companyInfoDao.queryById(order
								.getSellCompanyId());
						if (info != null) {
							dto.setSellCompany(info);
						}
					}
					// json details 转换
					if (StringUtils.isNotEmpty(order.getDetails())) {
						JSONObject js = JSONObject.fromObject(order.getDetails());
						dto.setDetailJson(js);
					} else {
						dto.setDetailJson(new JSONObject());
					}
					// 组装商品信息
					Goods goods = goodsDao.queryById(order.getGoodsId());
					dto.setGoods(goods);
					dto.setType(1);//设置为订单流水
				}
			}else if(tradeLog.getType().equals(2)){
				//若类型为2则为充值流水
				dto.setType(2);
			}else if(tradeLog.getType().equals(3)){
				//若类型为3则为提现流水
				//获得remark信息
				JSONObject remarkObj = JSONObject.fromObject(tradeLog.getRemark());
				if(remarkObj == null || remarkObj.get("cashAdvanceId") ==null){
					continue;
				}
				String cashAdvanceId = remarkObj.get("cashAdvanceId").toString();
				CashAdvance cashAdvance = cashAdvanceDao.queryCashAdvanceById(Integer.valueOf(cashAdvanceId));
				dto.setCashAdvance(cashAdvance);
				dto.setTradeLog(tradeLog);
				CompanyInfo info = companyInfoDao.queryById(companyId);
				dto.setSellCompany(info);
				dto.setType(3);
			}else{
				//若类型为4则为保证金流水
				dto.setType(4);
			}
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(tradeLogDao.countTradeLogList(search));
		return page;
	}

}
