package com.zz91.ep.service.mblog.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.dao.mblog.MBlogDao;
import com.zz91.ep.dao.mblog.MBlogFollowDao;
import com.zz91.ep.dao.mblog.MBlogInfoDao;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogFollow;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;
import com.zz91.ep.dto.mblog.MBlogFollowDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
import com.zz91.ep.service.mblog.MBlogFollowService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;
@Service("mblogFollowService")
public class MBlogFollowServiceImpl implements MBlogFollowService {
    
	final static Integer ONE_THOUSAND = 500;
	
	@Resource
	private MBlogFollowDao mBlogFollowDao;
	@Resource
	private MBlogInfoDao mBlogInfoDao;
	@Resource
	private CompProfileDao compProfileDao;
	@Resource
	private MBlogDao mBlogDao;
	
	@Override
	public Integer insert(MBlogFollow follow) {
			
		return mBlogFollowDao.insert(follow);
	}

	@Override
	public MBlogFollow queryByIdAndTargetId(Integer infoId, Integer targetId,String followStatus) {
		return mBlogFollowDao.queryByIdAndTargetId(infoId, targetId,followStatus);
	}

//	@Override
//	public PageDto<MBlogFollow> queryFansByConditions(Integer targetId,
//			PageDto<MBlogFollow> page) {
//		page.setRecords(mBlogFollowDao.queryFansByConditions(targetId, page));
//		page.setTotals(mBlogFollowDao.queryFansCountByConditions(targetId));
//		return page;
//	}

	@Override
	public Integer queryFansCountByConditions(Integer targetId,String type) {
		
		return mBlogFollowDao.queryFansCountByConditions(targetId,type);
	}

//	@Override
//	public PageDto<MBlogFollow> queryFollowByConditions(Integer infoId,
//			Integer groupId, PageDto<MBlogFollow> page) {
////		page.setRecords(mBlogFollowDao.queryFollowByConditions(infoId, groupId, page));
////		page.setTotals(mBlogFollowDao.queryFollowCountByConditions(infoId, groupId));
//		return page;
//	}

	@Override
	public PageDto<MBlogFollow> queryFollowByIdAndType(Integer infoId,
			Integer type, PageDto<MBlogFollow> page) {
		page.setRecords(mBlogFollowDao.queryFollowByIdAndType(infoId, type, page));
		page.setTotals(mBlogFollowDao.queryFollowCountByIdAndType(infoId, type));
		return page;
	}

	@Override
	public Integer queryFollowCountByConditions(Integer infoId, Integer groupId,String type) {
		return mBlogFollowDao.queryFollowCountByConditions(infoId, groupId,type);
	}

	@Override
	public Integer queryFollowCountByIdAndType(Integer infoId, Integer type) {
		return mBlogFollowDao.queryFollowCountByIdAndType(infoId, type);
	}

	@Override
	public Integer update(Integer id, String followStatus, String type) {
		
		return mBlogFollowDao.update(id, followStatus, type);
	}

	@Override
	public Integer updateFollowGroup(Integer infoId, Integer targetId,
			Integer groupId) {
		return mBlogFollowDao.updateFollowGroup(infoId, targetId, groupId);
	}

	@Override
	public Integer updateTypeById(Integer id, String type) {
		return mBlogFollowDao.updateTypeById(id, type);
	}

	@Override
	public List<MBlogFollow> queryByInfoIdOrGroupId(Integer infoId,
			Integer groupId) {
		return mBlogFollowDao.queryByInfoIdOrGroupId(infoId, groupId);
	}

	@Override
	public List<MBlogFollow> queryFansByTargetId(Integer targetId,String type,Integer start,Integer size) {
		List<MBlogFollow> followList=mBlogFollowDao.queryFansByTargetId(targetId,type,start,size);
		return followList;
	}

	@Override
	public Integer updateFollowStatus(String type, String followStatus,
			Integer infoId, Integer targetId,Integer groupId) {
		return mBlogFollowDao.updateFollowStatus(type, followStatus, infoId, targetId,groupId);
	}

	@Override
	public List<MBlogFollow> queryFollowByConditions(Integer infoId,
			Integer groupId,String type, PageDto<MBlogInfoDto> page) {
		List<MBlogFollow> followList=mBlogFollowDao.queryFollowByConditions(infoId, groupId,type,page);
		return followList;
	}

	@Override
	public Integer updateNoteNameById(Integer infoId, Integer targetId,
			String noteName) {
		
		return mBlogFollowDao.updateNoteNameById(infoId, targetId, noteName);
	}

	@Override
	public PageDto<MBlogInfoDto> pageQueryForkeywords(Integer infoId, String keywords,PageDto<MBlogInfoDto> page) {
		if(StringUtils.isEmpty(keywords)){
			return page;
		}
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<MBlogInfoDto> dtoList = new ArrayList<MBlogInfoDto>();

		try {
			if (StringUtils.isNotEmpty(keywords)) {
				keywords = keywords.replaceAll("/", "");
				keywords = keywords.replace("%", "");
				keywords = keywords.replace("\\", "");
				keywords = keywords.replace("-", "");
				keywords = keywords.replace("(", "");
				keywords = keywords.replace(")", "");
				sb.append("@(name) ").append(keywords);
			}     
			cl.SetFilter("info_id", infoId, false);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0, page.getLimit(), ONE_THOUSAND);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_created desc");
			SphinxResult res = cl.Query(sb.toString(), "followInfoForName");
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
			} // 总数量
			page.setTotals(res.totalFound);

			for (String id : resultList) {
				
				if (StringUtils.isNotEmpty(id) && !StringUtils.isNumber(id)) {
					continue;
				}
				
				MBlogFollow follow= mBlogFollowDao.queryOneFollowById(Integer.valueOf(id));
				
				if(follow==null){
					continue;
				}
				
				MBlogInfoDto dto=new MBlogInfoDto();
				
				MBlogInfo mBlogInfo=mBlogInfoDao.queryInfoById(follow.getTargetId());
				dto.setmBlogInfo(mBlogInfo);
				if (StringUtils.isNotEmpty(mBlogInfo.getProvinceCode())) {
					dto.setProvinceName(CodeCachedUtil.getValue(
							CodeCachedUtil.CACHE_TYPE_AREA, mBlogInfo
									.getProvinceCode()));
				}
				if (StringUtils.isNotEmpty(mBlogInfo.getAreaCode())) {
					dto.setAreaName(CodeCachedUtil.getValue(
							CodeCachedUtil.CACHE_TYPE_AREA, mBlogInfo
									.getAreaCode()));
				}
				//查询公司信息
				CompProfile compProfile=compProfileDao.queryCompProfileById(mBlogInfo.getCid());
				dto.setCompProfile(compProfile);
				if (follow.getType().equals("1")) {
					dto.setType("1");
				} else {
					dto.setType("0");
				}
				if(StringUtils.isNotEmpty(follow.getNoteName()) ){
					dto.setNoteName(follow.getNoteName());
				}
				//添加粉丝数
				dto.setFansCount(mBlogFollowDao.queryFansCountByConditions(follow.getTargetId(), null));
				//查询关注数
				dto.setFollowCount(mBlogFollowDao.queryFollowCountByConditions(follow.getTargetId(), null, null));
				// 查询微博数
				dto.setMblogCount(mBlogDao.queryAllMBlogCountById(follow.getTargetId()));
				dtoList.add(dto);
			}	
			page.setRecords(dtoList);
		}catch (SphinxException e) {

			return null;
		}
		   return page;
    }
}