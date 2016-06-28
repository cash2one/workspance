/**
 * @author shiqp
 * @date 2016-01-30
 */
package com.ast.feiliao91.persist.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.Orders;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrdersDto;
import com.ast.feiliao91.dto.goods.OrdersSearchDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.goods.OrdersDao;

@Component("ordersDao")
public class OrdersDaoImpl extends BaseDaoSupport implements OrdersDao {
	final static String SQL_PREFIX="orders";

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> queryOrdersByGoodsId(PageDto<OrdersDto> page, Integer goodsId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("goodsId", goodsId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryOrdersByGoodsId"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> queryOrdersByUser(PageDto<OrdersDto> page,OrdersSearchDto searchDto){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("searchDto", searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryOrdersByUser"), map);
	}

	@Override
	public Integer countOrdersByGoodsId(Integer goodsId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countOrdersByGoodsId"), goodsId);
	}

	@Override
	public Integer countOrdersByUser(OrdersSearchDto searchDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countOrdersByUser"), map);
	}

	@Override
	public Float countTradeQuality(Integer goodsId) {
		return (Float) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countTradeQuality"),goodsId);
	}
	
	@Override
	public Orders selectById(Integer id) {
		return (Orders) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectById"),id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> queryOrdersByOrderNo(String orderNo) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryOrdersByOrderNo"),orderNo);
	}
	
	@Override
	public Integer updatePrice(Orders order) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updatePrice"),order);
	}
	
	@Override
	public Integer updateDetails(Orders order) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateDetails"),order);
	}
	
	@Override
	public Integer updateStatusBuyXD(Integer id,Integer buyCompanyId,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("buyCompanyId", buyCompanyId);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusBuyXD"),map);
	}
	
	@Override
	public Integer updateStatusSellPass(Integer id,Integer sellCompanyId,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("sellCompanyId", sellCompanyId);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusSellPass"),map);
	}
	
	@Override
	public Integer updateStatusBuyPaySuc(Integer id,Integer buyCompanyId,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("buyCompanyId", buyCompanyId);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusBuyPaySuc"),map);
	}
	
	@Override
	public Integer updateStatusSellPostGoods(Integer id,Integer sellCompanyId,String logisticsNo,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("sellCompanyId", sellCompanyId);
		map.put("logisticsNo", logisticsNo);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusSellPostGoods"),map);
	}
	
	@Override
	public Integer updateStatusBuyGetGoods(Integer id,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusBuyGetGoods"),map);
	}
	
	@Override
	public Integer updateStatusTradeOver(Integer id,Integer buyCompanyId,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("buyCompanyId", buyCompanyId);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusTradeOver"),map);
	}
	
	@Override
	public Integer updateHaveRead(Integer id,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateHaveRead"),map);
	}
	@Override
	public Integer insertOrders(Orders orders) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertOrders"), orders);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> getDetailsForBuy(Integer companyId){
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "getDetailsForBuy"),companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> getDetailsForSell(Integer companyId){
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "getDetailsForSell"),companyId);
	}
	@Override
	public Integer updateDetailsByorderId(Integer id,String details){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("details", details);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateDetailsByorderId"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> selecJudge(Integer companyId, Integer goodsId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("goodsId", goodsId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "selecJudge"),map);
	}
	
	@Override
	public Integer updateStatusByOrderNo(String orderNo, Integer status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNo", orderNo);
		map.put("status", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusByOrderNo"),map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> queryByLogistics(String logisticsNo){
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryByLogistics"),logisticsNo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> queryByAllByCompanyId(Integer companyId,PageDto<OrdersDto> page){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryByAllByCompanyId"),map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> countByAllByCompanyId(Integer companyId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "countByAllByCompanyId"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> OrdersByUser(OrdersSearchDto searchDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("searchDto", searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "OrdersByUser"),map);
	}
	@Override
	public Integer updateUserDelByOrderNo(String orderNo, Integer buyIsDel, Integer sellIsDel) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		map.put("buyIsDel", buyIsDel);
		map.put("sellIsDel", sellIsDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateUserDelByOrderNo"),map);
	}
	@Override
	public Orders queryFistIdByOrderNo(String orderNo){
		return (Orders) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryFistIdByOrderNo"), orderNo);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> queryOrdersByAdmin(PageDto<OrdersDto> page,OrdersSearchDto searchDto){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("searchDto", searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryOrdersByAdmin"), map);
	}
	@Override
	public Integer countOrdersByAdmin(OrdersSearchDto searchDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countOrdersByAdmin"), map);
	}

	@Override
	public Integer updateStatus(Integer orderId, Integer status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("status", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatus"),map);
	}
}
