package com.ast.feiliao91.service.goods.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.service.BaseServiceTestCase;
import com.ast.feiliao91.service.commom.ParamService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.OrdersService;

public class OrdersServiceTest extends BaseServiceTestCase{
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private GoodsService goodsService;
	
//	public void test_xiaoliang(){
//		List<Object> a=goodsService.queryH("1000100010011001",3,10);
//		System.out.println(a);
//		for (Object object : a) {
//			JSONObject obj=JSONObject.fromObject(object);
//			System.out.println("id"+obj.get("g_id"));
//			if (obj.get("g_id") instanceof Integer) {
//				System.out.println("ok");
//			}
//		}
//	}
//	public void test_param(){
////		paramService.listParamByTypes("wuliu");
//		String company="datianwuliu";
//		System.out.println(paramService.queryParamByKey(company).getValue());
//	}
//	@Test
//	public void test_queryOrdersByOrderNo() {
//		List<Orders> orders = ordersService.queryOrdersByOrderNo("20160130261");
//		System.out.println((Integer) orders.size());
//	}
	
//	public void test_updateStatusBuyXD1(){
//		ordersService.cancelOrder("20160130261","ashdjagashdhajghj");
//	}
	
//	@Test
//	public void test_updateStatusBuyXD() {
//		ordersService.updateStatusBuyXD("20160130261",13);
//	}
	
	
//	@Test
//	public void test_updateStatusSellPass() {
//		ordersService.updateStatusSellPass("20160130262",12);
//	}
//	@Test
//	public void test_updateStatusBuyPaySuc() {
//		ordersService.updateStatusBuyPaySuc("20160130261",13);
//	}
//	@Test
//	public void test_updateStatusSellPostGoods() {
//		ordersService.updateStatusSellPostGoods("20160130261",11,"201602012156");
//	}
	
//	@Test
//	public void test_updateStatusBuyGetGoods() {
//		ordersService.updateStatusBuyGetGoods(1);
//	}
//	@Test
//	public void test_updateStatusTradeOver() {
//		ordersService.updateStatusTradeOver("20160130261",13);
//	}
//	//设置已读
//	@Test
//	public void test_updateHaveRead() {
//		ordersService.updateHaveRead("20160130261",13);
//	}
}
