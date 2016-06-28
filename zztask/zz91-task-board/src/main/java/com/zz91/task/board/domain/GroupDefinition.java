/**
 * @author shiqp
 * @date 2015-03-04
 */
package com.zz91.task.board.domain;

import java.util.Date;

public class GroupDefinition {
	private Integer id;
	private Integer groupId;
	private Integer definitionId;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Integer definitionId) {
		this.definitionId = definitionId;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
