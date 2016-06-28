package com.zz91.ep.service.mblog.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.mblog.MBlogGroupDao;
import com.zz91.ep.domain.mblog.MBlogGroup;
import com.zz91.ep.service.mblog.MBlogGroupService;
@Service("mblogGroupService")
public class MBlogGroupServiceImpl implements MBlogGroupService {
	
	@Resource
	private MBlogGroupDao mBlogGroupDao; 
	
	@Override
	public Integer delete(Integer id, String isDelete) {
		
		return mBlogGroupDao.delete(id, isDelete);
	}

	@Override
	public Integer insert(MBlogGroup group) {
		return mBlogGroupDao.insert(group);
	}

	@Override
	public List<MBlogGroup> queryById(Integer infoId) {
		List<MBlogGroup> groupList=mBlogGroupDao.queryById(infoId);
		return groupList;
	}

	@Override
	public MBlogGroup queryOneByConditions(Integer infoId, String groupName,Integer id) {
		
		return mBlogGroupDao.queryOneByConditions(infoId, groupName, id);
	}

}
