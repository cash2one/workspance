/**
 * @author shiqp
 * @date 2015-03-03
 */
package com.zz91.task.board.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.task.board.dao.JobGroupDao;
import com.zz91.task.board.domain.JobGroup;
import com.zz91.task.board.dto.Pager;
import com.zz91.task.board.service.JobGroupService;

@Component("jobGroupService")
public class JobGroupServiceImpl implements JobGroupService {
	@Resource
	private JobGroupDao jobGroupDao;

	@Override
	public Pager<JobGroup> queryAllGroup(Pager<JobGroup> page) {
		List<JobGroup> list = jobGroupDao.queryAllGroup();
		page.setRecords(list);
		page.setTotals(jobGroupDao.countAllGroup());
		return page;
	}

	@Override
	public Integer addGroupInfo(String groupName) {
		return jobGroupDao.addGroupInfo(groupName);
	}

	@Override
	public Integer delete(Integer id, Integer isDel) {
		return jobGroupDao.delete(id, isDel);
	}

	@Override
	public JobGroup queryGroupById(Integer id) {
		return jobGroupDao.queryGroupById(id);
	}

	@Override
	public Integer updateGroupById(Integer id, String groupName) {
		return jobGroupDao.updateGroupById(id, groupName);
	}

	@Override
	public JobGroup queryGroupByGroupName(String groupName) {
		return jobGroupDao.queryGroupByGroupName(groupName);
	}

	@Override
	public Pager<JobGroup> queryGroupByKeywords(Pager<JobGroup> page, String keywords) {
		page.setRecords(jobGroupDao.queryGroupByKeywords(keywords));
		page.setTotals(jobGroupDao.countGroupByKeywords(keywords));
		return page;
	}

	@Override
	public List<JobGroup> queryAllGroup() {
		return jobGroupDao.queryAllGroup();
	}

}
