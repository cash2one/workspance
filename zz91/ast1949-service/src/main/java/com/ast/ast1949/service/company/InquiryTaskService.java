package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.InquiryTask;

/**
 *	author:kongsj
 *	date:2013-7-11
 */
public interface InquiryTaskService {
	
	public final static String TARGET_TYPE_COMPANY = "1";
	
	/**
	 * 新增一条询盘发送任务
	 * @param inquiryTask
	 * @return
	 */
	public Integer addNewInquiryTask(InquiryTask inquiryTask);
}
