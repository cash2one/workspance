/**
 * 
 */
package com.zz91.ep.dao.crm.impl;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.crm.CrmMemberDao;

/**
 * @author mays
 *
 */
@Component("crmMemberDao")
public class CrmMemberDaoImpl extends BaseDao implements CrmMemberDao {

	final static String SQL_PREFIX="crmMember";
	
	@Override
	public String queryName(String code) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryName"), code);
	}

}
