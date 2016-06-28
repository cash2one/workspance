package com.zz91.ep.dto.mblog;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;

public class MBlogCommentDto {
	private MBlogComment comment;
	private MBlogInfo info;
	private MBlogInfo mBlogInfo;
	private MBlog mBlog;
	
	public MBlog getmBlog() {
		return mBlog;
	}

	public void setmBlog(MBlog mBlog) {
		this.mBlog = mBlog;
	}

	public MBlogInfo getmBlogInfo() {
		return mBlogInfo;
	}

	public void setmBlogInfo(MBlogInfo mBlogInfo) {
		this.mBlogInfo = mBlogInfo;
	}

	public MBlogComment getComment() {
		return comment;
	}

	public MBlogInfo getInfo() {
		return info;
	}

	public void setComment(MBlogComment comment) {
		this.comment = comment;
	}

	public void setInfo(MBlogInfo info) {
		this.info = info;
	}

}
