/**
 * 
 */
package com.ast.ast1949.persist.company;

import com.ast.ast1949.domain.company.CrmCsProfile;

/**
 * @author root
 *
 */
public interface CrmCsProfileDao {

	public Integer countProfile(Integer companyId);
	
	public Integer insertProfile(CrmCsProfile profile);
	
	public Integer updateLastVisit(Integer companyId);
	
	public Integer updateMemberShipCode(String membershipCode ,Integer companyId);
	/**
	 * 登录数和最近登录时间
	 * @param companyId
	 * @return
	 */
	public CrmCsProfile queryCrmCsProfileByCompanyId(Integer companyId);
}
