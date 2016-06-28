/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyCustomersDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCustomersDTO;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
public interface CompanyCustomersService {
	/**
	 * 按条件查询所有客户信息 name,company,companyPhone,tel
	 * 
	 * @return 查询结果集 null
	 * 
	 */
//	public List<CompanyCustomersDTO> queryCompanyCustomersForFront(
//			CompanyCustomersDTO companyCustomersDTO);

	/**
	 * 按groupId查询所有客户信息
	 * 
	 * @param groupId
	 * @return 查询结果集 null
	 */
//	public List<CompanyCustomersDO> queryCompanyCustomersByGroupId(Integer groupId);

	/**
	 * 添加客户信息
	 * 
	 * @param companyCustomersDO
	 *            不能为空
	 * @return 结果>0 添加成功 <0 添加失败
	 */
	public Integer insertCompanyCustomers(CompanyCustomersDO companyCustomersDO);

	/**
	 * 把公司信息导入到myrc为客户
	 * 
	 * @param companyDO
	 *            companyContactsDO
	 * @return
	 */
//	public Integer insertCompanyToCustomers(CompanyDO companyDO, CompanyContactsDO companyContactsDO);

	/**
	 * 按id 查询客户所有信息
	 * 
	 * @param id
	 *            不能为空
	 * @return CompanyCustomersDO
	 */
	public CompanyCustomersDO queryCompanyCustomersById(Integer id);

	/**
	 * 修改客户信息
	 * 
	 * @param companyCustomersDO
	 *            不能为空
	 * @return 结果>0 修改成功 <0 修改失败
	 */
	public Integer updateCompanyCustomers(CompanyCustomersDO companyCustomersDO);

	/**
	 * 修改客户所属组
	 * 
	 * @param companyCustomersDO
	 * @return 结果>0 修改成功 <0 修改失败
	 */
	public Integer batchUpdateGroupById(String ids, Integer companyCustomersGroupId);

	/**
	 * 删除客户信息 按Id
	 * 
	 * @param id
	 *            不能为空
	 * @return 结果>0 删除成功 <0 删除失败
	 */
//	public Integer deleteCompanyCustomersById(Integer id);

	/**
	 * 批量删除企业报价
	 * 
	 * @param entities
	 * @return i >o 修改成功 else 失败
	 */
	public Integer batchDeleteCustomerById(Integer[] entities);

	/**
	 * 查询客户信息记录
	 * 
	 * @param CompanyCustomersDTO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
//	public Integer queryCompanyCustomersRecordCount(CompanyCustomersDTO companyCustomersDTO);

	/**
	 * 查询所有客户信息
	 * 
	 * @param companyId为公司主键值
	 *            ，不能为空，否则抛出异常
	 * @return CompanyCustomersDO集合，没查询到数据时，返回空
	 */
//	public List<CompanyCustomersDO> queryCompanyCustomersByCompanyId(Integer companyId);

	/**
	 * 从询盘留言信息中查询潜在客户：询盘留言目标公司是本公司，又不在公司的客户列表中的客户联系信息
	 * 
	 * @param companyId
	 * @return
	 */
	public List<CompanyCustomersDO> queryCompanyCustomersForImportByInquiry(Integer companyId);

	/**
	 * 从询盘留言中导入客户信息
	 * 
	 * @param importAccount
	 *            导入操作的帐号名
	 * @param companyCustomerList
	 *            导入的客户列表
	 */
	public void insertCompanyCustomersForImport(String importAccount,
			List<CompanyCustomersDO> companyCustomerList);

	/**
	 * 根据组删除客戶
	 * 
	 * @param companyCustomersGroupId
	 * @return
	 */
	public Integer updateCustomersGroup(Integer newGroupId,
			Integer companyCustomersGroupId);

	public PageDto<CompanyCustomersDTO> queryCompanyCustomerListByCompanyIdAndGroupId(
			CompanyCustomersDO customer, PageDto page);
}
