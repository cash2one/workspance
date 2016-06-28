package com.zz91.ep.service.mblog;

import com.zz91.ep.domain.mblog.MBlogSent;

public interface MBlogSentService {
	
    public Integer insert(MBlogSent sent);
	
	public Integer querymBlogSentCountById(Integer topId);
	
	public MBlogSent querymBlogSentByMblogId(Integer mblogId);
}
