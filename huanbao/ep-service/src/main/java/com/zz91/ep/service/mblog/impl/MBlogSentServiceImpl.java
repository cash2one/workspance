package com.zz91.ep.service.mblog.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.mblog.MBlogSentDao;
import com.zz91.ep.domain.mblog.MBlogSent;
import com.zz91.ep.service.mblog.MBlogSentService;
@Service("mblogSentService")
public class MBlogSentServiceImpl implements MBlogSentService {
	
	@Resource
	private MBlogSentDao mBlogSentDao;
	
	@Override
	public Integer insert(MBlogSent sent) {
		
		return mBlogSentDao.insert(sent);
	}

	@Override
	public Integer querymBlogSentCountById(Integer topId) {
		
		return mBlogSentDao.querymBlogSentCountById(topId);
	}

	@Override
	public MBlogSent querymBlogSentByMblogId(Integer mblogId) {
		
		return mBlogSentDao.querymBlogSentByMblogId(mblogId);
	}

}
