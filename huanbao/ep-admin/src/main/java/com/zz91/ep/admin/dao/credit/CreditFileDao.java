package com.zz91.ep.admin.dao.credit;

import java.util.List;

import com.zz91.ep.domain.comp.CreditFile;

/**
 * 
 * @author tulf
 * 诚信文件相关操作
 * 2011-09-16
 */
public interface CreditFileDao {

	/**
	 * 根据公司ID查找相关荣誉证书
	 * @return
	 */
	public List<CreditFile> queryCreditFileByCid(Integer cid, String category, Short checkStatus);

	public Integer createCreditFile(CreditFile creditFile);

//	public Integer updateCreditFileName(Integer id, Integer cid, String fileName);

	public Integer deleteCreditById(Integer id, Integer cid);

	public Integer updateCheckStatus(Integer id, Integer cid, Short status,String person);
	
	public Integer queryCreditFileCount(Integer cid,Short checkStatus);
}
