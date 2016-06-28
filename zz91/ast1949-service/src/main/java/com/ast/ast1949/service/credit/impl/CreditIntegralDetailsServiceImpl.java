/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-21
 */
package com.ast.ast1949.service.credit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.credit.CreditIntegralDetailsDo;
import com.ast.ast1949.persist.credit.CreditIntegralDetailsDao;
import com.ast.ast1949.service.credit.CreditIntegralDetailsService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-21
 */
@Component("creditIntegralDetailsService")
public class CreditIntegralDetailsServiceImpl implements
		CreditIntegralDetailsService {

	@Autowired
	CreditIntegralDetailsDao creditIntegralDetailsDao;

	static {
		// 初始化公司资料相关的 关联ID
		COMPANY_RELATEID.put("company_name", 1);
		COMPANY_RELATEID.put("company_email", 2);
		COMPANY_RELATEID.put("company_contact", 3);
		COMPANY_RELATEID.put("company_phone", 4);
		COMPANY_RELATEID.put("company_address", 5);

		// 初始化操作与名称
		OPERATION_NAME.put("company_name", "公司资料（公司名称）");
		OPERATION_NAME.put("company_email", "公司资料（邮箱）");
		OPERATION_NAME.put("company_contact", "公司资料（联系人）");
		OPERATION_NAME.put("company_phone", "公司资料（手机/联系电话）");
		OPERATION_NAME.put("company_address", "公司资料（地址）");

		OPERATION_NAME.put("customer_vote_0", "好评");// 好评
		OPERATION_NAME.put("customer_vote_1", "中评");// 中评
		OPERATION_NAME.put("customer_vote_2", "差评");// 差评

		OPERATION_NAME.put("credit_file", "荣誉证书");// 荣誉证书
		OPERATION_NAME.put("credit_reference", "资信参考人");
		OPERATION_NAME.put("service_zst_year", "再生通开通年限");

		// 初始化操作与分值
		OPERATION_INTEGRAL.put("company_name", 2);
		OPERATION_INTEGRAL.put("company_email", 2);
		OPERATION_INTEGRAL.put("company_contact", 2);
		OPERATION_INTEGRAL.put("company_phone", 2);
		OPERATION_INTEGRAL.put("company_address", 2);

		OPERATION_INTEGRAL.put("customer_vote_0", 2);
		OPERATION_INTEGRAL.put("customer_vote_1", 0);
		OPERATION_INTEGRAL.put("customer_vote_2", -2);

		OPERATION_INTEGRAL.put("credit_file", 1);
		OPERATION_INTEGRAL.put("credit_reference", 1);
		OPERATION_INTEGRAL.put("service_zst_year", 20);
		

	}

	@Override
	public int countIntegralByCompany(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		return creditIntegralDetailsDao.countIntegralByCompany(companyId);
	}

	@Override
	public Integer countIntegralByOperationKey(Integer companyId,
			String operationKey) {
		Assert.notNull(companyId, "the companyId can not be null");
		Integer num=creditIntegralDetailsDao.countIntegralByOperationKey(companyId,operationKey);
		if(num!=null){
			return num.intValue();
		}
		return 0;
	}

	@Override
	public Integer queryCompanyRelateIdByColumn(String column) {
		return COMPANY_RELATEID.get(column);
	}

	@Override
	public Integer queryIntegralByOperationKey(String operationKey) {
		return OPERATION_INTEGRAL.get(operationKey);
	}

	@Override
	public Integer saveIntegeral(Integer companyId, String operationKey,
			Integer relateId, Integer integral) {
//		Assert.notNull(companyId, "the companyId can not be null");
//		Assert.notNull(operationKey, "the operationKey can not be null");
//		Assert.notNull(relateId, "the relateId can not be null");

//		if (StringUtils.isEmpty(operationKey)) {
//			return null;
//		}
//
//		if (OPERATION_INTEGRAL.get(operationKey) == null) {
//			return null;
//		}

		CreditIntegralDetailsDo detail = new CreditIntegralDetailsDo();
		detail.setCompanyId(companyId);
		detail.setOperationKey(operationKey);
		detail.setRelatedId(relateId);
		detail.setIntegral(integral);
		//如果已经存在，则更新积分
		return creditIntegralDetailsDao.insertIntegral(detail);
	}

	@Override
	public Integer countIntegralByParentOperationKey(Integer companyId,
			String fatherKey) {
//		Assert.notNull(companyId, "the companyId can not be null");
//		Assert.notNull(fatherKey, "the fatherKey can not be null");
//		return creditIntegralDetailsDao.countIntegralByOperationKey(companyId,
//				fatherKey);
		return null;
	}

}
