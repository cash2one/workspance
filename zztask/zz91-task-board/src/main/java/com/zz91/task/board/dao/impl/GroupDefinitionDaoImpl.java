/**
 * @author shiqp
 * @date 2015-03-04
 */
package com.zz91.task.board.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.zz91.task.board.dao.GroupDefinitionDao;
import com.zz91.task.board.domain.JobDefinition;
import com.zz91.task.board.dto.Pager;

@Component("groupDefinitionDao")
public class GroupDefinitionDaoImpl extends SqlMapClientDaoSupport implements GroupDefinitionDao {

	@Override
	public Integer insert(Integer groupId, Integer definitionId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("groupId", groupId);
		map.put("definitionId", definitionId);
		return (Integer) getSqlMapClientTemplate().insert("groupDefinition.insertGroupDefinition", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> queryDefinitionIdByGroupId(Integer groupId,Pager<JobDefinition> page) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("groupId", groupId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList("groupDefinition.queryGroupDefiByGroupId", map);
	}

	@Override
	public Integer countDefinitionIdByGroupId(Integer groupId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("groupDefinition.countGroupDefiByGroupId", groupId);
	}

	@Override
	public Integer delete(Integer definitionId) {
		return getSqlMapClientTemplate().delete("groupDefinition.deleteGroupDefinition", definitionId);
	}

	@Override
	public Integer queryGroupIdByDefiId(Integer definitionId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("groupDefinition.selectGroupDefiByDefiId", definitionId);
	}

	@Override
	public Integer updateGroupIdByDefiId(Integer groupId, Integer definitionId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("groupId", groupId);
		map.put("definitionId", definitionId);
		return getSqlMapClientTemplate().update("groupDefinition.updateGroupIdByDefiId", map);
	}

}
