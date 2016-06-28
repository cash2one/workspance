/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-2
 */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.InquiryGroup;


/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface InquiryGroupService  {
	
	public List<InquiryGroup> queryGroupOfCompany(Integer companyId);
	
	public List<InquiryGroup> queryGroupOfAccount(String account);
	
	public List<InquiryGroup> querySystemGroup();
	
	public Integer createGroup(InquiryGroup group);
	
	public Integer updateGroup(InquiryGroup group);
	
	public Integer deleteGroup(Integer id, String account);
	
	public String queryName(Integer id);
	
//	/**
//	 * 添加分组
//	 * @param inquiryGroupDO
//	 * 	分组名称不能为空
//	 * @return
//	 */
//	public Integer insertInquiryGroup(InquiryGroupDO inquiryGroupDO);
//	/**
//	 * 根据Id删除分组,同时将该分组下的询盘信息设置为未分组。
//	 * @param id
//	 * @return
//	 */
//	public Integer deleteInquiryGroupById(Integer id);
//
//	/**
//	 * 根据Id更新分组
//	 * @param inquiryGroupDO
//	 * @return
//	 */
//	public Integer updateInquiryGroup(InquiryGroupDO inquiryGroupDO);
//
//	/**
//	 * 根据Id查询分组信息
//	 * @param id
//	 * @return
//	 */
////	public InquiryGroupDO queryInquiryGroupById(Integer id);
//
//	/**
//	 * 根据公司编号（companyId）查询分组信息
//	 * @param companyId 公司编号
//	 * @return 返回结果中海包含了companyId=0的记录，即：系统默认分组
//	 */
//	public List<InquiryGroupDO> queryInquiryGroupListByCompanyId(Integer companyId);
//
//	/**
//	 * 批量分组
//	 * @param inquiryArray:待分组的询盘ID,长度为0时不进行删除处理
//	 * @param inquiryGroupId:将要设置的组ID,不可以为null或空,否则抛出异常
//	 * @return 操作的影响行数
//	 */
//	public Integer batchSetInquiryGroup(String inquiryArray, String inquiryGroupId);
//	/**
//	 * 根据公司编号（companyId）查询自定义分组
//	 * @param companyId 公司编号
//	 * @return 
//	 */
//	public List<InquiryGroupDO> queryCustomizecInquiryGroupListByCompanyId(Integer companyId);
}
