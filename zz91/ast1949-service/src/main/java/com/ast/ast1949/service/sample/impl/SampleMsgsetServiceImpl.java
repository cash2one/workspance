package com.ast.ast1949.service.sample.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.SampleMsgset;
import com.ast.ast1949.persist.sample.SampleMsgsetDao;
import com.ast.ast1949.service.sample.SampleMsgsetService;

@Component("sampleMsgsetService")
public class SampleMsgsetServiceImpl implements SampleMsgsetService {

	@Resource
	private SampleMsgsetDao sampleMsgsetDao;

	@Override
	public Integer openMsgset(Integer companyId, Integer email, Integer sms, Integer wechat) {
		Integer i = 0;
		do {
			if (companyId==null) {
				break;
			}
			if (email==null) {
				email = 0;
			}
			if (sms==null) {
				sms = 0;
			}
			if (wechat==null) {
				wechat = 0;
			}
			SampleMsgset sampleMsgset = queryByCompanyId(companyId);
			if (sampleMsgset==null) {
				sampleMsgset = new SampleMsgset();
				sampleMsgset.setCompanyId(companyId);
				sampleMsgset.setEmail(email);
				sampleMsgset.setSms(sms);
				sampleMsgset.setWechat(wechat);
				i = sampleMsgsetDao.insert(sampleMsgset);
				break;
			}
			sampleMsgset.setEmail(email);
			sampleMsgset.setSms(sms);
			sampleMsgset.setWechat(wechat);
			i = sampleMsgsetDao.update(sampleMsgset);

		} while (false);
		return i;
	}

	@Override
	public SampleMsgset queryByCompanyId(Integer companyId) {
		if (companyId==null||companyId==0) {
			return null;
		}
		return sampleMsgsetDao.queryByCompanyId(companyId);
	}
	
}
