/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午07:48:49
 */
package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.SubnetCompRecommendDao;
import com.zz91.ep.domain.comp.CompProfile;

@Component("subnetCompRecommendDao")
public class SubnetCompRecommendDaoImpl extends BaseDao implements SubnetCompRecommendDao {
	
	final static String PREFIX="subnetCompRecommend";

	@SuppressWarnings("unchecked")
	@Override
	public List<CompProfile> queryCompBySubRec(String subnetCategory,
			Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("subnetCategory", subnetCategory);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(PREFIX, "queryCompBySubRec"), root);
	}

}
