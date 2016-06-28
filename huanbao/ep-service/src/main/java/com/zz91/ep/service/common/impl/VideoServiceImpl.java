package com.zz91.ep.service.common.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.VideoDao;
import com.zz91.ep.domain.common.Video;
import com.zz91.ep.service.common.VideoService;

/**
 *	author:kongsj
 *	date:2013-8-3
 */
@Component("videoService")
public class VideoServiceImpl implements VideoService{
	
	@Resource
	private VideoDao videoDao;

	@Override
	public Integer insert(Video video) {
		return videoDao.insert(video);
	}

	@Override
	public Video queryByTypeAndId(Integer targetId, String targetType) {
		return videoDao.queryByTypeAndId(targetId, targetType);
	}

	@Override
	public Integer update(Video video) {
		return videoDao.update(video);
	}
	
}
