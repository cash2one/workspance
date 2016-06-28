package com.zz91.ep.service.mblog.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.dao.mblog.MBlogDao;
import com.zz91.ep.dao.mblog.MBlogFollowDao;
import com.zz91.ep.dao.mblog.MBlogInfoDao;
import com.zz91.ep.dao.mblog.MBlogSystemDao;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogFollow;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
import com.zz91.ep.service.mblog.MBlogInfoService;
import com.zz91.ep.service.mblog.MBlogSystemService;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;
@Service("mblogInfoService")
public class MBlogInfoServiceImpl implements MBlogInfoService {

	final static Integer ONE_THOUSAND = 500;
	
	@Resource
	private MBlogInfoDao mBlogInfoDao;
	@Resource
	private MBlogDao mBlogDao;	
	@Resource
	private MBlogSystemDao mBlogSystemDao;
	@Resource
	private CompProfileDao compProfileDao;
	@Resource 
	private MBlogFollowDao mBlogFollowDao;
	
	@Override
	public Integer insert(MBlogInfo mBlogInfo) {
		return mBlogInfoDao.insert(mBlogInfo);
	}

	@Override
	public MBlogInfoDto queryInfoById(Integer id) {
		MBlogInfoDto infoDto=new MBlogInfoDto();
		MBlogInfo info=mBlogInfoDao.queryInfoById(id);
		if(info!=null){
			infoDto.setmBlogInfo(info);
			if (StringUtils.isNotEmpty(info.getProvinceCode())) {
				infoDto.setProvinceName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, info
								.getProvinceCode()));
			}
			if (StringUtils.isNotEmpty(info.getAreaCode())) {
				infoDto.setAreaName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, info
								.getAreaCode()));
			}
		}
		return infoDto;
	}

	@Override
	public PageDto<MBlogInfo> queryInfoBykeywords(String keywords,
			PageDto<MBlogInfo> page) {
		return null;
	}

	@Override
	public Integer queryInfoCountByName(String name) {
		return null;
	}

	@Override
	public Integer update(MBlogInfo mBlogInfo) {
		return mBlogInfoDao.update(mBlogInfo);
	}

	@Override
	public Integer updateIsDeleteStatus(String account, String isDelete) {
		return mBlogInfoDao.updateIsDeleteStatus(account, isDelete);
	}

	@Override
	public List<MBlogInfo> queryInfobyAreaCode(String areaCode,
			String provinceCode) {
		
		return mBlogInfoDao.queryInfobyAreaCode(areaCode, provinceCode);
	}

	@Override
	public MBlogInfo queryInfoByInfoIdorCid(Integer infoId,Integer cid) {
		
		return mBlogInfoDao.queryInfoByInfoIdorCid(infoId,cid);
	}

	@Override
	public MBlogInfo queryInfoByInfoByName(String name) {
		
		return mBlogInfoDao.queryInfoByInfoByName(name);
	}

	@Override
	public List<MBlogInfo> queryInfoBytopIcTitle(String title, Integer size) {
		if(size==null){
			size=16;
		}
		List<MBlogInfo> infoList=new ArrayList<MBlogInfo>();
		List<MBlog> mblogList= mBlogDao.queryInfoBytopIcTitle(title, size);
		for (MBlog mBlog : mblogList) {
			 MBlogInfo info=mBlogInfoDao.queryInfoByInfoIdorCid(mBlog.getInfoId(), null);
			 infoList.add(info);
		}
		return infoList;
	}

	@Override
	public List<MBlogInfo> queryAnetInfo(Integer fromId, Integer size) {
		if(size==null){
			size=10;
		}
		List<MBlogInfo> infoList=new ArrayList<MBlogInfo>();
		List<MBlogSystem> systemAnteList = mBlogSystemDao.queryAnteAndCountByfromId(fromId, size);
		for (MBlogSystem mBlogSystem : systemAnteList) {
			 MBlogInfo info=mBlogInfoDao.queryInfoByInfoIdorCid(mBlogSystem.getToId(), null);
			 if(info==null){
				 continue;
			 }
			 if(!fromId.equals(info.getId())){
				 infoList.add(info);
			 }
		}
		return infoList;
	}

	@Override
	public PageDto<MBlogInfoDto> pageBySearchEngine(String keywords,
			PageDto<MBlogInfoDto> page,Integer infoId) {
	
		if(StringUtils.isEmpty(keywords) ){
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
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0, page.getLimit(), ONE_THOUSAND);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_created desc");
			SphinxResult res = cl.Query(sb.toString(), "mblogInfoForName");
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
				MBlogInfo mBlogInfo= mBlogInfoDao.queryInfoById(Integer.valueOf(id));
				if (mBlogInfo == null) {
					continue;
				}
				if(mBlogInfo.getId().equals(infoId)){
					continue;
				}
				MBlogInfoDto mBlogInfoDto = new MBlogInfoDto();
				if(mBlogInfo.getName().indexOf(keywords)!=-1){
					mBlogInfo.setName(mBlogInfo.getName().replace(keywords, "<span class=\"red\">"+keywords+"</span>"));
				}
				mBlogInfoDto.setmBlogInfo(mBlogInfo);
				//查询出是否已经关注
				MBlogFollow uFollow = mBlogFollowDao.queryByIdAndTargetId(infoId,mBlogInfo.getId(), null);
				if(uFollow!=null){
					if(uFollow.getType().equals("1")){
						mBlogInfoDto.setType("1"); //互相关注
					}else if (uFollow.getFollowStatus().equals("1")) {
						mBlogInfoDto.setType("2"); //已经关注
					}
				}else{
					mBlogInfoDto.setType("0");//没有关注
				}
				//查询出地区
				if (StringUtils.isNotEmpty(mBlogInfo.getProvinceCode())) {
					mBlogInfoDto.setProvinceName(CodeCachedUtil.getValue(
							CodeCachedUtil.CACHE_TYPE_AREA, mBlogInfo
									.getProvinceCode()));
				}
				if (StringUtils.isNotEmpty(mBlogInfo.getAreaCode())) {
					mBlogInfoDto.setAreaName(CodeCachedUtil.getValue(
							CodeCachedUtil.CACHE_TYPE_AREA, mBlogInfo
									.getAreaCode()));
				}
				CompProfile compProfile = compProfileDao.queryCompProfileById(mBlogInfo.getCid());
                mBlogInfoDto.setCompProfile(compProfile);
                //微博数
                mBlogInfoDto.setMblogCount(mBlogDao.queryAllMBlogCountById(mBlogInfo.getId()));
                //查询关注的人
                mBlogInfoDto.setFollowCount(mBlogFollowDao.queryFollowCountByConditions(mBlogInfo.getId(), null, null));
                //查询粉丝数
                mBlogInfoDto.setFansCount(mBlogFollowDao.queryFansCountByConditions( mBlogInfo.getId(), null));
				dtoList.add(mBlogInfoDto);
			}// 数据集合
			page.setRecords(dtoList);
		} catch (SphinxException e) {

			return null;
		}
		
		return page;
	}

	@Override
	public MBlogInfo queryInfoByCid(Integer cid) {
		return mBlogInfoDao.queryInfoByCid(cid);
	}
	

}
