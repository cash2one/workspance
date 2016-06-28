package com.zz91.ep.admin.service.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;

public interface MBlogCommentService {
	
	public List<MBlogCommentDto> queryCommentByMblogId(Integer mblogId);
	
	public PageDto<MBlogCommentDto> queryAllMblogComment(MBlogComment comment,MBlogInfo info,PageDto<MBlogCommentDto> page);
	
	public Integer delete (Integer id);
	
	public Integer updateDeleteStatus(Integer id);
}
