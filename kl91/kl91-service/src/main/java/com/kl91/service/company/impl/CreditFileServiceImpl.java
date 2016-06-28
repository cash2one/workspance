package com.kl91.service.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.CreditFile;
import com.kl91.domain.dto.company.CreditFileSearchDto;
import com.kl91.persist.company.CreditFileDao;
import com.kl91.service.company.CreditFileService;
import com.kl91.service.company.UploadPicService;

@Component("creaditFileService")
public class CreditFileServiceImpl implements CreditFileService{
	
	@Resource
	private CreditFileDao creditFileDao;
	@Resource
	private UploadPicService uploadPicService;

	@Override
	public Integer createByUser(CreditFile createFile, Integer picid) {
		uploadPicService.editUploadPicById(picid, createFile.getCid(), UploadPicService.TARGETTYPE_OF_CREDIT);
		return creditFileDao.insert(createFile);
	}

	@Override
	public Integer deleteById(Integer id) {
		
		return creditFileDao.delete(id);
	}
	
	@Override
	public Integer editFile(CreditFile creditFile, boolean nfileFlag) {
//		if(nfileFlag==true){
//			uploadPicDao.updateTargetId(creditFile.getId(), null,null);
//		}
//		return creditFileDao.update(creditFile);
		return 0;
	}

	@Override
	public CreditFile queryById(Integer id) {
		
		return creditFileDao.queryById(id);
	}

	@Override
	public List<CreditFile> queryFile(CreditFileSearchDto searchDto) {
		
		return creditFileDao.queryFile(searchDto);
	}

}
