package com.ast.ast1949.persist.company;

import com.ast.ast1949.domain.company.InquiryTask;

/**
 * author:kongsj date:2013-7-11
 */
public interface InquiryTaskDao {
	public InquiryTask query(Integer companyId, Integer targetId,
			String targetType);

	public Integer insert(InquiryTask inquiryTask);

}
