/**
 * @author shiqp
 * @date 2015-03-04
 */
package com.zz91.task.board.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.task.board.dao.GroupDefinitionDao;
import com.zz91.task.board.dao.JobDefinitionDao;
import com.zz91.task.board.domain.JobDefinition;
import com.zz91.task.board.dto.Pager;
import com.zz91.task.board.service.GroupDefinitionService;

@Component("groupDefinitionService")
public class GroupDefinitionServiceImpl implements GroupDefinitionService {
	@Resource
	private GroupDefinitionDao groupDefinitionDao;
	@Resource
	private JobDefinitionDao jobDefinitionDao;
	@Override
	public Integer insert(Integer groupId, Integer definitionId) {
		return groupDefinitionDao.insert(groupId, definitionId);
	}
	@Override
	public Pager<JobDefinition> queryDefinitionByGroupId(Pager<JobDefinition> page,Integer groupId) {
		List<Integer> listDefi=groupDefinitionDao.queryDefinitionIdByGroupId(groupId,page);
		Integer listLeg=groupDefinitionDao.countDefinitionIdByGroupId(groupId);
		List<JobDefinition> list=new ArrayList<JobDefinition>();
		JobDefinition defi=new JobDefinition();
		for(Integer in:listDefi){
			defi=jobDefinitionDao.queryJobDefinitionById(in);
			if(defi!=null){
				list.add(defi);
			}else{
				listLeg=listLeg-1;
			}
		}
		page.setRecords(list);
		page.setTotals(listLeg);
		return page;
	}
	@Override
	public Integer delete(Integer definitionId) {
		return groupDefinitionDao.delete(definitionId);
	}
	@Override
	public Integer queryGroupIdByDefiId(Integer definitionId) {
		return groupDefinitionDao.queryGroupIdByDefiId(definitionId);
	}
	@Override
	public Integer updateGroupIdByDefiId(Integer groupId, Integer definitionId) {
		return groupDefinitionDao.updateGroupIdByDefiId(groupId, definitionId);
	}

}
