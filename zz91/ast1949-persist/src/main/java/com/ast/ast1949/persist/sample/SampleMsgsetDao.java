package com.ast.ast1949.persist.sample;

import com.ast.ast1949.domain.sample.SampleMsgset;


public interface SampleMsgsetDao {
	
	public SampleMsgset queryById(Integer id);
	
	public SampleMsgset queryByCompanyId(Integer companyId);
	
	public Integer insert(SampleMsgset sampleMsgset);
	
	public Integer update(SampleMsgset sampleMsgset);
}