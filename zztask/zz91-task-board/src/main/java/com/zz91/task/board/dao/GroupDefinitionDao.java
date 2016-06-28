/**
 * @author shiqp
 * @date 2015-03-04
 */
package com.zz91.task.board.dao;

import java.util.List;

import com.zz91.task.board.domain.JobDefinition;
import com.zz91.task.board.dto.Pager;

public interface GroupDefinitionDao {
	public Integer insert(Integer groupId, Integer definitionId);

	public List<Integer> queryDefinitionIdByGroupId(Integer groupId,Pager<JobDefinition> page);

	public Integer countDefinitionIdByGroupId(Integer groupId);

	public Integer delete(Integer definitionId);

	public Integer queryGroupIdByDefiId(Integer definitionId);

	public Integer updateGroupIdByDefiId(Integer groupId, Integer definitionId);
}
