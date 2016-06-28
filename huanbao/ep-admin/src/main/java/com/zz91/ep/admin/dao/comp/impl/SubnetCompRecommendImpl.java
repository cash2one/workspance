/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午07:48:49
 */
package com.zz91.ep.admin.dao.comp.impl;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.comp.SubnetCompRecommendDao;
import com.zz91.ep.domain.comp.SubnetCompRecommend;

@Component("subnetCompRecommendDao")
public class SubnetCompRecommendImpl extends BaseDao implements SubnetCompRecommendDao {
	
	final static String PREFIX="subnetCompRecommend";

	@Override
	public Integer deleteSubnetCompRecommend(Integer cid) {
		return getSqlMapClientTemplate().delete(buildId(PREFIX, "deleteSubnetCompRecommend"), cid);
	}

	@Override
	public Integer insertSubnetCompRecommend(SubnetCompRecommend recommend) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(PREFIX, "insertSubnetCompRecommend"), recommend);
	}

}
