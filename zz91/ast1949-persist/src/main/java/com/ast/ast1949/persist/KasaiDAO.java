/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午01:40:35
 */
package com.ast.ast1949.persist;

import java.sql.SQLException;

/**
 *
 * @author Ryan
 *
 */
public interface KasaiDAO {
	/**
	 * 删除objects
	 * @param ObjectId
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteObjects(String objectId) throws SQLException;

	/**
	 * 是否存在该用户名
	 * @param username
	 * @return
	 */
	public Object isExist(String loginId);
}
