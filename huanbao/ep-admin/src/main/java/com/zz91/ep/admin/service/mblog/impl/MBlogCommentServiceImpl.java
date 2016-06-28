package com.zz91.ep.admin.service.mblog.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.mblog.MBlogCommentDao;
import com.zz91.ep.admin.service.mblog.MBlogCommentService;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;
@Component("mblogCommentService")
public class MBlogCommentServiceImpl implements MBlogCommentService {

	@Resource
	private MBlogCommentDao mBlogCommentDao;
	
	@Override
	public List<MBlogCommentDto> queryCommentByMblogId(Integer mblogId) {
		List<MBlogCommentDto> dtoList=mBlogCommentDao.queryCommentByMblogId(mblogId);
		return dtoList;
	}

	@Override
	public PageDto<MBlogCommentDto> queryAllMblogComment(MBlogComment comment,MBlogInfo info,
			PageDto<MBlogCommentDto> page) {
		if(page.getLimit()==null){
			page.setLimit(20);
		}
		List<MBlogCommentDto> dtoList=mBlogCommentDao.queryAllMblogComment(comment,info,page);
		page.setRecords(dtoList);
		page.setTotals(mBlogCommentDao.queryAllMblogCommentCount());
		return page;
	}

	@Override
	public Integer delete(Integer id) {
		
		return mBlogCommentDao.delete(id);
	}

	@Override
	public Integer updateDeleteStatus(Integer id) {
		
		return mBlogCommentDao.updateDeleteStatus(id);
	}

}
