package com.ast.ast1949.service.company.impl;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.InquiryTask;
import com.ast.ast1949.persist.company.InquiryTaskDao;
import com.ast.ast1949.service.company.InquiryTaskService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj date:2013-7-11
 */
@Component("inquiryTaskService")
public class InquiryTaskServiceImpl implements InquiryTaskService {

	@Resource
	private InquiryTaskDao inquiryTaskDao;
	
	@Override
	public Integer addNewInquiryTask(InquiryTask inquiryTask) {
		do {
			
			// 条件判断
			if(inquiryTask.getCompanyId()==null||inquiryTask.getTargetId()==null||StringUtils.isEmpty(inquiryTask.getTargetType())){
				break;
			}
			
			InquiryTask obj = inquiryTaskDao.query(inquiryTask.getCompanyId(),inquiryTask.getTargetId(),inquiryTask.getTargetType());
			
			// 已经发送过 不再重复发布
			if(obj!=null){
				break;
			}
			
			// 发送时间
			if (inquiryTask.getPostTime() == null) {
				try {
					inquiryTask.setPostTime(DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), 1));
				} catch (ParseException e) {
					inquiryTask.setPostTime(new Date());
				}
			}
			return inquiryTaskDao.insert(inquiryTask);
		} while (false);
		return 0;
	}

}
