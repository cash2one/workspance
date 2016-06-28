package com.ast.feiliao91.service.goods.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrderReturnDto;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.goods.GoodsDao;
import com.ast.feiliao91.persist.goods.OrderReturnDao;
import com.ast.feiliao91.persist.goods.OrdersDao;
import com.ast.feiliao91.service.goods.OrderReturnService;
import com.ast.feiliao91.service.goods.OrdersService;

@Component("orderReturnService")
public class OrderReturnServiceImpl implements OrderReturnService{
    @Resource
    private OrderReturnDao orderReturnDao;
    @Resource
    private OrdersDao ordersDao;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private CompanyInfoDao companyInfoDao;
	@Override
	public Integer insert(OrderReturn orderReturn) {
		return orderReturnDao.insert(orderReturn);
	}
	@Override
	public OrderReturn selectById(Integer id) {
		return orderReturnDao.selectById(id);
	}
	@Override
	public Integer updateStatus(Integer orderReturnId,Integer status) {
		return orderReturnDao.updateStatus(orderReturnId,status);
	}
	@Override
	public Integer updateOrdersReturn(OrderReturn orderReturn) {
		return orderReturnDao.updateOrdersReturn(orderReturn);
	}
	/**
	 * 卖家同意退款，订单进入退款中状态
	 */
	@Override
	public Integer updateOrdersReturnTwo(OrderReturn orderReturn) {
		Integer i = orderReturnDao.updateOrdersReturnTwo(orderReturn);
		if (i>0) {
			ordersDao.updateStatusByOrderNo(orderReturn.getOrderNo(), OrdersService.STATUS_50);
		}
		return i;
	}
	@Override
	public Integer selectByOrderId(Integer orderId) {
		return orderReturnDao.selectByOrderId(orderId);
	}
	@Override
	public OrderReturn queryByOrderId(Integer orderId) {
		return orderReturnDao.queryByOrderId(orderId);
	}
	@Override
	public Integer updateAll(OrderReturn orderReturn) {
		return orderReturnDao.updateAll(orderReturn);
	}
	@Override
	public PageDto<OrderReturnDto> myRefund(PageDto<OrderReturnDto> page,
			Integer companyId) {
		List<OrderReturn> list =  orderReturnDao.myRefund(page,companyId);
		List<OrderReturnDto> list2= new ArrayList<OrderReturnDto>();
		if(list!=null){
		for(OrderReturn or :list ){
			OrderReturnDto dto = new OrderReturnDto();
			dto.setOrderReturn(or);
			Orders order = ordersDao.selectById(or.getOrderId());
			dto.setOrders(order);
			Goods goods = goodsDao.queryGoodById(order.getGoodsId());
			dto.setGoods(goods);
			CompanyInfo info = companyInfoDao.queryById(order.getSellCompanyId());
			dto.setCompanyInfo(info);
			Float payprice = order.getBuyPricePay();
			Float num=order.getBuyQuantity();
			Float logistics = order.getBuyPriceLogistics();
			Float sum=payprice*num+logistics;
			dto.setPrice(sum);
			list2.add(dto);
		}
		page.setRecords(list2);
		}
		page.setTotalRecords(orderReturnDao.myRefundCount(companyId));
		return page;
	}
	@Override
	public Integer myRefundCount(Integer companyId) {
		return orderReturnDao.myRefundCount(companyId);
	}
	@Override
	public PageDto<OrderReturnDto> getRefund(PageDto<OrderReturnDto> page,
			Integer companyId) {
		List<OrderReturn>  list = orderReturnDao.getRefund(page,companyId);
		List<OrderReturnDto> list2= new ArrayList<OrderReturnDto>();
		if(list!=null){
		for(OrderReturn or :list ){
			OrderReturnDto dto = new OrderReturnDto();
			dto.setOrderReturn(or);
			Orders order = ordersDao.selectById(or.getOrderId());
			dto.setOrders(order);
			Goods goods = goodsDao.queryGoodById(order.getGoodsId());
			dto.setGoods(goods);
			CompanyInfo info = companyInfoDao.queryById(order.getSellCompanyId());
			dto.setCompanyInfo(info);
			Float payprice = order.getBuyPricePay();
			Float num=order.getBuyQuantity();
			Float logistics = order.getBuyPriceLogistics();
			Float sum=payprice*num+logistics;
			dto.setPrice(sum);
			list2.add(dto);
		}
		page.setRecords(list2);
		}
		page.setTotalRecords(orderReturnDao.getRefundCount(companyId));
		return page;
	}
	@Override
	public Integer getRefundCount(Integer companyId) {
		return orderReturnDao.getRefundCount(companyId);
	}
	@Override
	public OrderReturn selectByOrdId(Integer orderId) {
		return orderReturnDao.selectByOrdId(orderId);
	}

}
