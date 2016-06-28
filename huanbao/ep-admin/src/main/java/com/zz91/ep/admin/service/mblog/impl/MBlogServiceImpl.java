package com.zz91.ep.admin.service.mblog.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.mblog.MBlogDao;

import com.zz91.ep.admin.service.mblog.MBlogService;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;
import com.zz91.util.lang.StringUtils;
@Component("mblogService")
public class MBlogServiceImpl implements MBlogService {
	
	@Resource
	private MBlogDao mBlogDao;

	@Override
	public PageDto<MBlogDto> queryAllMBlog(MBlog mBlog,MBlogInfo info,PageDto<MBlogDto> page) {
		if(page.getLimit()==null){
			page.setLimit(20);
		}
		List<MBlogDto> mblogdtoList=mBlogDao.queryAllMBlog(mBlog,info,page);
		//查询图片
		for (MBlogDto mBlogDto : mblogdtoList) {
			if(StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())){
				List<String> sList=new ArrayList<String>();
				String[] photos= mBlogDto.getmBlog().getPhotoPath().split(",");
				for (int i = 0; i < photos.length; i++) {
					if(StringUtils.isNotEmpty(photos[i])){
						sList.add(photos[i]);
					}
				}
				mBlogDto.setsList(sList);
			}
		}
		 
		page.setRecords(mblogdtoList);
		page.setTotals(mBlogDao.queryMBlogCount());
		 
		return page;
	}

	@Override
	public Integer queryMBlogCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MBlog queryOneMBlogById(Integer id) {
		
		return mBlogDao.queryOneMBlogById(id);
	}

	@Override
	public Integer updateMBlog(MBlog mBlog) {
		
		return mBlogDao.updateMBlog(mBlog);
	}

	@Override
	public Integer delete(Integer id) {
		
		return mBlogDao.delete(id);
	}

	@Override
	public PageDto<MBlogDto> queryAllTopic(MBlog mBlog, MBlogInfo info,
			PageDto<MBlogDto> page) {
		if(page.getLimit()==null){
			page.setLimit(20);
		}
		List<MBlogDto> mblogdtoList=mBlogDao.queryAllTopic(mBlog, info, page);
		//查询图片
		for (MBlogDto mBlogDto : mblogdtoList) {
			if(StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())){
				List<String> sList=new ArrayList<String>();
				String[] photos= mBlogDto.getmBlog().getPhotoPath().split(",");
				for (int i = 0; i < photos.length; i++) {
					if(StringUtils.isNotEmpty(photos[i])){
						sList.add(photos[i]);
					}
				}
				mBlogDto.setsList(sList);
			}
		}
		page.setRecords(mblogdtoList);
		page.setTotals(mBlogDao.queryAllTopicCount());
		 
		return page;

	}

	@Override
	public Integer queryAllTopicCount() {
		
		return mBlogDao.queryAllTopicCount();
	}

	@Override
	public List<MBlogDto> queryTopicByTitle(String title) {
		
		return mBlogDao.queryTopicByTitle(title);
	}

	@Override
	public Integer updateDelete(Integer id) {
		
		return mBlogDao.updateDelete(id);
	}

}
