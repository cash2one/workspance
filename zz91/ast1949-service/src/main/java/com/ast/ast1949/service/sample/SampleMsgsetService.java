package com.ast.ast1949.service.sample;

import com.ast.ast1949.domain.sample.SampleMsgset;

public interface SampleMsgsetService {

	final static Integer FLAG_ON = 1;
	final static Integer FLAG_OFF = 0;

	public Integer openMsgset(Integer companyId, Integer email, Integer sms,Integer wechat);
	
	public SampleMsgset queryByCompanyId(Integer companyId);
	
}
