package com.zz91.ep.dto.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSystem;

public class MBlogDto {
	private MBlog mBlog;
	private MBlogInfo info;
	private MBlog tragetBlog;
	private MBlogInfo sentInfo;// 用户转发用户的添加
	private List<MBlog> mbList;// 用于存放图片
	private String noteName;
	private List<String> sList;// 用于存放搜索的图片
	private MBlogSystem mBlogSystem;
	
	public MBlogSystem getmBlogSystem() {
		return mBlogSystem;
	}

	public void setmBlogSystem(MBlogSystem mBlogSystem) {
		this.mBlogSystem = mBlogSystem;
	}

	public List<String> getsList() {
		return sList;
	}

	public void setsList(List<String> sList) {
		this.sList = sList;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	public List<MBlog> getMbList() {
		return mbList;
	}

	public void setMbList(List<MBlog> mbList) {
		this.mbList = mbList;
	}

	public MBlogInfo getSentInfo() {
		return sentInfo;
	}

	public void setSentInfo(MBlogInfo sentInfo) {
		this.sentInfo = sentInfo;
	}

	public MBlogInfo getInfo() {
		return info;
	}

	public MBlog getTragetBlog() {
		return tragetBlog;
	}

	public void setInfo(MBlogInfo info) {
		this.info = info;
	}

	public void setTragetBlog(MBlog tragetBlog) {
		this.tragetBlog = tragetBlog;
	}

	public MBlog getmBlog() {
		return mBlog;
	}

	public void setmBlog(MBlog mBlog) {
		this.mBlog = mBlog;
	}

}
