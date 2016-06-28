/**
 * @author shiqp
 * @date 2015-03-03
 */
package com.zz91.task.board.dao;

import java.util.List;

import com.zz91.task.board.domain.JobGroup;

public interface JobGroupDao {
	public List<JobGroup> queryAllGroup();

	public Integer countAllGroup();

	public Integer addGroupInfo(String groupName);

	public Integer delete(Integer id, Integer isDel);

	public JobGroup queryGroupById(Integer id);

	public Integer updateGroupById(Integer id, String groupName);

	public JobGroup queryGroupByGroupName(String groupName);

	public List<JobGroup> queryGroupByKeywords(String keywords);

	public Integer countGroupByKeywords(String keywords);

}
