package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CrmCsLogAdded;


public interface CrmCsLogAddedService {
 
	/**
	 * 给小记追加额外的信息
	 *
	 *  
	 */
	public Integer createAdded(CrmCsLogAdded added, Integer companyId);
}
 
