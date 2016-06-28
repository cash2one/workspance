package com.zz91.ep.admin.service.mblog.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.mblog.MBlogSystemDao;
import com.zz91.ep.admin.service.mblog.MBlogSystemService;
import com.zz91.ep.domain.mblog.MBlogSystem;
@Component("mblogSystemService")
public class MBlogSystemServiceImpl implements MBlogSystemService {

	@Resource
	private MBlogSystemDao mBlogSystemDao;
	
	@Override
	public Integer insert(MBlogSystem mBlogSystem) {
		
		return mBlogSystemDao.insert(mBlogSystem);
	}

}
