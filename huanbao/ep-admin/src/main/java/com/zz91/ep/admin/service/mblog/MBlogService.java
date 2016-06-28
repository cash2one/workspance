package com.zz91.ep.admin.service.mblog;


import java.util.List;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;

public interface MBlogService {
	
    public PageDto<MBlogDto> queryAllMBlog(MBlog mBlog,MBlogInfo info,PageDto<MBlogDto> page);
	
	public MBlog queryOneMBlogById(Integer id);
	
	public Integer queryMBlogCount();
	
	public Integer updateMBlog(MBlog mBlog);
	
	public Integer delete(Integer id);
//	public MBlog queryOneMblog(Integer id);
	
	public Integer updateDelete(Integer id);
	
	public PageDto<MBlogDto> queryAllTopic(MBlog mBlog,MBlogInfo info,PageDto<MBlogDto> page);
	
	public Integer queryAllTopicCount();
	
	public List<MBlogDto> queryTopicByTitle(String title);
}
