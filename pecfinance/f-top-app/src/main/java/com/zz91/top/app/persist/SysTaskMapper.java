/**
 * 
 */
package com.zz91.top.app.persist;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zz91.top.app.domain.SysTask;

/**
 * @author mays
 *
 */
public interface SysTaskMapper {

	public List<SysTask> queryByTask(
			@Param("method") String method, 
			@Param("fromId") Integer fromId,
			@Param("limit") Integer limit);
	
	public Date queryLastEndDate(@Param("nick") String nick, 
			@Param("method") String method);
	
	public Integer insert(SysTask task);
	
	public Integer update(SysTask task);
	
	public Integer countByTaskId(String taskId);
	
}
