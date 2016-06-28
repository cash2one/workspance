/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-9 下午04:43:00
 */
package com.zz91.ep.dao.crm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.crm.CrmCompSvrDao;
import com.zz91.ep.domain.crm.CrmCompSvr;

@Component("crmCompSvrDao")
public class CrmCompSvrDaoImpl extends BaseDao implements CrmCompSvrDao {
	
	final static String PREFIX="crmCompSvr";
	
	@Override
	public CrmCompSvr queryLastSvr(Integer cid, Integer svrId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("svrId", svrId);
		return (CrmCompSvr) getSqlMapClientTemplate().queryForObject(buildId(PREFIX, "queryLastSvr"), root);
	}

	@Override
	public Integer querySeoSvrCount(Integer cid, Integer svrId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("svrId", svrId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(PREFIX, "querySeoSvrCount"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompSvr> querySvr(Integer cid, Integer svrId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("svrId", svrId);
		return getSqlMapClientTemplate().queryForList(buildId(PREFIX, "querySvr"), map);
	}
	
}
