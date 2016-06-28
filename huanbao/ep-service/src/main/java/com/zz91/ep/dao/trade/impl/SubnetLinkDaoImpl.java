/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:57:20
 */
package com.zz91.ep.dao.trade.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.SubnetLinkDao;
import com.zz91.ep.domain.trade.SubnetLink;

@Component("subnetLinkDao")
public class SubnetLinkDaoImpl extends BaseDao implements SubnetLinkDao {
	
	final static String PREFIX="subnetLink";

	@SuppressWarnings("unchecked")
	@Override
	public List<SubnetLink> querySubnetLinkByparentId(Integer parentId) {
		return getSqlMapClientTemplate().queryForList(buildId(PREFIX, "querySubnetLinkByparentId"), parentId);
	}

}
