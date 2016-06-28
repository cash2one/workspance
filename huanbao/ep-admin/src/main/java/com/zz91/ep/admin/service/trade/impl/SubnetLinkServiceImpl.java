/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:53:56
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.SubnetLinkDao;
import com.zz91.ep.admin.service.trade.SubnetLinkService;
import com.zz91.ep.domain.trade.SubnetLink;
import com.zz91.ep.dto.PageDto;

@Component("subnetLinkService")
public class SubnetLinkServiceImpl implements SubnetLinkService {
	
	@Resource
	private SubnetLinkDao subnetLinkDao;

	@Override
	public Integer createSubnetLink(SubnetLink subnetLink) {
		if (subnetLink.getParentId()==null){
			subnetLink.setParentId(0);
		}
		return subnetLinkDao.insertSubnetLink(subnetLink);
	}

	@Override
	public Integer deleteLinkById(Integer id) {
		return subnetLinkDao.deleteLinkById(id);
	}

	@Override
	public PageDto<SubnetLink> pageSubnetLink(Integer parentId,
			PageDto<SubnetLink> page) {
		page.setRecords(subnetLinkDao.queryChildLinkByParentId(parentId,page));
		page.setTotals(subnetLinkDao.queryChildCountByParentId(parentId));
		return page;
	}

	@Override
	public List<SubnetLink> queryParentLink() {
		return subnetLinkDao.queryParentLink();
	}

	@Override
	public Integer updateSubnetLink(SubnetLink subnetLink) {
		return subnetLinkDao.updateSubnetLink(subnetLink);
	}

}
