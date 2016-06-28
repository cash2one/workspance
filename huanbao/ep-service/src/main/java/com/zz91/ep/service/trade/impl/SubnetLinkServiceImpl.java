/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:53:56
 */
package com.zz91.ep.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.trade.SubnetLinkDao;
import com.zz91.ep.domain.trade.SubnetLink;
import com.zz91.ep.service.trade.SubnetLinkService;

@Component("subnetLinkService")
public class SubnetLinkServiceImpl implements SubnetLinkService {
	
	@Resource
	private SubnetLinkDao subnetLinkDao;

	@Override
	public List<SubnetLink> querySubnetLink(Integer parentId) {
		return subnetLinkDao.querySubnetLinkByparentId(parentId);
	}
	
	
}
