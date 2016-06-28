package com.zz91.ep.service.mblog.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.mblog.MBlogCommentDao;
import com.zz91.ep.dao.mblog.MBlogInfoDao;
import com.zz91.ep.domain.common.Face;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;
import com.zz91.ep.service.mblog.MBlogCommentService;
import com.zz91.ep.service.mblog.MBlogService;

@Service("mblogCommentService")
public class MBlogCommentServiceImpl implements MBlogCommentService {
	
	@Resource
	private MBlogCommentDao mBlogCommentDao;
	@Resource
	private MBlogService mBlogService;
	@Resource 
	private MBlogInfoDao mBlogInfoDao;
	
	@Override
	public Integer delete(Integer id, String isDelete) {
		
		return mBlogCommentDao.delete(id, isDelete);
	}

	@Override
	public PageDto<MBlogCommentDto> queryCommentBymblogId(Integer mblogId,
			PageDto<MBlogCommentDto> page) {
		page.setRecords(mBlogCommentDao.queryCommentBymblogId(mblogId, page));
		page.setTotals(mBlogCommentDao.queryCommentCountBymblogId(mblogId));
		return page;
	}

	@Override
	public Integer queryCommentCountBymblogId(Integer mblogId) {
		return mBlogCommentDao.queryCommentCountBymblogId(mblogId);
	}

	@Override
	public Integer sentComment(MBlogComment comment) {
		return mBlogCommentDao.sentComment(comment);
	}

	@Override
	public List<MBlogCommentDto> queryMblogCommentBymblogId(Integer mblogId) {
		
		List<MBlogCommentDto> dtoList=new ArrayList<MBlogCommentDto>();
		List<MBlogCommentDto> mblogCommentDtoList= mBlogCommentDao.queryMblogCommentBymblogId(mblogId);
		
		for (MBlogCommentDto mBlogCommentDto : mblogCommentDtoList) {
			// 替换@功能
			if (mBlogCommentDto.getComment().getContent().indexOf("@") != -1) {
				Map<String, Object> maps = mBlogService.replceMblogAnte(mBlogCommentDto.getComment().getContent());

				for (String contentKey : maps.keySet()) {
					// 查询出对应的人的id
					MBlogInfo mBlogInfos = mBlogInfoDao.queryInfoByInfoByName(contentKey);
					if (mBlogInfos != null) {
						mBlogCommentDto.getComment().setContent(mBlogCommentDto.getComment().getContent()
								.replace("@" + contentKey,"<a href=\"ublog"+ mBlogInfos.getId()+ ".htm\" style=\"color:#006633;\">@"+ contentKey+ "</a>"));
						// 添加到系统表
					} else {
						mBlogCommentDto.getComment().setContent(
								mBlogCommentDto.getComment().getContent()
										.replace("@" + contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+ contentKey + "</a>"));
					}
				}
			}
			MBlogInfo mBlogInfo = mBlogInfoDao.queryInfoByInfoIdorCid(
					mBlogCommentDto.getComment().getInfoId(), null);
			if(mBlogInfo==null){
				continue;
			}
			mBlogCommentDto.setInfo(mBlogInfo);
			// 查询目标id对应的人//回复的人
			if (mBlogCommentDto.getComment().getTargetType().equals("2")) {
				MBlogComment comment = mBlogCommentDao
						.queryOneCommentById(mBlogCommentDto.getComment()
								.getTargetId());
				// 查询目标评论的人
				MBlogInfo info = mBlogInfoDao.queryInfoByInfoIdorCid(comment.getInfoId(), null);
				if(info==null){
					continue;
				}
				mBlogCommentDto.setmBlogInfo(info);
			} else {
				// 替换表情
				mBlogCommentDto.getComment().setContent(Face.getFace(mBlogCommentDto.getComment().getContent()));
			}
			dtoList.add(mBlogCommentDto);
		}
		return dtoList;
	}

	@Override
	public MBlogComment queryOneCommentById(Integer id) {
		
		return mBlogCommentDao.queryOneCommentById(id);
	}

	

}
