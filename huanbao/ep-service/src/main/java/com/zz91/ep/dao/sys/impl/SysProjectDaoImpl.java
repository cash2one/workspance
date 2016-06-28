/**
 * 
 */
package com.zz91.ep.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.sys.SysProjectDao;

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

}
