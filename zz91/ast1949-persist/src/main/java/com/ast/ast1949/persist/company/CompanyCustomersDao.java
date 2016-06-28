/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyCustomersDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCustomersDTO;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface CompanyCustomersDao {

	/**
	 *    按条件查询所有客户信息
	 * @param companyCustomersDTO:name,company,companyPhone,tel,companyId 
	 *         companyid   不能为空,
	 *         name,company,companyPhone 可以为空  
	 * @return 查询结果集
	 *           null
	 */
//	public List<CompanyCustomersDTO> queryCompanyCustomersForFront(CompanyCustomersDTO companyCustomersDTO); 
	/**
	 *      按groupId查询所有客户信息
	 * @param groupId  不能为空
	 * @return  查询结果集
	 *          null
	 */
//	public List<CompanyCustomersDO> queryCompanyCustomersByGroupId(Integer groupId);
	
	/**
	 *      添加客户信息
	 * @param companyCustomersDO  不能为空
	 * @return  结果>0  添加成功
	 *          <0   添加失败
	 */
	public Integer insertCompanyCustomers(CompanyCustomersDO companyCustomersDO);
	
	/**
	 *  按id 查询客户所有信息
	 * @param id  不能为空
	 * @return CompanyCustomersDO
	 */
	public CompanyCustomersDO queryCompanyCustomersById(Integer id);
	
	/**
	 *      修改客户信息
	 * @param companyCustomersDO  不能为空
	 * @return  结果>0  修改成功
	 *          <0           修改失败
	 */
	public Integer updateCompanyCustomers(CompanyCustomersDO companyCustomersDO);
	
	/**
	 *    修改客户所属组
	 * @param companyCustomersDO
	 * @return  结果>0  修改成功
	 *          <0           修改失败
	 */
	public Integer updateGroupById(Integer[] companyCustomersGroupId,Integer id); 
	/**
	 *     删除客户信息 按Id
	 * @param id   不能为空
	 * @return   结果>0              删除成功
	 *               <0              删除失败
	 */
//	public Integer deleteCompanyCustomersById(Integer id);
	/**
	 * 根据组update客戶
	 * @param companyCustomersGroupId
	 * @return 
	 */
	public Integer updateCustomersGroup(Integer newGroupId,Integer companyCustomersId);
	/**
	 *     P批量删除客户信息
	 * @param entities
	 * @return  结果>0              删除成功
	 *               <0              删除失败
	 */
	public Integer batchDeleteCompanyCustomersById(Integer[] entities);
	
	/**
	 *     查询客户信息记录 
	 * @param CompanyCustomersDTO
	 * @return  成功：返回影响行数；<br/>
	 *           失败：返回0.
	 */
//	public Integer queryCompanyCustomersRecordCount(CompanyCustomersDTO companyCustomersDTO);
	
	/**
	 * 查询所有客户信息
	 * @param companyId为公司主键值，不能为空，否则抛出异常
	 * @return CompanyCustomersDO集合，没查询到数据时，返回空
	 */
//	public List<CompanyCustomersDO> queryCompanyCustomersByCompanyId(Integer companyId);
	/**
	 * 根据询盘信息查询可以导入的所有客户列表
	 * @param companyId
	 * @return
	 */
	public List<CompanyCustomersDO> queryCompanyCustomersForImportByInquiry(Integer companyId);
	
	public PageDto<CompanyCustomersDTO> queryCompanyCustomerListByCompanyIdAndGroupId(
			CompanyCustomersDO customer,PageDto page);
}
