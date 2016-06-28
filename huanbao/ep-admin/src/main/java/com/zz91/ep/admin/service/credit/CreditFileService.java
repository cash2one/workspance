package com.zz91.ep.admin.service.credit;

import java.util.List;

import com.zz91.ep.domain.comp.CreditFile;

public interface CreditFileService {

	/**
	 * 根据公司ID查找相关荣誉证书
	 * @return
	 */
	public List<CreditFile> queryCreditFileByCid(Integer cid, String category, Short checkStatus);

	public Integer createCreditFile(CreditFile creditFile);

//	public Integer updateCreditFileName(Integer id, Integer companyId,
//			String fileName);

	public Integer deleteCreditById(Integer id, Integer companyId, String path);
	
	public Integer updateCheckStatus(Integer id,Integer cid,Short status,String person);
}
