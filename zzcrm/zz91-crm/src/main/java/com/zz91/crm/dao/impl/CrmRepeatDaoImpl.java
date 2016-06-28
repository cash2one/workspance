package com.zz91.crm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.CrmRepeatDao;
import com.zz91.crm.domain.CrmRepeat;
import com.zz91.crm.dto.CrmRepeatDto;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-15 
 */
@Component("crmRepeatDao")
public class CrmRepeatDaoImpl extends BaseDao implements CrmRepeatDao {
	
	final static String SQL_PRIFIX="crmRepeat";

	@Override
	public Integer insertCrmRepeat(CrmRepeat repeat) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PRIFIX, "insertCrmRepeat"), repeat);
	}

	@Override
	public Integer updateCheckStatus(Integer id, Short status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("status", status);
		return getSqlMapClientTemplate().update(buildId(SQL_PRIFIX, "updateCheckStatus"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmRepeat> queryRepeat(Short status,
			PageDto<CrmRepeat> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("status", status);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryRepeat"), root);
	}

	@Override
	public Integer queryRepeatCount(Short status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("status", status);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PRIFIX, "queryRepeatCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmRepeatDto> queryRepeatByOrderId(Integer orderId, Short status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("orderId", orderId);
		root.put("status", status);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PRIFIX, "queryRepeatByOrderId"), root);
	}
}
