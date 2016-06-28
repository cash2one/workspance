package com.ast.ast1949.persist.sample.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.OrderBill;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.OrderBillDAO;

@Component("orderBillDao")
public class OrderBillDAOImpl extends BaseDaoSupport implements OrderBillDAO {

	public Integer insert(OrderBill record) {
		return (Integer) getSqlMapClientTemplate().insert("sample_orderbill.insert", record);
	}

	public int updateByPrimaryKey(OrderBill record) {
		int rows = getSqlMapClientTemplate().update("sample_orderbill.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(OrderBill record) {
		int rows = getSqlMapClientTemplate().update("sample_orderbill.updateByPrimaryKeySelective", record);
		return rows;
	}

	public OrderBill selectByPrimaryKey(Integer id) {
		OrderBill key = new OrderBill();
		key.setId(id);
		OrderBill record = (OrderBill) getSqlMapClientTemplate().queryForObject("sample_orderbill.selectByPrimaryKey", key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		OrderBill key = new OrderBill();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("sample_orderbill.deleteByPrimaryKey", key);
		return rows;
	}

	@Override
	public OrderBill selectByOrderSeq(String orderid) {
		OrderBill key = new OrderBill();
		key.setOrderid(orderid);
		OrderBill record = (OrderBill) getSqlMapClientTemplate().queryForObject("sample_orderbill.selectByOrderSeq", key);
		return record;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBill> queryBuyListByCompanyId(Integer buyerId, String state,PageDto<OrderBill> page, String from, String to,String keyword,Integer sampleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyerId);
		map.put("state", state);
		map.put("page", page);
		map.put("sampleId", sampleId);
		map.put("from", from);
		map.put("to", to);
		map.put("keyword", keyword);
		return getSqlMapClientTemplate().queryForList("sample_orderbill.queryBuyListByCompanyId", map);
	}
	
	@Override
	public Integer  queryBuyListByCompanyIdCount(Integer buyerId, String state, String from, String to,String keyword,Integer sampleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", buyerId);
		map.put("state", state);
		map.put("sampleId", sampleId);
		map.put("from", from);
		map.put("to", to);
		map.put("keyword", keyword);
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.queryBuyListByCompanyIdCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBill> querySellListByCompanyId(Integer sellerId, String state,PageDto<OrderBill> page, String from, String to,String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sellerId", sellerId);
		map.put("state", state);
		map.put("page", page);
		
		map.put("from", from);
		map.put("to", to);
		map.put("keyword", keyword);
		return getSqlMapClientTemplate().queryForList("sample_orderbill.querySellListByCompanyId", map);
	}
	
	@Override
	public Integer querySellListByCompanyIdCount(Integer sellerId, String state, String from, String to,String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sellerId", sellerId);
		map.put("state", state);
		
		map.put("from", from);
		map.put("to", to);
		map.put("keyword", keyword);
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.querySellListByCompanyIdCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBill> queryListByCompanyId(Integer companyId, String state, String rangeState,PageDto<OrderBill> page, String from, String to, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("state", state);
		map.put("type", type);
		map.put("rangeState", rangeState);
		map.put("page", page);
		
		map.put("from", from);
		map.put("to", to);
		return getSqlMapClientTemplate().queryForList("sample_orderbill.queryListByCompanyId", map);
	}
	

	@Override
	public Integer queryListByCompanyIdCount(Integer companyId, String state, String rangeState, String from, String to, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("state", state);
		map.put("type", type);
		map.put("rangeState", rangeState);
		
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.queryListByCompanyIdCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBill> queryListBySampleId(Integer companyId, String state, PageDto<OrderBill> page, String from, String to,Integer sampleId,String isfront) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("state", state);
		map.put("page", page);
		map.put("sampleId", sampleId);
		
		map.put("from", from);
		map.put("to", to);
		map.put("isfront", isfront);
		return getSqlMapClientTemplate().queryForList("sample_orderbill.queryListBySampleId", map);
	}

	@Override
	public Integer queryListBySampleIdCount(Integer companyId, String state, String from, String to, Integer sampleId,String isfront) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("state", state);
		map.put("sampleId", sampleId);
		
		map.put("from", from);
		map.put("to", to);
		map.put("isfront", isfront);
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.queryListBySampleIdCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBill> queryListByFilter(Map<String, Object> filterMap) {
		return getSqlMapClientTemplate().queryForList("sample_orderbill.queryListByFilter", filterMap);
	}

	@Override
	public Integer queryListByFilterCount(Map<String, Object> filterMap) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.queryListByFilterCount", filterMap);
	}
	
	@Override
	public Integer sumSampleBySampleId(Integer sampleId){
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.sumSampleBySampleId", sampleId);
	}
	
	@Override
	public Integer countBuyerIdByTime(String from,String to){
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.countBuyerIdByTime", map);
	}
	
	@Override
	public Integer countBuyerIdBySampleId(Integer sampleId){
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.countBuyerIdBySampleId", sampleId);
	}

	@Override
	public Integer countNotFinishBySampleId(Integer sampleId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("sample_orderbill.countNotFinishBySampleId", sampleId);
	}
}