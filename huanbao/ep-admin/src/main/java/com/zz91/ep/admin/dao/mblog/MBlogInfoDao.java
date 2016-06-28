package com.zz91.ep.admin.dao.mblog;

import java.util.List;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;

public interface MBlogInfoDao {
	
	public List<MBlogInfoDto> queryAllMblogInfo(MBlogInfo mBlogInfo,CompProfile compProfile,PageDto<MBlogInfoDto> page);
	
	public Integer queryAllMblogCountInfo(MBlogInfo mBlogInfo,CompProfile compProfile);
	
	public Integer updateIsDeleteStatus(Integer infoId,String isDelete);
}
