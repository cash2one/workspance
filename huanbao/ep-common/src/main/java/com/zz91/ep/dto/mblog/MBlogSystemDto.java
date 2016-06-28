package com.zz91.ep.dto.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSystem;

public class MBlogSystemDto {

	private MBlogSystem mBlogSystem;
	private MBlog mBlog;// 博文
	private MBlogComment comment;// 用户装我发布信息
	private MBlogComment tragetComment;// 用于装对方对我的评论的信息
	private MBlogInfo tragetInfo;// 用于装对方的id
	private MBlog tragetMBlog;// 用于转发目标的博文
	private MBlogInfo info;// 用于装@我的人的资料
	private List<String> photoList;

	public List<String> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}

	public MBlogInfo getInfo() {
		return info;
	}

	public void setInfo(MBlogInfo info) {
		this.info = info;
	}

	public MBlog getTragetMBlog() {
		return tragetMBlog;
	}

	public void setTragetMBlog(MBlog tragetMBlog) {
		this.tragetMBlog = tragetMBlog;
	}

	public MBlogComment getTragetComment() {
		return tragetComment;
	}

	public void setTragetComment(MBlogComment tragetComment) {
		this.tragetComment = tragetComment;
	}

	public MBlogSystem getmBlogSystem() {
		return mBlogSystem;
	}

	public void setmBlogSystem(MBlogSystem mBlogSystem) {
		this.mBlogSystem = mBlogSystem;
	}

	public MBlog getmBlog() {
		return mBlog;
	}

	public MBlogComment getComment() {
		return comment;
	}

	public MBlogInfo getTragetInfo() {
		return tragetInfo;
	}

	public void setmBlog(MBlog mBlog) {
		this.mBlog = mBlog;
	}

	public void setComment(MBlogComment comment) {
		this.comment = comment;
	}

	public void setTragetInfo(MBlogInfo tragetInfo) {
		this.tragetInfo = tragetInfo;
	}

}
