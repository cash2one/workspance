/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-26
 */
package com.ast.ast1949.persist.credit;

import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;

/**
 * @author yuyonghui
 * 
 */
public interface CreditIntegralDetailsDao {

	public int countIntegralByCompany(Integer companyId);

	public Integer insertIntegral(CreditIntegralDetailsDo detail);

	public Integer updateIntegral(CreditIntegralDetailsDo detail);

	public Integer countIntegralByOperationKey(Integer companyId,
			String operationKey);
	
	public Integer deleteIntegralByOperation(String operationKey, Integer relatedId, Integer companyId);

	/**
	 * 根据项目操作key 查询对应的分值
	 * 
	 * @param operatorKey
	 * @return CreditIntegralDetailsDO
	 */
	// public CreditIntegralDetailsDO queryCreditIntegralByOperatorKey(Integer
	// companyId,String opertorKey);
	/**
	 * 加分或减分 添加日志记录
	 * 
	 * @param creditIntegralDetailsDO
	 *            修改分值
	 * @return
	 */
	// public Integer insertCreditIntegralDetailsDO(CreditIntegralDetailsDo
	// creditIntegralDetailsDO);
	//	
	// public CreditIntegralDetailsDo queryByComanyIDAndOperatorKey(Integer
	// companyId,String operatorKey);
}
