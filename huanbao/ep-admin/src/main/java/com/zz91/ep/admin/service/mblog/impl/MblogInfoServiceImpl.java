package com.zz91.ep.admin.service.mblog.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.admin.dao.mblog.MBlogInfoDao;
import com.zz91.ep.admin.service.mblog.MBlogInfoService;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;
@Component("mblogInfoService")
public class MblogInfoServiceImpl implements MBlogInfoService{
	
	@Resource
	private MBlogInfoDao mBlogInfoDao;
	@Resource
	private CompProfileDao compProfileDao;
	@Override
	public PageDto<MBlogInfoDto> queryAllMblogInfo(MBlogInfo mBlogInfo,CompProfile compProfile,PageDto<MBlogInfoDto> page) {
		if(page.getLimit()==null){
			page.setLimit(20);
		}
		List<MBlogInfoDto>	dtoList=mBlogInfoDao.queryAllMblogInfo(mBlogInfo,compProfile,page);
		for (MBlogInfoDto mBlogInfoDto : dtoList) {
			
			if (StringUtils.isNotEmpty(mBlogInfoDto.getmBlogInfo().getProvinceCode())) {
				mBlogInfoDto.setProvinceName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, mBlogInfoDto.getmBlogInfo().getProvinceCode()));
			}
			
			if (StringUtils.isNotEmpty(mBlogInfoDto.getmBlogInfo().getAreaCode())) {
				mBlogInfoDto.setAreaName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, mBlogInfoDto.getmBlogInfo().getAreaCode()));
			}
			mBlogInfoDto.setAddress(mBlogInfoDto.getProvinceName()+mBlogInfoDto.getAreaName());
			//查询出公司的名字
			CompProfile compProfiles= compProfileDao.queryCompProfileById(mBlogInfoDto.getmBlogInfo().getCid());
			mBlogInfoDto.setCompProfile(compProfiles);
			
		}
		page.setRecords(dtoList);
		page.setTotals(mBlogInfoDao.queryAllMblogCountInfo(mBlogInfo,compProfile));
		return page;
	}
	@Override
	public Integer updateIsDeleteStatus(Integer infoId, String isDelete) {
		
		return mBlogInfoDao.updateIsDeleteStatus(infoId, isDelete);
	}

}
