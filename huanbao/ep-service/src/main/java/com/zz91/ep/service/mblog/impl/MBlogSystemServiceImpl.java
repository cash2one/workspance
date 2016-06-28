package com.zz91.ep.service.mblog.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.mblog.MBlogCommentDao;
import com.zz91.ep.dao.mblog.MBlogDao;
import com.zz91.ep.dao.mblog.MBlogInfoDao;
import com.zz91.ep.dao.mblog.MBlogSentDao;
import com.zz91.ep.dao.mblog.MBlogSystemDao;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSent;
import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogSystemDto;
import com.zz91.ep.service.mblog.MBlogService;
import com.zz91.ep.service.mblog.MBlogSystemService;
import com.zz91.util.lang.StringUtils;
@Service("mblogSystemService")
public class MBlogSystemServiceImpl implements MBlogSystemService {
	@Resource
	private MBlogSystemDao mBlogSystemDao;
	@Resource
	private MBlogDao mBlogDao;
	@Resource
	private MBlogInfoDao mBlogInfoDao;
	@Resource
	private MBlogCommentDao mBlogCommentDao;
	@Resource
	private MBlogSentDao mBlogSentDao;
	@Resource
	private MBlogService mBlogService;
	@Override
	public Integer insert(MBlogSystem system) {
		
		return mBlogSystemDao.insert(system);
	}

	@Override
	public List<MBlogSystem> queryById(Integer toId, String type, String isRead,Integer start,Integer size) {
		List<MBlogSystem> systemsList=mBlogSystemDao.queryById(toId, type, isRead,start,size);
		return systemsList;
	}

	@Override
	public Integer queryCountById(Integer toId, String type, String isRead) {
		return mBlogSystemDao.queryCountById(toId, type, isRead);
	}

	@Override
	public Integer updateIsReadStatus(Integer id) {
		return mBlogSystemDao.updateIsReadStatus(id);
	}

	@Override
	public PageDto<MBlogSystemDto> querySystemById(Integer toId, String type,
			String isRead, PageDto<MBlogSystemDto> page) {
		List<MBlogSystemDto> systemDtoList=new ArrayList<MBlogSystemDto>();
		List<MBlogSystemDto> dtoList= mBlogSystemDao.querySystemById(toId, type, isRead, page);
		for (MBlogSystemDto mBlogSystemDto : dtoList) {
			//查询出评论的信息
			 MBlogComment tragetComment= mBlogCommentDao.queryOneCommentById(Integer.valueOf(mBlogSystemDto.getmBlogSystem().getContent()));
			 //说明是评论
			 if(tragetComment!=null && tragetComment.getTargetType().equals("1")){
				//查询出对应的博文
				MBlog mBlog=mBlogDao.queryOneById(tragetComment.getMblogId());
				mBlogSystemDto.setmBlog(mBlog);
			 }else if(tragetComment!=null && tragetComment.getTargetType().equals("2")){
				 //查询出我的评论信息
				 MBlogComment comment=mBlogCommentDao.queryOneCommentById(tragetComment.getTargetId());
				 mBlogSystemDto.setComment(comment);
				 //查询出我回复的mblogId
				 MBlog mBlog=mBlogDao.queryOneById(comment.getMblogId());
				 mBlogSystemDto.setmBlog(mBlog);
			 }
			 
			 //把他的信息插入的评论的信息
			 mBlogSystemDto.setTragetComment(tragetComment);
			 //把对象插入到info
			 MBlogInfo info=mBlogInfoDao.queryInfoById(mBlogSystemDto.getmBlogSystem().getFromId());
			 if(info==null){
				 continue;
			 }else{
			 mBlogSystemDto.setTragetInfo(info);
			 }
			 systemDtoList.add(mBlogSystemDto);
		}
		page.setRecords(systemDtoList);
		page.setTotals(mBlogSystemDao.queryCountById(toId, type, isRead));
		return page;
	}

	@Override
	public List<MBlogSystem> querySystemByisReadAndType(Integer toId,
			String type, String isRead) {
		
		return mBlogSystemDao.querySystemByisReadAndType(toId, type, isRead);
	}

	@Override
	public List<MBlogSystem> queryAnteAndCountByfromId(Integer fromId, Integer size) {
		if(size==null){
			size=10;
		}
		return mBlogSystemDao.queryAnteAndCountByfromId(fromId, size);
	}

	@Override
	public PageDto<MBlogSystemDto> queryAnteSystemById(Integer toId, String type,
			String isRead, PageDto<MBlogSystemDto> page) {
		List<MBlogSystemDto> systemDtoList=new ArrayList<MBlogSystemDto>();
		List<MBlogSystemDto> dtoList= mBlogSystemDao.querySystemById(toId, type, isRead, page);
		for (MBlogSystemDto mBlogSystemDto : dtoList) {
			
			//查询出博文的信息
			MBlog mBlog=mBlogDao.queryOneById(Integer.valueOf(mBlogSystemDto.getmBlogSystem().getContent()));
			if(mBlog==null){
				continue;
			}
			//判断是否是转发的
			MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlog.getId());
			//说明是转发
			if(sent!=null){
				//查询出源头信息
				MBlog topMBlog= mBlogDao.queryOneById(sent.getTopId());
				if(topMBlog==null){
					continue;
				}
				if(topMBlog!=null){
					if(topMBlog.getContent().indexOf('@') !=-1){
						//替换@功能
						Map<String,Object> maps=mBlogService.replceMblogAnte(topMBlog.getContent());
						for (String contentKey : maps.keySet()) {
								//查询出对应的人的id
								MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
								if(mBlogInfo!=null){
									topMBlog.setContent(topMBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									 //添加到系统表
								}else{
									topMBlog.setContent(topMBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								}
						}
						
					}
					if(topMBlog.getType().equals("2")){
						//替换话题的标题
						MBlog tailkMbBlog=mBlogDao.querymblogByTitle(topMBlog.getTitle());
						topMBlog.setContent(topMBlog.getContent().replace(topMBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+topMBlog.getTitle()+" </a>") );
					}
					mBlogSystemDto.setTragetMBlog(topMBlog);
					
					//查询是否有图片
					if(StringUtils.isNotEmpty(topMBlog.getPhotoPath())){
						List<String> photoList=new ArrayList<String>();
						String[] photos= topMBlog.getPhotoPath().split(",");
						for (int i = 0; i < photos.length; i++) {
							if(StringUtils.isNotEmpty(photos[i])){
								photoList.add(photos[i]);
							}
						}
						mBlogSystemDto.setPhotoList(photoList);
					}
					//根据umBlog查询出发布动态的人
					MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(topMBlog.getInfoId(), null);
					if(mBlogInfo!=null){
						mBlogSystemDto.setTragetInfo(mBlogInfo);
					}
				}

			}
				
			//替换@的功能
			if(mBlog.getContent().indexOf('@') !=-1){
				//替换@功能
				Map<String,Object> maps=mBlogService.replceMblogAnte(mBlog.getContent());
				for (String contentKey : maps.keySet()) {
						//查询出对应的人的id
						MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
						if(mBlogInfo!=null){
							mBlog.setContent(mBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							 //添加到系统表
						}else{
							mBlog.setContent(mBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
						}
				}
				
			}
			
			//替换话题的标题
			if(mBlog.getType().equals("2")){
			  //查询是否是第一个发布 
			  MBlog tailkMbBlog=mBlogDao.querymblogByTitle(mBlog.getTitle());
			  mBlog.setContent(mBlog.getContent().replace(mBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlog.getTitle()+" </a>") );
			}
			
			mBlogSystemDto.setmBlog(mBlog);
			//查询出对应的人
			MBlogInfo info=mBlogInfoDao.queryInfoById(mBlog.getInfoId());
			if(info==null){
				continue;
			}
			mBlogSystemDto.setInfo(info);
			//查询是否有图片
			if(StringUtils.isNotEmpty(mBlog.getPhotoPath())){
				List<String> photoList=new ArrayList<String>();
				String[] photos= mBlog.getPhotoPath().split(",");
				for (int i = 0; i < photos.length; i++) {
					if(StringUtils.isNotEmpty(photos[i])){
						photoList.add(photos[i]);
					}
				}
				mBlogSystemDto.setPhotoList(photoList);
			}
			systemDtoList.add(mBlogSystemDto);
		}
	
		page.setRecords(systemDtoList);
		page.setTotals(mBlogSystemDao.queryCountById(toId, type, isRead));
		return page;

	}

	@Override
	public PageDto<MBlogSystem> queryMessageByInfoId(Integer toId,
			String type, String isRead, PageDto<MBlogSystem> page) {
		
		List<MBlogSystem> systemList=mBlogSystemDao.queryMessageByConditions(toId, type, isRead, page);
		for (MBlogSystem mBlogSystem : systemList) {
			 mBlogSystem.setContent(mBlogSystem.getContent().split("=")[1]);
		}
		page.setRecords(systemList);
		page.setTotals(mBlogSystemDao.queryCountById(toId, type, isRead));
		return page;
	}
	
	
}
