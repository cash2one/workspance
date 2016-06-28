package com.kl91.service.company.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.UploadPic;
import com.kl91.persist.company.UploadPicDao;
import com.kl91.service.company.UploadPicService;

@Component("uploadPicService")
public class UploadPicServiceImpl implements UploadPicService{
	
	@Resource
	private UploadPicDao uploadPicDao;
	
	@Override
	public Integer createUploadPic(UploadPic uploadPic) {		
		return uploadPicDao.insert(uploadPic);
	}

	@Override
	public Integer deleteById(Integer id) {
		return uploadPicDao.deleteById(id);
	}

	@Override
	public Integer editUploadPicById(Integer id, Integer targetId,
			Integer targetType) {
		return uploadPicDao.updateTargetId(id, targetId,targetType);
	}

	@Override
	public UploadPic queryById(Integer id) {
		return uploadPicDao.queryById(id);
	}

	@Override
	public UploadPic queryUploadPicByTargetId(Integer targetId) {
		return uploadPicDao.queryByTargetId(targetId);
	}

}
