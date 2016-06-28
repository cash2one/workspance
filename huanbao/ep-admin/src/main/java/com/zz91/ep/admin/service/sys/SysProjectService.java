package com.zz91.ep.admin.service.sys;

import com.zz91.ep.domain.sys.SysProject;
import com.zz91.ep.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-11-16 
 */
public interface SysProjectService {
	
	/**
	 * 根据id查询信息
	 * @param code
	 * @return
	 */
	public SysProject querySysProjectById(Integer id);
	
	/**
	 * 添加一条新信息
	 * @param sysProject
	 * @return
	 */
	public Integer createSysProject(SysProject sysProject);
	/**
	 *更新平台信息
	 * @param sysProject
	 * @return
	 */
	public Integer updateSysProject(SysProject sysProject);
	/**
	 * 删除一条信息
	 * @param id
	 * @return
	 */
	public Integer deleteSysProjectById(Integer id);
	/**
	 * 平台项目分页
	 * @param sysProject
	 * @param page
	 * @return
	 */
	public PageDto<SysProject> pageAllSysProject(SysProject sysProject,PageDto<SysProject> page);
}
