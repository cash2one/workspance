package com.zz91.ep.service.mblog.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.mblog.MBlogDao;
import com.zz91.ep.dao.mblog.MBlogFollowDao;
import com.zz91.ep.dao.mblog.MBlogInfoDao;
import com.zz91.ep.dao.mblog.MBlogSentDao;
import com.zz91.ep.dao.mblog.MBlogSystemDao;
import com.zz91.ep.domain.common.Face;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogFollow;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSent;
import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
import com.zz91.ep.service.mblog.MBlogService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;
@Service("mblogService")
public class MBlogServiceImpl implements MBlogService {
	
	final static Integer ONE_THOUSAND = 500;
	@Resource
	private MBlogDao mblogDao;
	@Resource
	private MBlogInfoDao mBlogInfoDao;
	@Resource
	private MBlogSentDao mBlogSentDao;
	@Resource
	private MBlogFollowDao mBlogFollowDao;
	@Resource
	private MBlogSystemDao mBlogSystemDao;
	
	@Override
	public Integer delete(Integer id) {
		return mblogDao.delete(id);
	}

	@Override
	public Integer insert(MBlog mBlog) {
		return mblogDao.insert(mBlog);
	}

	@Override
	public Integer queryAllCountMBlogById(Integer infoId) {
		return mblogDao.queryAllMBlogCountById(infoId);
	}

	@Override
	public PageDto<MBlogDto> queryAllmBlogById(Integer infoId,String type, PageDto<MBlogDto> page) {

		List<MBlogDto> dtoList=mblogDao.queryAllmBlogById(infoId, page);
		List<MBlogDto> dtoPicList=new ArrayList<MBlogDto>();
		Integer count=mblogDao.queryPhotoCountByInfo(infoId);
		//显示图片列表页的
		if(StringUtils.isNotEmpty(type)){
			List<MBlogSent> mBlogList=mBlogSentDao.querytopIdByinfoId(infoId);
			for (MBlogSent mBlogSent : mBlogList) {
				MBlog mBlog=mblogDao.queryOneById(mBlogSent.getTopId());
				if(mBlog!=null && StringUtils.isNotEmpty(mBlog.getPhotoPath())){
					count++;
				}
			}
		}
		//读取有图片的博文
		if(StringUtils.isNotEmpty(type)){
			for (MBlogDto mBlogDto : dtoList) {
				//判断是否是转发的
				MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlogDto.getmBlog().getId());
				//如果是
				if(sent!=null){
				   //很据topId查询出被转发的源头id
					MBlog uMBlog= mblogDao.queryOneById(sent.getTopId());
					if(uMBlog!=null && StringUtils.isNotEmpty(uMBlog.getPhotoPath())){
						mBlogDto.setmBlog(mBlogDto.getmBlog());
						//替换@功能
						if(uMBlog.getContent().indexOf('@') !=-1){

							Map<String,Object> maps=replceMblogAnte(uMBlog.getContent());
						
							for (String contentKey : maps.keySet()) {
									if(StringUtils.isEmpty(contentKey)){
										continue;
									}
									//查询出对应的人的id
									MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
									if(mBlogInfos!=null){
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
										 //添加到系统表
									}else{
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									}
							}
					   
				        }
						if(uMBlog.getType().equals("2")){
							//查询出第一个发布的人
							MBlog tailkMbBlog=mblogDao.querymblogByTitle(uMBlog.getTitle());
							//替换话题的标题
							uMBlog.setContent(uMBlog.getContent().replace(uMBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+uMBlog.getTitle()+" </a>") );
						}
						//替换成表情
						uMBlog.setContent(Face.getFace(uMBlog.getContent()));
						//查询结果放入dto
						mBlogDto.setTragetBlog(uMBlog);
						//根据umBlog查询出发布动态的人
						MBlogInfo tragetBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(uMBlog.getInfoId(), null);
						if(tragetBlogInfo!=null){
							mBlogDto.setInfo(tragetBlogInfo);
						}
						//转发是否包含图片
						if(StringUtils.isNotEmpty(uMBlog.getPhotoPath())){
							List<MBlog> mbList=new ArrayList<MBlog>();
							String[] photos= uMBlog.getPhotoPath().split(",");
							for (int i = 0; i < photos.length; i++) {
								MBlog mBlog=new MBlog();
								if(StringUtils.isNotEmpty(photos[i])){
									mBlog.setPhotoPath(photos[i]);
									mbList.add(mBlog);
									mBlogDto.setMbList(mbList);
								}
							}
						}
						
						//替换表情
						mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
						if(mBlogDto.getmBlog().getContent().indexOf("@")!=-1){
							//替换@功能
							Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
							
							for (String contentKey : maps.keySet()) {
									if(StringUtils.isEmpty(contentKey)){
										continue;
									}
									//查询出对应的人的id
									MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
									if(mBlogInfos!=null){
										mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
										 //添加到系统表
									}else{
										mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									}
							}
						}
						//替换话题
						if(mBlogDto.getmBlog().getType().equals("2")){
							//话题截取
							MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
						}
						dtoPicList.add(mBlogDto);
					}
					
				}else if (StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())) {
					
					//只添加有图片的博文
					mBlogDto.setmBlog(mBlogDto.getmBlog());
					
					if(mBlogDto.getmBlog().getContent().indexOf("@")!=-1){
						//替换@功能
						Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
						
						for (String contentKey : maps.keySet()) {
								if(StringUtils.isEmpty(contentKey)){
									continue;
								}
								//查询出对应的人的id
								MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
								if(mBlogInfos!=null){
									mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									 //添加到系统表
								}else{
									mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								}
						}
					}
					//替换话题
					if(mBlogDto.getmBlog().getType().equals("2")){
						//话题截取
						MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
						mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
					}
					//替换表情
					mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
					
					if(StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())){
						List<MBlog> mbList=new ArrayList<MBlog>();
						String[] photos= mBlogDto.getmBlog().getPhotoPath().split(",");
						for (int i = 0; i < photos.length; i++) {
							MBlog mBlog=new MBlog();
							if(StringUtils.isNotEmpty(photos[i])){
								mBlog.setPhotoPath(photos[i]);
								mbList.add(mBlog);
								mBlogDto.setMbList(mbList);
							}
						}
					}
					MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
					if(mBlogInfo!=null){
					  mBlogDto.setInfo(mBlogInfo);
					}
					dtoPicList.add(mBlogDto);
				}
			}  
			
		}else{
				for (MBlogDto mBlogDto : dtoList) {
					//判断是否是转发的
					MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlogDto.getmBlog().getId());
					//如果是
					if(sent!=null){
					   if(mBlogDto.getmBlog().getContent().indexOf('@') !=-1){
							//替换@功能
							Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
							
							for (String contentKey : maps.keySet()) {
									if(StringUtils.isEmpty(contentKey)){
										continue;
									}
									//查询出对应的人的id
									MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
									if(mBlogInfos!=null){
										mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
										 //添加到系统表
									}else{
										mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									}
							}
		 
					   }
					   //很据topId查询出被转发的源头id
						MBlog uMBlog= mblogDao.queryOneById(sent.getTopId());
						if(uMBlog!=null){
							
							if(uMBlog.getType().equals("2")){
								//查询出第一个发布的人
								MBlog tailkMbBlog=mblogDao.querymblogByTitle(uMBlog.getTitle());
								//替换话题的标题
								uMBlog.setContent(uMBlog.getContent().replace(uMBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+uMBlog.getTitle()+" </a>") );
							}
							//先替换成表情
							uMBlog.setContent(Face.getFace(uMBlog.getContent()));
							//查询结果放入dto
							mBlogDto.setTragetBlog(uMBlog);
							//根据umBlog查询出发布动态的人
							MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(uMBlog.getInfoId(), null);
							if(mBlogInfo!=null){
							 mBlogDto.setInfo(mBlogInfo);
							}
							//转发是否包含图片
							if(StringUtils.isNotEmpty(uMBlog.getPhotoPath())){
								List<MBlog> mbList=new ArrayList<MBlog>();
								String[] photos= uMBlog.getPhotoPath().split(",");
								for (int i = 0; i < photos.length; i++) {
									MBlog mBlog=new MBlog();
									if(StringUtils.isNotEmpty(photos[i])){
										mBlog.setPhotoPath(photos[i]);
										mbList.add(mBlog);
										mBlogDto.setMbList(mbList);
									}
								}
							}
						}else{
							MBlog mBlog=new MBlog();
							mBlog.setContent("对不起，该博文已删除。");
							mBlogDto.setTragetBlog(mBlog);
						}
					}else{
						
						if(mBlogDto.getmBlog().getContent().indexOf("@")!=-1){
							//替换@功能
							Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
							
							for (String contentKey : maps.keySet()) {
									if(StringUtils.isEmpty(contentKey)){
										continue;
									}
									//查询出对应的人的id
									MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
									if(mBlogInfos!=null){
										mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
										 //添加到系统表
									}else{
										mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									}
							}
						}
						//替换话题
						if(mBlogDto.getmBlog().getType().equals("2")){
							//话题截取
							MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
						}
						
						//替换表情
						mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));

						if(StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())){
							List<MBlog> mbList=new ArrayList<MBlog>();
							String[] photos= mBlogDto.getmBlog().getPhotoPath().split(",");
							for (int i = 0; i < photos.length; i++) {
								MBlog mBlog=new MBlog();
								if(StringUtils.isNotEmpty(photos[i])){
									mBlog.setPhotoPath(photos[i]);
									mbList.add(mBlog);
									mBlogDto.setMbList(mbList);
								}
							}
						}
		
						
						MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
						if(mBlogInfo!=null){
						  mBlogDto.setInfo(mBlogInfo);
						}
						
										
					}
				}  
		}
		if(StringUtils.isNotEmpty(type)){
			page.setRecords(dtoPicList);
			page.setTotals(count);
		}else{
			page.setRecords(dtoList);
			page.setTotals(mblogDao.queryAllMBlogCountById(infoId));
		}
		
		
		return page;
	}

	@Override
	public PageDto<MBlog> queryBykeywords(String keywords, PageDto<MBlog> page) {
		return null;
	}

	@Override
	public MBlog queryOneById(Integer id) {
		
		MBlog mBlog=mblogDao.queryOneById(id);
		if(mBlog!=null){
			//替换话题
			if(mBlog.getType().equals("2")){
				//话题截取
				MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlog.getTitle());
			    mBlog.setContent(mBlog.getContent().replace(mBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlog.getTitle()+" </a>") );
			}
			//替换表情
			mBlog.setContent(Face.getFace(mBlog.getContent()));
		}
		return mBlog;
	}

	@Override
	public MBlog quyerOneMblogById(Integer id) {
		
		return null;
	}

	@Override
	public Integer updateSentCount(Integer id) {
		return mblogDao.updateSentCount(id);
	}

	@Override
	public List<MBlog> queryAllmBlogByInfoId(Integer infoId,Integer size) {
		if(size==null){
			size=15;
		}
		return mblogDao.queryAllmBlogByInfoId(infoId,size);
	}

	@Override
	public Integer updateDiscussCount(Integer id) {
		
		return mblogDao.updateDiscussCount(id);
	}

	@Override
	public List<MBlog> queryTopicTitle(Integer size) {
		if(size==null){
			size=10;
		}
		return mblogDao.queryTopicTitle(size);
	}

	@Override
	public PageDto<MBlogDto> queryMyFollowBlog(Integer infoId,
			Integer groupId,PageDto<MBlogDto> page) {
		
		List<MBlogDto>	mBlogDtoList=new ArrayList<MBlogDto>();
		List<MBlogDto> dtoList=new ArrayList<MBlogDto>();
		//查询是否纯正关系表中
		List<MBlogFollow>  follList=mBlogFollowDao.queryByInfoIdOrGroupId(infoId, groupId);
		if(follList.size()==0){
			//不存在就查询自己的博文
			dtoList=mblogDao.queryMyBlog(infoId,page);
		}else{
			dtoList= mblogDao.queryMyFollowBlog(infoId,groupId,page);
		}
		for (MBlogDto mBlogDto : dtoList) {
			//判断是否是转发
			MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlogDto.getmBlog().getId()); 
			if(sent!=null){
				//很据topId查询出被转发的源头id
				MBlog uMBlog= mblogDao.queryOneById(sent.getTopId());
				if(uMBlog==null){
					continue;
				}
				//查询备注名
				if(!mBlogDto.getmBlog().getInfoId().equals(infoId)){
					//查询出备注名字
					MBlogFollow follow=mBlogFollowDao.queryByIdAndTargetId(infoId, mBlogDto.getmBlog().getInfoId(), "1");
					if(follow!=null && StringUtils.isNotEmpty(follow.getNoteName())){
						mBlogDto.setNoteName(follow.getNoteName());
					}
				}
				//先替换成表情
				mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
				//话题截取
				if(mBlogDto.getmBlog().getType().equals("2")){
					  MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
					  mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
				}
				
				if(mBlogDto.getmBlog().getContent().indexOf('@') !=-1){
					//替换@功能
					Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
					
					for (String contentKey : maps.keySet()) {
							if(StringUtils.isEmpty(contentKey)){
								continue;
							}
							//查询出对应的人的id
							MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
							if(mBlogInfo!=null){
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								 //添加到系统表
							}else{
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							}
					}
					
				   }
					if(uMBlog!=null){
						
						if(uMBlog.getType().equals("2")){
							//查询出第一个发布的人
							MBlog tailkMbBlog=mblogDao.querymblogByTitle(uMBlog.getTitle());
							//替换话题的标题
							uMBlog.setContent(uMBlog.getContent().replace(uMBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+uMBlog.getTitle()+" </a>") );
						}
						//先替换成表情
						uMBlog.setContent(Face.getFace(uMBlog.getContent()));
						//替换成博文的@的人
						if(uMBlog.getContent().indexOf('@') !=-1){
							//替换@功能
							Map<String,Object> maps=replceMblogAnte(uMBlog.getContent());
							
							for (String contentKey : maps.keySet()) {
									if(StringUtils.isEmpty(contentKey)){
										continue;
									}
									//查询出对应的人的id
									MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
									if(uMBlog.getContent()!=null && mBlogInfo!=null){
										
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
										 //添加到系统表
									}else{
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									}
							}
							
						 }
						//查询结果放入dto
						mBlogDto.setTragetBlog(uMBlog);
						//根据umBlog查询出发布动态的人
						MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(uMBlog.getInfoId(), null);
						if(mBlogInfo!=null){
						 mBlogDto.setInfo(mBlogInfo);
						}
						//转发是否包含图片
						if(StringUtils.isNotEmpty(uMBlog.getPhotoPath())){
							List<MBlog> mbList=new ArrayList<MBlog>();
							String[] photos= uMBlog.getPhotoPath().split(",");
							for (int i = 0; i < photos.length; i++) {
								MBlog mBlog=new MBlog();
								if(StringUtils.isNotEmpty(photos[i])){
									mBlog.setPhotoPath(photos[i]);
									mbList.add(mBlog);
									mBlogDto.setMbList(mbList);
								}
							}
						}
					}
					//如果转发被删除
					if(uMBlog==null){
						//查询出自己的发布
						MBlogInfo myInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
						if(myInfo==null){
							continue;
						}
						if(myInfo!=null){
						mBlogDto.setInfo(myInfo);
						}
					}
					
					//如果是转发要添加转发的的人的用户信息
					MBlogInfo sentInfo=mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
					if(sentInfo==null){
						continue;
					}
					mBlogDto.setSentInfo(sentInfo);
					mBlogDtoList.add(mBlogDto);
			}else{
				//查询备注名
				if(!mBlogDto.getmBlog().getInfoId().equals(infoId)){
					//查询出备注名字
					MBlogFollow follow=mBlogFollowDao.queryByIdAndTargetId(infoId, mBlogDto.getmBlog().getInfoId(), "1");
					if(follow!=null && StringUtils.isNotEmpty(follow.getNoteName())){
						mBlogDto.setNoteName(follow.getNoteName());
					}
				}
				
				//话题截取
				if(mBlogDto.getmBlog().getType().equals("2")){
					 MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
					 mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
				}
				
				//替换表情
				mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
				if(mBlogDto.getmBlog().getContent().indexOf("@")!=-1){
					//替换@功能
					Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
					
					for (String contentKey : maps.keySet()) {
							if(StringUtils.isEmpty(contentKey)){
								continue;
							}
							//查询出对应的人的id
							MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
							
							if(mBlogInfo!=null){
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								 //添加到系统表
							}else{
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							}
					}
				}
				
				
				if(StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())){
					List<MBlog> mbList=new ArrayList<MBlog>();
					String[] photos= mBlogDto.getmBlog().getPhotoPath().split(",");
					for (int i = 0; i < photos.length; i++) {
						MBlog mBlog=new MBlog();
						if(StringUtils.isNotEmpty(photos[i])){
							mBlog.setPhotoPath(photos[i]);
							mbList.add(mBlog);
							mBlogDto.setMbList(mbList);
						}
					}
				}
				
				MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
				if(mBlogInfo==null){
					continue;
				}
				if(mBlogInfo!=null){
				 mBlogDto.setInfo(mBlogInfo);
				}
				mBlogDtoList.add(mBlogDto);
			}
		}
		page.setRecords(mBlogDtoList);
		
		if(follList.size()==0){
			page.setTotals(mblogDao.queryMyBlogCount(infoId));
		}else{
			page.setTotals(mblogDao.queryMyFollowBlogCount(infoId, groupId));	
		}
		return page;
	}

	@Override
	public PageDto<MBlogDto> queryAllBlog(PageDto<MBlogDto> page) {
		
		List<MBlogDto> dtoAllMblogList=new ArrayList<MBlogDto>(); 
 		List<MBlogDto> dtoList=mblogDao.queryAllBlog(page);
		for (MBlogDto mBlogDto : dtoList) {
			//判断是否是转发
			MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlogDto.getmBlog().getId()); 
			if(sent!=null){
				//很据topId查询出被转发的源头id
				MBlog uMBlog= mblogDao.queryOneById(sent.getTopId());
				if(uMBlog==null){
					continue;
				}
				if(mBlogDto.getmBlog().getType().equals("2")){
					//查询是否是第一个发布 
					  MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
					  mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );  
				}
				//先替换成表情
				mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
				
				if(mBlogDto.getmBlog().getContent().indexOf('@') !=-1){
					 
					//替换@功能
					Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
					
					for (String contentKey : maps.keySet()) {
							if(StringUtils.isEmpty(contentKey)){
								continue;
							}
							//查询出对应的人的id
							MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
							if(mBlogInfo!=null){
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								 //添加到系统表
							}else{
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							}
					}
				}
				    
					if(uMBlog!=null){
						
						if(uMBlog.getType().equals("2")){
							//替换话题的标题
							MBlog tailkMbBlog=mblogDao.querymblogByTitle(uMBlog.getTitle());
							uMBlog.setContent(uMBlog.getContent().replace(uMBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+uMBlog.getTitle()+" </a>") );
						}
						//先替换成表情
						uMBlog.setContent(Face.getFace(uMBlog.getContent()));
						//替换成博文的@的人
						if(uMBlog.getContent().indexOf('@') !=-1){
							//替换@功能
							Map<String,Object> maps=replceMblogAnte(uMBlog.getContent());
							
							for (String contentKey : maps.keySet()) {
									if(StringUtils.isEmpty(contentKey)){
										continue;
									}
									//查询出对应的人的id
									MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
									if(uMBlog.getContent()!=null && mBlogInfo!=null){
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
										 //添加到系统表
									}else{
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									}
							}
							
						 }
						//查询结果放入dto
						mBlogDto.setTragetBlog(uMBlog);
						
						//转发是否包含图片
						if(StringUtils.isNotEmpty(uMBlog.getPhotoPath())){
							List<MBlog> mbList=new ArrayList<MBlog>();
							String[] photos= uMBlog.getPhotoPath().split(",");
							for (int i = 0; i < photos.length; i++) {
								MBlog mBlog=new MBlog();
								if(StringUtils.isNotEmpty(photos[i])){
									mBlog.setPhotoPath(photos[i]);
									mbList.add(mBlog);
									mBlogDto.setMbList(mbList);
								}
							}
						}
						
						//根据umBlog查询出发布动态的人
						MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(uMBlog.getInfoId(), null);
						if(mBlogInfo!=null){
						 mBlogDto.setInfo(mBlogInfo);
						}
					}
					//如果转发被删除
					if(uMBlog==null){
						//查询出自己的发布
						MBlogInfo myInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
						if(myInfo!=null){
						 mBlogDto.setInfo(myInfo);
						}
					}
					
					//如果是转发要添加转发的的人的用户信息
					MBlogInfo sentInfo=mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
					mBlogDto.setSentInfo(sentInfo);
					dtoAllMblogList.add(mBlogDto);
					
			}else{
				
				
				if(mBlogDto.getmBlog().getContent().indexOf("@")!=-1){
					//替换@功能
					Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
					
					for (String contentKey : maps.keySet()) {
							if(StringUtils.isEmpty(contentKey)){
								continue;
							}
							//查询出对应的人的id
							MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
							if(mBlogInfo!=null){
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								 //添加到系统表
							}else{
								mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							}
					}
				}	
				//替换话题的标题
				if(mBlogDto.getmBlog().getType().equals("2")){
					
				  //查询是否是第一个发布 
				  MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
				  mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
				}
				//先替换成表情
				mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
				if(StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())){
					List<MBlog> mbList=new ArrayList<MBlog>();
					String[] photos= mBlogDto.getmBlog().getPhotoPath().split(",");
					for (int i = 0; i < photos.length; i++) {
						MBlog mBlog=new MBlog();
						if(StringUtils.isNotEmpty(photos[i])){
							mBlog.setPhotoPath(photos[i]);
							mbList.add(mBlog);
							mBlogDto.setMbList(mbList);
						}
					}
				}
				
				MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
				if(mBlogInfo!=null){
				 mBlogDto.setInfo(mBlogInfo);
				}
				dtoAllMblogList.add(mBlogDto);
			}
			
		}
		page.setRecords(dtoAllMblogList);
		page.setTotals(mblogDao.queryAllBlogCount());
		return page;
	}

	@Override
	public Integer queryCountBlogByTime(String gmtCreated) {
		
		return mblogDao.queryCountBlogByTime(gmtCreated);
	}

	//替换话题的链接count
	@SuppressWarnings("unused")
	private String replceContent(String contents){
		    //判断是否是话题 截取##号之间的值
	    	String title="";
	    	//首先截取第一个#号
	    	Integer idx=contents.indexOf("#");
	    	Integer count=0;//用于标记是否是当前的#
	    	for (int i = idx; i < contents.length()-1; i++) {
	    		//如果当前#的下一个是#就让他继续
			if(contents.charAt(i)=='#' && contents.charAt(i+1)=='#'){
				continue;
			}else{
				title+=contents.charAt(i);
				count++;
				if(i+1==contents.length()-1 && contents.charAt(i+1)!='#'){
					title="";
					break;
				}
				if(count!=1 && contents.charAt(i+1)=='#'){
					title=title+contents.charAt(i+1);
					break;
				}
			}
		}
		
		return title;
	}

	@Override
	public MBlog querymblogByTitle(String title) {
		
		return mblogDao.querymblogByTitle(title);
	}

	@Override
	public Integer querytopicCountByInfo(String title) {
		   
		return mblogDao.querytopicCountByInfo(title);
	}

	@Override
	public List<MBlogDto> querytopicbyInfo(String title, Integer size) {
		if(size==null){
			size=20;
		}
		List<MBlogDto> dtoList=mblogDao.querytopicbyInfo(title, size);
		for (MBlogDto mBlogDto : dtoList) {
			//根据博文的infoId查询出对应的人
			MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
			if(mBlogInfo!=null){ 
			  mBlogDto.setInfo(mBlogInfo);
			}
			if(StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())){
				List<MBlog> mbList=new ArrayList<MBlog>();
				String[] photos= mBlogDto.getmBlog().getPhotoPath().split(",");
				for (int i = 0; i < photos.length; i++) {
					MBlog mBlog=new MBlog();
					if(StringUtils.isNotEmpty(photos[i])){
						mBlog.setPhotoPath(photos[i]);
						mbList.add(mBlog);
						mBlogDto.setMbList(mbList);
					}
				}
			}
			
			if(mBlogDto.getmBlog().getContent().indexOf("@")!=-1){
				
				//替换@功能
				Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
				
				for (String contentKey : maps.keySet()) {
						if(StringUtils.isEmpty(contentKey)){
							continue;
						}
						//查询出对应的人的id
						MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
						if(mBlogInfos!=null){
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							 //添加到系统表
						}else{
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
						}
				}
			}
			//查询出第一个发布的人的id
			MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
			mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
			//先替换成表情
			mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
		}
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List queryMblogPhoto(String photos) {
		List photoList=new ArrayList();
		String[] photo=photos.split(",");
		for (int i = 0; i < photo.length; i++) {
			if(StringUtils.isNotEmpty(photo[i])){
				photoList.add(photo[i]);
				
			} 
		}
		return photoList;
	}

	@Override
	public MBlogDto queryOneBymblogId(Integer id) {
		
		//查询出博文
		MBlogDto mBlogDto=mblogDao.queryOneBymblogId(id);
	    
		//判断是否是转发的
		MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlogDto.getmBlog().getId());
		
		if(sent!=null){
			
			if(mBlogDto.getmBlog().getType().equals("2")){
				//查询是否是第一个发布 
				  MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
				  mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );  
				
			}
			//先替换成表情
			mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
			if(mBlogDto.getmBlog().getContent().indexOf('@') !=-1){
				//替换@功能
				Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
				
				for (String contentKey : maps.keySet()) {
						if(StringUtils.isEmpty(contentKey)){
							continue;
						}
						//查询出对应的人的id
						MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
						if(mBlogInfos!=null){
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							 //添加到系统表
						}else{
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
						}
				}
				
//				   String name=mBlogDto.getmBlog().getContent().substring(mBlogDto.getmBlog().getContent().indexOf('@')+1,mBlogDto.getmBlog().getContent().indexOf(':'));
//				   //根据名字转发
//				   MBlogInfo info=mBlogInfoDao.queryInfoByInfoByName(name);                                    
//				 //  mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replaceAll("@"+name, "<a href=\"#springUrl('/ublog')"+info.getId()+".htm\" style=\"color:#006633\">@"+name+"</a>"));
//				   mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replaceAll("@"+name, "<a href=\"ublog"+info.getId()+".htm\" style=\"color:#006633\">@"+name+"</a>")); 
			   }
			   //很据topId查询出被转发的源头id
				MBlog uMBlog= mblogDao.queryOneById(sent.getTopId());
				if(uMBlog!=null){
					//先替换成表情
					uMBlog.setContent(Face.getFace(uMBlog.getContent()));
					if(uMBlog.getType().equals("2")){
						//替换话题的标题
						MBlog tailkMbBlog=mblogDao.querymblogByTitle(uMBlog.getTitle());
						uMBlog.setContent(uMBlog.getContent().replace(uMBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+uMBlog.getTitle()+" </a>") );
					}
					
					//替换成博文的@的人
					if(uMBlog.getContent().indexOf('@') !=-1){
						//替换@功能
						Map<String,Object> maps=replceMblogAnte(uMBlog.getContent());
						
						for (String contentKey : maps.keySet()) {
								if(StringUtils.isEmpty(contentKey)){
									continue;
								}
								//查询出对应的人的id
								MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
								if(uMBlog.getContent()!=null && mBlogInfo!=null){
									uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									 //添加到系统表
								}else{
									uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								}
						}
						
					 }
					//查询结果放入dto
					mBlogDto.setTragetBlog(uMBlog);
					//根据umBlog查询出发布动态的人
					MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(uMBlog.getInfoId(), null);
					if(mBlogInfo!=null){
					 mBlogDto.setInfo(mBlogInfo);
					}
				}
				//如果是转发要添加转发的的人的用户信息
				MBlogInfo sentInfo=mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
				mBlogDto.setSentInfo(sentInfo);
				//转发是否包含图片
				if(StringUtils.isNotEmpty(uMBlog.getPhotoPath())){
					List<MBlog> mbList=new ArrayList<MBlog>();
					String[] photos= uMBlog.getPhotoPath().split(",");
					for (int i = 0; i < photos.length; i++) {
						MBlog mBlog=new MBlog();
						if(StringUtils.isNotEmpty(photos[i])){
							mBlog.setPhotoPath(photos[i]);
							mbList.add(mBlog);
							mBlogDto.setMbList(mbList);
						}
					}
				}
		}else{
		
            if(mBlogDto.getmBlog().getContent().indexOf("@")!=-1){ 
				//替换@功能
				Map<String,Object> maps=replceMblogAnte(mBlogDto.getmBlog().getContent());
				
				for (String contentKey : maps.keySet()) {
						if(StringUtils.isEmpty(contentKey)){
							continue;
						}
						//查询出对应的人的id
						MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
						if(mBlogInfos!=null){
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							 //添加到系统表
						}else{
							mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
						}
				}
            }
			//替换话题
			if(mBlogDto.getmBlog().getType().equals("2")){
				//话题截取
				MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlogDto.getmBlog().getTitle());
			    mBlogDto.getmBlog().setContent(mBlogDto.getmBlog().getContent().replace(mBlogDto.getmBlog().getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlogDto.getmBlog().getTitle()+" </a>") );
			}
			//替换表情
			mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
		}
		
		return mBlogDto;
	}

	//用于截取出@的人
	@Override   
	public Map<String, Object> replceMblogAnte(String contents) {
		contents = contents.trim();
		//截取出@的人
		//主要判断“@”或“ ”（空格）的次数
		int flg = 0;
		//临时截取字符串存放的地址  
		String newContent = "";
		String resultContent = "";
		Map<String, Object> map=new HashMap<String, Object>();
		//逐个读取contents值
		for (int i = contents.indexOf("@") ; i < contents.length(); i++ ) {
			if (contents.charAt(i) == '<' && contents.charAt(i+1) =='i'){
				int idx = i;
				for (int j = idx; ;j++) {
					if (contents.charAt(j-1) == '/' && contents.charAt(j)=='>') {
						i = j;
						break;
					}
				}
			} 
			//当不是走到最后一个字符,切flg==2时，即是第二个“@”或空格，则截取到想要的字符串
			if (flg != 2 && i != contents.length()-1) {
				//主要截取字符串并添加到临时存放的newContent
				newContent += contents.charAt(i);
				//当当前的字符是“@”或者空格则将flg标签加1
				if (contents.charAt(i) == '@' || contents.charAt(i) == ' ' || contents.charAt(i) ==':' || contents.charAt(i) =='/') {
					flg += 1;
					if (contents.charAt(i)=='/' && flg == 1) {
						flg = 0;
						continue;
					}
				}
			} 
			if (flg ==2 || i == contents.length()-1) {
				//当走到最后一个字符需要截取的字符串
				if (i == contents.length()-1) {
					if ( newContent.startsWith("@")) {
						resultContent = newContent + contents.charAt(i);
					    //resultContent += newContent + contents.charAt(i);
						resultContent = resultContent.replace("@", "").replace(":", "").replace("/", "").replace(">", "");
						
						map.put(resultContent.replace(" ", ""), resultContent.replace(" ", ""));
					}
					break;
				}else{
				//由于第一次截取的newContent是含有“@”，而之后则不会含有“@”
//				if (newContent.startsWith("@")) {
//					//resultContent += newContent.substring(1) + ",";
					resultContent = newContent;
					resultContent = resultContent.replace("@", "").replace(":", "").replace("/", "").replace(">", "");
				    map.put(resultContent.replace(" ", ""), resultContent.replace(" ", ""));
				}    
//				} else {
//					//resultContent += newContent + ",";
//					resultContent = newContent;
//					map.put(resultContent.replace(" ", ""), resultContent.replace(" ", ""));
//				}
				
				newContent = "";
				flg = 0;
//				char c = contents.charAt(i);
/*				if (contents.charAt(i)!='/') {
					i--;
				}
*/				
			}
		}
		
		return map;
	}

	@Override
	public Integer updateMblogIsDeleteStatus(Integer id, String isDelete) {
		
		return mblogDao.updateMblogIsDeleteStatus(id, isDelete);
	}

	@Override
	public PageDto<MBlogDto> pageBySearchEngine(String keywords,
			PageDto<MBlogDto> page) {
		if(StringUtils.isEmpty(keywords)){
			return page;
		}
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<MBlogDto> dtoList = new ArrayList<MBlogDto>();

		try {
			if (StringUtils.isNotEmpty(keywords)) {
				keywords = keywords.replaceAll("/", "");
				keywords = keywords.replace("%", "");
				keywords = keywords.replace("\\", "");
				keywords = keywords.replace("-", "");
				keywords = keywords.replace("(", "");
				keywords = keywords.replace(")", "");
				sb.append("@(content) ").append(keywords);
			}                
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0, page.getLimit(), ONE_THOUSAND);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_created desc");
			SphinxResult res = cl.Query(sb.toString(), "mblogForContent");
			if (res == null) {
				return page;
			}
			List<String> resultList = new ArrayList<String>();
			Integer preValue = res.totalFound;
			if (preValue != null || preValue != 0) {
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					resultList.add(String.valueOf("" + info.docId));
				}
			}
			// 总数量
			page.setTotals(res.totalFound);

			for (String id : resultList) {

				if (StringUtils.isNotEmpty(id) && !StringUtils.isNumber(id)) {
					continue;
				}
				
				MBlog mBlog = mblogDao.queryOneById(Integer.valueOf(id));
				
				if(mBlog==null){
					continue;
				}
				
				MBlogDto mBlogDto=new MBlogDto();
				
				
				//截取出@的人
	            if(mBlog.getContent().indexOf("@")!=-1){ 
					//替换@功能
					Map<String,Object> maps=replceMblogAnte(mBlog.getContent());
					
					for (String contentKey : maps.keySet()) {
							if(StringUtils.isEmpty(contentKey)){
								continue;
							}
							//查询出对应的人的id
							MBlogInfo mBlogInfos=mBlogInfoDao.queryInfoByInfoByName(contentKey);
							if(mBlogInfos!=null){
								mBlog.setContent(mBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfos.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
								 //添加到系统表
							}else{
								mBlog.setContent(mBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
							}
					}
	            }
	            //显示亮
	            if(mBlog.getContent().indexOf(keywords)!=-1){
	            	mBlog.setContent(mBlog.getContent().replace(keywords, "<span class=\"red\">"+keywords+"</span>"));
				}
	            
				//查询是否是话题
				if(mBlog.getType().equals("2")){
					 //查询是否是第一个发布 
					 MBlog tailkMbBlog=mblogDao.querymblogByTitle(mBlog.getTitle());
					 mBlog.setContent(mBlog.getContent().replace(mBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+mBlog.getTitle()+" </a>") );  
				}
				
				//先替换成表情
				mBlog.setContent(Face.getFace(mBlog.getContent()));
				
				mBlogDto.setmBlog(mBlog);
				
				//查询是是否含有图片
				if(StringUtils.isNotEmpty(mBlog.getPhotoPath())){
					List<String> sList=new ArrayList<String>();
					String[] photos= mBlog.getPhotoPath().split(",");
					for (int i = 0; i < photos.length; i++) {
						if(StringUtils.isNotEmpty(photos[i])){
							sList.add(photos[i]);
							
						}
					}
					mBlogDto.setsList(sList);
				}
				//添加发布博文的人的信息
				MBlogInfo info = mBlogInfoDao.queryInfoByInfoIdorCid(mBlog.getInfoId(), null);
				mBlogDto.setInfo(info);
				
				//判断是否是转发的
				MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlog.getId());
				if(sent!=null){
				   //很据topId查询出被转发的源头id
					MBlog uMBlog= mblogDao.queryOneById(sent.getTopId());
					if(uMBlog!=null){
						//先替换成表情
						uMBlog.setContent(Face.getFace(uMBlog.getContent()));
						if(uMBlog.getType().equals("2")){
							//替换话题的标题
							MBlog tailkMbBlog=mblogDao.querymblogByTitle(uMBlog.getTitle());
							uMBlog.setContent(uMBlog.getContent().replace(uMBlog.getTitle(), "<a href=\"topic"+tailkMbBlog.getId()+".htm\" style= \"color:#0078b6\">"+uMBlog.getTitle()+" </a>") );
						}
						
						//替换成博文的@的人
						if(uMBlog.getContent().indexOf('@') !=-1){
							//替换@功能
							Map<String,Object> maps=replceMblogAnte(uMBlog.getContent());
							
							for (String contentKey : maps.keySet()) {
									if(StringUtils.isEmpty(contentKey)){
										continue;
									}
									//查询出对应的人的id
									MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoByInfoByName(contentKey);
									if(uMBlog.getContent()!=null && mBlogInfo!=null){
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"ublog"+mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
										 //添加到系统表
									}else{
										uMBlog.setContent(uMBlog.getContent().replace("@"+contentKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+contentKey+"</a>"));
									}
							}
							
						 }
						//显示亮
						if(uMBlog.getContent().indexOf(keywords)!=-1){
							uMBlog.setContent(uMBlog.getContent().replace(keywords, "<span class=\"red\">"+keywords+"</span>"));
						}
						//查询结果放入dto
						mBlogDto.setTragetBlog(uMBlog);
						//根据umBlog查询出发布动态的人
						MBlogInfo sentInfo =mBlogInfoDao.queryInfoByInfoIdorCid(uMBlog.getInfoId(), null);
						if(sentInfo!=null){
						 mBlogDto.setSentInfo(sentInfo);
						}
						//替换图片
						//转发是否包含图片
						if(StringUtils.isNotEmpty(uMBlog.getPhotoPath())){
							List<String> sList=new ArrayList<String>();
							String[] photos= uMBlog.getPhotoPath().split(",");
							for (int i = 0; i < photos.length; i++) {
									sList.add(photos[i]);
							}
							mBlogDto.setsList(sList);
						}
						
					}
					
				}
				dtoList.add(mBlogDto);
			}
			// 数据集合
			page.setRecords(dtoList);

		} catch (SphinxException e) {

			return null;
		}
		
		return page;
	}

	@Override
	public PageDto<MBlogDto> queryMBlog(PageDto<MBlogDto> page) {
		List<MBlogDto> dtoAllMblogList=new ArrayList<MBlogDto>(); 
 		List<MBlogDto> dtoList=mblogDao.queryAllBlog(page);
		for (MBlogDto mBlogDto : dtoList) {
			//判断是否是转发
			MBlogSent sent=mBlogSentDao.querymBlogSentByMblogId(mBlogDto.getmBlog().getId()); 
			if(sent!=null){
				//很据topId查询出被转发的源头id
				MBlog uMBlog= mblogDao.queryOneById(sent.getTopId());
				if(uMBlog==null){
					continue;
				}
				//先替换成表情
				mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
					//如果转发被删除
					if(uMBlog==null){
						//查询出自己的发布
						MBlogInfo myInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
						if(myInfo!=null){
						 mBlogDto.setInfo(myInfo);
						}
					}
					
					//如果是转发要添加转发的的人的用户信息
					MBlogInfo sentInfo=mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
					mBlogDto.setSentInfo(sentInfo);
					dtoAllMblogList.add(mBlogDto);
					
			}else{
				
				//先替换成表情
				mBlogDto.getmBlog().setContent(Face.getFace(mBlogDto.getmBlog().getContent()));
				//查询出发布信息的人
				MBlogInfo mBlogInfo =mBlogInfoDao.queryInfoByInfoIdorCid(mBlogDto.getmBlog().getInfoId(), null);
				if(mBlogInfo!=null){
				 mBlogDto.setInfo(mBlogInfo);
				}
				dtoAllMblogList.add(mBlogDto);
			}
			
		}
		page.setRecords(dtoAllMblogList);
		page.setTotals(mblogDao.queryAllBlogCount());
		return page;
	}

	
	
}
