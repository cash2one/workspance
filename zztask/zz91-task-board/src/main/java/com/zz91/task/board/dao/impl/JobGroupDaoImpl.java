/**
 * @author shiqp
 * @date 2015-03-03
 */
package com.zz91.task.board.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.zz91.task.board.dao.JobGroupDao;
import com.zz91.task.board.domain.JobGroup;
@Component("jobGroupDao")
public class JobGroupDaoImpl extends SqlMapClientDaoSupport implements JobGroupDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<JobGroup> queryAllGroup() {
		return getSqlMapClientTemplate().queryForList("jobGroup.selectAllGroup");
	}

	@Override
	public Integer countAllGroup() {
		return (Integer) getSqlMapClientTemplate().queryForObject("jobGroup.countAllGroup");
	}

	@Override
	public Integer addGroupInfo(String groupName) {
		return (Integer) getSqlMapClientTemplate().insert("jobGroup.addGroupInfo", groupName);
	}

	@Override
	public Integer delete(Integer id,Integer isDel) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("isDel", isDel);
		return getSqlMapClientTemplate().update("jobGroup.deleteGroupById", map);
	}

	@Override
	public JobGroup queryGroupById(Integer id) {
		return (JobGroup) getSqlMapClientTemplate().queryForObject("jobGroup.selectGroupById", id);
	}

	@Override
	public Integer updateGroupById(Integer id, String groupName) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("groupName", groupName);
		return getSqlMapClientTemplate().update("jobGroup.updateGroupById", map);
	}

	@Override
	public JobGroup queryGroupByGroupName(String groupName) {
		return (JobGroup) getSqlMapClientTemplate().queryForObject("jobGroup.selectGroupByGroupName",groupName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JobGroup> queryGroupByKeywords(String keywords) {
		return getSqlMapClientTemplate().queryForList("jobGroup.selectGroupByKeyword",keywords);
	}

	@Override
	public Integer countGroupByKeywords(String keywords) {
		return (Integer) getSqlMapClientTemplate().queryForObject("jobGroup.countGroupByKeywords",keywords);
	}

}
