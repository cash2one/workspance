package com.ast.feiliao91.persist.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.OrderReturn;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.OrderReturnDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.goods.OrderReturnDao;
@Component("orderReturnDao")
public class OrderReturnDaoImpl extends BaseDaoSupport implements OrderReturnDao{
   
	final static String SQL_PREFIX="orderReturn";
	
	@Override
	public Integer insert(OrderReturn orderReturn) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"),orderReturn);
	}

	@Override
	public OrderReturn selectById(Integer id) {
		// TODO Auto-generated method stub
		return (OrderReturn) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectById"),id);
	}

	@Override
	public Integer updateStatus(Integer orderReturnId,Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderReturnId", orderReturnId);
		map.put("status", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatus"),map);
	}

	@Override
	public Integer updateOrdersReturn(OrderReturn orderReturn) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateOrdersReturn"),orderReturn);
	}

	@Override
	public Integer updateOrdersReturnTwo(OrderReturn orderReturn) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateOrdersReturnTwo"),orderReturn);
	}

	@Override
	public Integer selectByOrderId(Integer orderId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectByOrderId"),orderId);
	}

	@Override
	public OrderReturn queryByOrderId(Integer orderId) {
		return (OrderReturn) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryByOrderId"),orderId);
	}

	@Override
	public Integer updateAll(OrderReturn orderReturn) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateAll"),orderReturn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderReturn queryByLogistics(String logisticsNo) {
		return (OrderReturn) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryByLogistics"),logisticsNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderReturn> myRefund(PageDto<OrderReturnDto> page,
			Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "myRefund"), map);
	}

	@Override
	public Integer myRefundCount(Integer companyId) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "myRefundCount"),companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderReturn> getRefund(PageDto<OrderReturnDto> page,
			Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "getRefund"), map);
	}

	@Override
	public Integer getRefundCount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "getRefundCount"),companyId);
	}

	@Override
	public OrderReturn selectByOrdId(Integer orderId) {
		return (OrderReturn) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectByOrdId"),orderId);
	}

}
