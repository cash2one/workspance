package com.zz91.ep.service.common;

import com.zz91.ep.domain.common.Video;

/**
 *	author:kongsj
 *	date:2013-8-3
 */
public interface VideoService {
	
	final static String TYPE_NEWS = "1";
	
	public Video queryByTypeAndId(Integer targetId, String targetType);

	public Integer update(Video video);

	public Integer insert(Video video);
}
