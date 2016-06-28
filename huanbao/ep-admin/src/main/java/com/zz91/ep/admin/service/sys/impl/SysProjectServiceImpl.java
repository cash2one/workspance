package com.zz91.ep.admin.service.sys.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.sys.SysProjectDao;
import com.zz91.ep.admin.service.sys.SysProjectService;
import com.zz91.ep.domain.sys.SysProject;
import com.zz91.ep.dto.PageDto;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-11-16 
 */
@Component("sysProjectService")
public class SysProjectServiceImpl implements SysProjectService {
	
	@Resource
	private SysProjectDao sysProjectDao;
	
	final static int PASSWORD_LENGTH = 16;

	@Override
	public SysProject querySysProjectById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return sysProjectDao.querySysProjectById(id);
	}

	@Override
	public Integer createSysProject(SysProject sysProject) {
		
		if (sysProject.getCode()==null || StringUtils.isEmpty(sysProject.getCode())) {
			sysProject.setCode(UUID.randomUUID().toString());
		}
		
		if (sysProject.getPassword()==null || StringUtils.isEmpty(sysProject.getPassword())) {
			sysProject.setPassword(StringUtils.randomString(PASSWORD_LENGTH));
		}
		
		Assert.notNull(sysProject, "the sysProject can not be null");
		if (StringUtils.isEmpty(sysProject.getAvatar())) {
			sysProject.setAvatar("/themes/images/no_image.gif");
		}
		return sysProjectDao.insertSysProject(sysProject);
	}

	@Override
	public Integer updateSysProject(SysProject sysProject) {
		Assert.notNull(sysProject, "the sysProject can not be null");
		return sysProjectDao.updateSysProject(sysProject);
	}

	@Override
	public Integer deleteSysProjectById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return sysProjectDao.deleteSysProjectById(id);
	}

	@Override
	public PageDto<SysProject> pageAllSysProject(SysProject sysProject,PageDto<SysProject> page) {
		page.setRecords(sysProjectDao.querySysProject(sysProject, page));
		page.setTotals(sysProjectDao.querySysProjectCount(sysProject));
		return page;
	}
}
