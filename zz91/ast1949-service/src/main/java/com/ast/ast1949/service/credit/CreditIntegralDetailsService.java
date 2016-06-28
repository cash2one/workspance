/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-21
 */
package com.ast.ast1949.service.credit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-21
 */
public interface CreditIntegralDetailsService {
	
	final static Map<String, String> OPERATION_NAME = new HashMap<String, String>();
	final static Map<String, Integer> OPERATION_INTEGRAL = new HashMap<String, Integer>();
	final static Map<String, Integer> COMPANY_RELATEID = new HashMap<String, Integer>();

	public int countIntegralByCompany(Integer companyId);

	public Integer saveIntegeral(Integer companyId, String operationKey,
			Integer relateId, Integer integral);

	/**
	 * 查找公司字段对应的关联ID
	 */
	public Integer queryCompanyRelateIdByColumn(String column);

	/**
	 * 根据操作key查找对应的分值信息
	 */
	public Integer queryIntegralByOperationKey(String operationKey);

	public Integer countIntegralByOperationKey(Integer companyId,
			String operationKey);

	public Integer countIntegralByParentOperationKey(Integer companyId,
			String fatherKey);
}
