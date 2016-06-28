package com.zz91.ep.admin.service.credit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.credit.CreditFileDao;
import com.zz91.ep.admin.service.credit.CreditFileService;
import com.zz91.ep.domain.comp.CreditFile;

@Component("creditFileService")
public class CreditFileServiceImpl implements CreditFileService{

	@Resource
	private CreditFileDao creditFileDao;
	
	@Override
	public List<CreditFile> queryCreditFileByCid(Integer cid, String category,
			Short checkStatus) {
		return creditFileDao.queryCreditFileByCid(cid, category, checkStatus);
	}

	@Override
	public Integer createCreditFile(CreditFile creditFile) {
		return creditFileDao.createCreditFile(creditFile);
	}

//	@Override
//	public Integer updateCreditFileName(Integer id, Integer cid,
//			String fileName) {
//		return creditFileDao.updateCreditFileName(id,cid,fileName);
//	}

	@Override
	public Integer deleteCreditById(Integer id, Integer cid, String path) {
        return creditFileDao.deleteCreditById(id,cid);
	}

	@Override
	public Integer updateCheckStatus(Integer id, Integer cid, Short status,String person) {
		return creditFileDao.updateCheckStatus(id,cid,status,person);
	}
}
