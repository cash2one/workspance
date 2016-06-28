/**
 * 
 */
package com.zz91.ep.admin.dao.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.sys.SysProjectDao;
import com.zz91.ep.domain.sys.SysProject;
import com.zz91.ep.dto.PageDto;

/**
 * @author root
 *
 */
@Repository("sysProjectDao")
public class SysProjectDaoImpl extends BaseDao implements SysProjectDao {

	final static String SQL_PREFIX="sysProject";
	
	@Override
	public String queryRightByProject(String projectCode) {
		
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryRightByProject"), projectCode);
	}

	@Override
	public SysProject querySysProjectById(Integer id) {
		return (SysProject) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySysProjectById"), id);
	}

	@Override
	public Integer insertSysProject(SysProject sysProject) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertSysProject"), sysProject);
	}

	@Override
	public Integer updateSysProject(SysProject sysProject) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSysProject"), sysProject);
	}

	@Override
	public Integer deleteSysProjectById(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteSysProjectById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysProject> querySysProject(SysProject sysProject,PageDto<SysProject> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("sysProject", sysProject);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySysProject"), root);
	}

	@Override
	public Integer querySysProjectCount(SysProject sysProject) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("sysProject", sysProject);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySysProjectCount"), root);
	}

}
