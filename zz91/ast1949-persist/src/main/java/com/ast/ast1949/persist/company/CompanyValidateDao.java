/**
 * 
 */
package com.ast.ast1949.persist.company;

import com.ast.ast1949.domain.company.CompanyValidate;

/**
 * @author mays
 *
 */
public interface CompanyValidateDao {

	public Integer insert(CompanyValidate cv);
	
	public CompanyValidate queryOneByKey(String key);
	
	public CompanyValidate queryOneByAccount(String account);
	
	public Integer updateActived(Integer id, Integer activedType);
	
	public Integer queryActived(String account);
	
	public CompanyValidate queryValidateByCompanyId(Integer companyId);
	/**
	 * 每天同类别验证的次数
	 * @param account
	 * @param activedType
	 * @param gmtCreated
	 * @return
	 */
	public Integer countValidateByCondition(String account, Integer activedType,String gmtCreated);
}
