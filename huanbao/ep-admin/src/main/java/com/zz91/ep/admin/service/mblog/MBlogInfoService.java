package com.zz91.ep.admin.service.mblog;



import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;

public interface MBlogInfoService {
	
	public PageDto<MBlogInfoDto> queryAllMblogInfo(MBlogInfo mBlogInfo,CompProfile compProfile,PageDto<MBlogInfoDto> page);
	
	public Integer updateIsDeleteStatus(Integer infoId,String isDelete);
}
