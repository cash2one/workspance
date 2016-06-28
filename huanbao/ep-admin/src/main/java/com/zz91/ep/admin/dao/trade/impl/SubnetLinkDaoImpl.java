/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:57:20
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.SubnetLinkDao;
import com.zz91.ep.domain.trade.SubnetLink;
import com.zz91.ep.dto.PageDto;

@Component("subnetLinkDao")
public class SubnetLinkDaoImpl extends BaseDao implements SubnetLinkDao {
	
	final static String PREFIX="subnetLink";

	@Override
	public Integer deleteLinkById(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(PREFIX, "deleteLinkById"), id);
	}

	@Override
	public Integer insertSubnetLink(SubnetLink subnetLink) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(PREFIX, "insertSubnetLink"), subnetLink);
	}

	@Override
	public Integer queryChildCountByParentId(Integer parentId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("parentId", parentId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(PREFIX, "queryChildCountByParentId"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubnetLink> queryChildLinkByParentId(Integer parentId,
			PageDto<SubnetLink> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("parentId", parentId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(PREFIX, "queryChildLinkByParentId"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubnetLink> queryParentLink() {
		return getSqlMapClientTemplate().queryForList(buildId(PREFIX, "queryParentLink"));
	}

	@Override
	public Integer updateSubnetLink(SubnetLink subnetLink) {
		return getSqlMapClientTemplate().update(buildId(PREFIX, "updateSubnetLink"), subnetLink);
	}

}
