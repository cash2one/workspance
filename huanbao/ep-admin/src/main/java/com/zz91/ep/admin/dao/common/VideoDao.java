package com.zz91.ep.admin.dao.common;

import com.zz91.ep.domain.common.Video;

/**
 * author:kongsj 
 * date:2013-8-3
 */
public interface VideoDao {
	public Integer delete(Integer id);

	public Video queryByTypeAndId(Integer targetId, String targetType);

	public Integer update(Video video);

	public Integer insert(Video video);
}
