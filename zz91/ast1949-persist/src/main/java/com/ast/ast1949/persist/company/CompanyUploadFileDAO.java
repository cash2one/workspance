/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-16
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyUploadFileDO;

/**
 * @author yuyonghui
 *
 */
public interface CompanyUploadFileDAO {

	/**
	 *   按公司Id查询公司图片信息
	 * @param companyId
	 * @return    CompanyUploadFileDO
	 */
	public List<CompanyUploadFileDO> queryByCompanyId(Integer companyId);
	
	/**
	 *    添加公司图片信息
	 * @param companyUploadFileDO
	 * @return if >0 添加成功
	 *         else 添加失败
	 */
	public Integer insertCompanyUploadFile(CompanyUploadFileDO companyUploadFileDO);
	/**
	 *    修改公司图片信息
	 * @param companyUploadFileDO
	 * @return if >0 修改成功
	 *         else  修改失败
	 */
//	public Integer updateCompanyUploadFile(CompanyUploadFileDO companyUploadFileDO);
    /**
     *    删除公司图片信息
     * @param id
     * @return if >0 删除成功
     *         else 删除失败
     */
	public Integer deleteComapanyUploadFileById(Integer id);
	
	public CompanyUploadFileDO queryById(Integer id);
	
	/**
	 * 获得最近修改的一张公司上传图片
	 * @param companyId为公司主键值，不能为空，否则抛出异常
	 * @return CompanyUploadFileDO信息，没找到数据时为空
	 */
//	public CompanyUploadFileDO queryByCompanyIdAndModefied(Integer companyId);
	
}
