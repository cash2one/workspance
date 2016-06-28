package com.zz91.ep.admin.service.common.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.common.VideoDao;
import com.zz91.ep.admin.service.common.VideoService;
import com.zz91.ep.domain.common.Video;

/**
 *	author:kongsj
 *	date:2013-8-3
 */
@Component("videoService")
public class VideoServiceImpl implements VideoService{
	
	@Resource
	private VideoDao videoDao;

	@Override
	public Integer insert(Integer targetId,String targetType,String content,String photoCover) {
		Video video = new Video();
		video.setContent(content);
		video.setTargetId(targetId);
		video.setTargetType(targetType);
		video.setPhotoCover(photoCover);
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
