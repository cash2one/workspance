/**
 * @author shiqp 日期:2014-11-17
 */
package com.ast.ast1949.dto.bbs;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;

public class NoticeDto {
	private BbsPostNoticeRecommend notice;
	private BbsPostDO post;
	private BbsUserProfilerDO profiler;

	public BbsPostNoticeRecommend getNotice() {
		return notice;
	}

	public void setNotice(BbsPostNoticeRecommend notice) {
		this.notice = notice;
	}

	public BbsPostDO getPost() {
		return post;
	}

	public void setPost(BbsPostDO post) {
		this.post = post;
	}

	public BbsUserProfilerDO getProfiler() {
		return profiler;
	}

	public void setProfiler(BbsUserProfilerDO profiler) {
		this.profiler = profiler;
	}

}
