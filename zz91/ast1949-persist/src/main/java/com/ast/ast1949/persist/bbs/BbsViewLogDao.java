/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-8-16
 */
package com.ast.ast1949.persist.bbs;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-8-16
 */
public interface BbsViewLogDao {

	public Integer countViewLog(Integer postId, long targetDate);
	
	public Integer updateViewLogNum(Integer postId, long targetDate);
	
	public Integer insertViewNum(Integer postId, long targetDate);
}
