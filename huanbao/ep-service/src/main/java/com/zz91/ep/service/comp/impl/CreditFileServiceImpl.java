/*
 * 文件名称：CreditFileServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-19 上午10:38:50
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.comp.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CreditFileDao;
import com.zz91.ep.domain.comp.CreditFile;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.service.comp.CreditFileService;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：荣誉证书操作实现类
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("creditFileService")
public class CreditFileServiceImpl implements CreditFileService {

	@Resource
	private CreditFileDao creditFileDao;
	
	@Override
	public List<CreditFile> queryCreditFileByCid(Integer cid, String category, Short checkStatus) {
		return creditFileDao.queryCreditFileByCid(cid, category, checkStatus);
	}

	@Override
	public ExtResult updateCreditFileName(Integer id, Integer cid, String fileName) {
		ExtResult result = null;
        do {
          Integer count = creditFileDao.updateCreditFileName(id,cid,fileName);
            if (count != null && count > 0) {
            	result = new ExtResult();
                result.setData(count);
                result.setSuccess(true);
            }
        } while (false);
        return result;
        
	}

	@Override
	public ExtResult deleteCreditById(Integer id, Integer companyId, String path) {
		   ExtResult result = null;
	        do {
	            if (id == null) {
	                break;
	            }
	            Integer count = creditFileDao.deleteCreditById(id, companyId);
	            if (count != null && count.intValue()> 0) {
//	            	FileUtils.deleteFile(path);
	            	result = new ExtResult();
	                result.setSuccess(true);
	            }
	        } while (false);
	    return result;
	}

	@Override
	public ExtResult createCreditFile(Integer cid, String account,
			String picture, String fileName) {
		ExtResult result =  null;
        do {
        	CreditFile creditFile = new CreditFile();
        	creditFile.setCid(cid);
        	creditFile.setPicture(picture);
        	creditFile.setAccount(account);
        	creditFile.setFileName(fileName);
            Integer count = creditFileDao.createCreditFile(creditFile);
            if (count != null && count > 0) {
            	result = new ExtResult();
                result.setData(count);
                result.setSuccess(true);
			}
        } while (false);
        return result;
	}

}
