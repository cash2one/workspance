package com.zz91.ep.admin.dao.sys;

import java.util.List;

import com.zz91.ep.domain.sys.SysProject;
import com.zz91.ep.dto.PageDto;

public interface SysProjectDao {

	public String queryRightByProject(String projectCode);
	
	public SysProject querySysProjectById(Integer id);
	
	public Integer insertSysProject(SysProject sysProject);
	
	public Integer updateSysProject(SysProject sysProject);
	
	public Integer deleteSysProjectById(Integer id);
	
	public List<SysProject> querySysProject(SysProject sysProject,PageDto<SysProject> page);
	
	public Integer querySysProjectCount(SysProject sysProject);
 }
