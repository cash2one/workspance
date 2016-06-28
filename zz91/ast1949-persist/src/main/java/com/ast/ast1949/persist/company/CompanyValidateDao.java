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
}
