/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-25
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyCustomersGroupDO;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public interface CompanyCustomersGroupDao {
    
	/**
	 *     查询所有客户组 根据登录 公司 Id
	 * @return 
	 */
 	public List<CompanyCustomersGroupDO> queryCompanyCustomersGroupByCompanyId(Integer companyId);
 	
 	/**
 	 *   查询客户组信息 
 	 * @param id  is not null
 	 * @return  CompanyCustomersGroupDO
 	 * 
 	 */
 	public CompanyCustomersGroupDO queryGroupById(Integer id);
 	
 	/**
 	 *         添加广告组
 	 * @param companyCustomersGroupDO  不能为空
 	 * @return  >0  添加成功
 	 *          <0 添加失败 
 	 */
 	public Integer insertCompanyCustomersGroup(CompanyCustomersGroupDO companyCustomersGroupDO);
 	
 	/**
 	 *          修改广告组
 	 * @param companyCustomersGroupDO
 	 * @return   >0  修改成功
 	 *           <0     修改失败 
 	 */
 	public Integer updateCompanyCustomersGroup(CompanyCustomersGroupDO companyCustomersGroupDO);
 	
 	/**
 	 *          删除广告组
 	 * @param id 不能为空  
 	 * @return  >0  删除成功
 	 *          <0  删除失败 
 	 */
 	public Integer deleteCompanyCustomersGroupById(Integer id);
 	
}
