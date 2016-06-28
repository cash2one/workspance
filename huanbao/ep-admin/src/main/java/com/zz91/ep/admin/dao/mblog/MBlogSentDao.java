package com.zz91.ep.admin.dao.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlogSent;

public interface MBlogSentDao {
public Integer insert(MBlogSent sent);
	
	public Integer querymBlogSentCountById(Integer topId);
	
	public MBlogSent querymBlogSentByMblogId(Integer mblogId);
	
	public List<MBlogSent> querytopIdByinfoId(Integer infoId);
}
