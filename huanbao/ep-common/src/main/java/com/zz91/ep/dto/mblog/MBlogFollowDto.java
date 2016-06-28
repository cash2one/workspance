package com.zz91.ep.dto.mblog;

import com.zz91.ep.domain.mblog.MBlogFollow;
import com.zz91.ep.domain.mblog.MBlogInfo;

public class MBlogFollowDto {
	private MBlogInfo mBlogInfo;
	private MBlogFollow mBlogFollow;

	public MBlogInfo getmBlogInfo() {
		return mBlogInfo;
	}

	public MBlogFollow getmBlogFollow() {
		return mBlogFollow;
	}

	public void setmBlogInfo(MBlogInfo mBlogInfo) {
		this.mBlogInfo = mBlogInfo;
	}

	public void setmBlogFollow(MBlogFollow mBlogFollow) {
		this.mBlogFollow = mBlogFollow;
	}

}
