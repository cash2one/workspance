/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.dto.bbs;

import java.io.Serializable;

import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
public class BbsPostReplyDto implements Serializable{

	private static final long serialVersionUID = 2661883740990510806L;
	private BbsPostReplyDO reply;
	private BbsUserProfilerDO profiler;
	
	/**
	 * @return the reply
	 */
	public BbsPostReplyDO getReply() {
		return reply;
	}
	/**
	 * @param reply the reply to set
	 */
	public void setReply(BbsPostReplyDO reply) {
		this.reply = reply;
	}
	/**
	 * @return the profiler
	 */
	public BbsUserProfilerDO getProfiler() {
		return profiler;
	}
	/**
	 * @param profiler the profiler to set
	 */
	public void setProfiler(BbsUserProfilerDO profiler) {
		this.profiler = profiler;
	}
	
	
}
