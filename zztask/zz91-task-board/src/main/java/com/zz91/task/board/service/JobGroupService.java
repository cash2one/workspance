/**
 * @author shiqp
 * @date 2015-03-03
 */
package com.zz91.task.board.service;

import java.util.List;

import com.zz91.task.board.domain.JobGroup;
import com.zz91.task.board.dto.Pager;

public interface JobGroupService {
	public Pager<JobGroup> queryAllGroup(Pager<JobGroup> page);

	public Integer addGroupInfo(String groupName);

	public Integer delete(Integer id, Integer isDel);

	public JobGroup queryGroupById(Integer id);

	public Integer updateGroupById(Integer id, String groupName);

	public JobGroup queryGroupByGroupName(String groupName);

	public Pager<JobGroup> queryGroupByKeywords(Pager<JobGroup> page, String keywords);

	public List<JobGroup> queryAllGroup();
}
