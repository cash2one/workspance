/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午01:45:36
 */
package com.ast.ast1949.persist;

import java.sql.SQLException;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ryan
 *
 */
@Component("kasaiDAO")
public class KasaiDAOImpl extends SqlMapClientDaoSupport implements KasaiDAO {
	private SqlMapClientTemplate sqlMapClientTemplate = getSqlMapClientTemplate();
	// can't find sqlMapClient in here!
	//private SqlMapClient sqlMapClient = getSqlMapClient();

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ast.ast1949.persist.KasaiDAO#deleteObjects()
	 */
	public boolean deleteObjects(String objectId) throws SQLException {
		try {
			getSqlMapClient().startTransaction();
			sqlMapClientTemplate.delete("KasaiOperation.deleteObjectsInKasaiObjects", objectId);
			sqlMapClientTemplate.delete("KasaiOperation.deleteObjectsInKasaiObjectsGroupsRoles", objectId);
			sqlMapClientTemplate.delete("KasaiOperation.deleteObjectsInKasaiObjectsUsersRoles", objectId);
			getSqlMapClient().commitTransaction();
			return true;
		} finally{
			getSqlMapClient().endTransaction();
		}
	}

	/* (non-Javadoc)
	 * @see com.ast.ast1949.persist.KasaiDAO#isExists(java.lang.String)
	 */
	public Object isExist(String loginId) {
		Object result=sqlMapClientTemplate.queryForObject("KasaiOperation.isExistThisName",loginId);
		return result;
	}

}
