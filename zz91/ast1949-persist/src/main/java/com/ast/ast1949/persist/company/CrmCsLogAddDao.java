package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CrmCsLogAdded;


public interface CrmCsLogAddDao {
 
	/**
	 * 给小记追加额外的信息
	 *
	 *  
	 */
	public Integer createAdded(CrmCsLogAdded added);
	public List<CrmCsLogAdded> queryAddedByLog(Integer logId);
}
 
