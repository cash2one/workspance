/**
 * @author shiqp
 * @date 2015-03-04
 */
package com.zz91.task.board.service;

import com.zz91.task.board.domain.JobDefinition;
import com.zz91.task.board.dto.Pager;

public interface GroupDefinitionService {
	public Integer insert(Integer groupId, Integer definitionId);

	public Pager<JobDefinition> queryDefinitionByGroupId(Pager<JobDefinition> page, Integer groupId);

	public Integer delete(Integer definitionId);

	public Integer queryGroupIdByDefiId(Integer definitionId);

	public Integer updateGroupIdByDefiId(Integer groupId, Integer definitionId);

}
