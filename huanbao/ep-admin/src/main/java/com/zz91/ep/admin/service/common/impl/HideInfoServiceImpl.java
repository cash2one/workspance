package com.zz91.ep.admin.service.common.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.admin.dao.common.HideInfoDao;
import com.zz91.ep.admin.service.common.HideInfoService;
import com.zz91.ep.domain.common.HideInfo;

@Service("HideInfoService")
public class HideInfoServiceImpl  implements HideInfoService {
    
	@Resource
    private HideInfoDao hideInfoDao;	
	
	@Override
	public Integer delete(Integer targetId,String targetType) {
		return hideInfoDao.delete(targetId, targetType);
	}

	@Override
	public Integer insert(HideInfo hideInfo) {
		return hideInfoDao.insert(hideInfo);
	}

	@Override
	public HideInfo queryHideInfoByIdAndType(Integer targetId, String targetType) {
		return hideInfoDao.queryHideInfoByIdAndType(targetId, targetType);
	}

	@Override
	public Integer querycount(Integer targetId, String targetType) {
		return hideInfoDao.querycount(targetId, targetType);
	}

	@Override
	public Integer update(HideInfo hideInfo) {
		return hideInfoDao.update(hideInfo);
	}

}
