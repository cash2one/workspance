package com.zz91.ep.admin.dao.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;

public interface MBlogDao {

	public List<MBlogDto> queryAllMBlog(MBlog mBlog,MBlogInfo info,PageDto<MBlogDto> page);
	
	public MBlog queryOneMBlogById(Integer id);
	
	public Integer queryMBlogCount();
	
	public MBlog queryoneMblog(Integer id);
	
	public Integer updateMBlog(MBlog mBlog);
	
	public Integer delete(Integer id);
	
	public Integer updateDelete(Integer id);
	
	public List<MBlogDto> queryAllTopic(MBlog mBlog,MBlogInfo info,PageDto<MBlogDto> page);
	
	public Integer queryAllTopicCount();
	
	public List<MBlogDto> queryTopicByTitle(String title);
	
}
