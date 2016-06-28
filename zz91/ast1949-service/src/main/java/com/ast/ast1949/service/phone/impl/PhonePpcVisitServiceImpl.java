package com.ast.ast1949.service.phone.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhonePpcVisit;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhonePpcVisitDto;
import com.ast.ast1949.persist.phone.PhonePpcVisitDao;
import com.ast.ast1949.service.phone.PhonePpcVisitService;
@Component("phonePpsVisitService")
public class PhonePpcVisitServiceImpl implements PhonePpcVisitService {
	@Resource
	private PhonePpcVisitDao phonePpsVisitDao;
	@Override
	public PageDto<PhonePpcVisitDto> pagePpcVisitList(PhonePpcVisit phonePpcVisit, PageDto<PhonePpcVisitDto> page){
		page.setTotalRecords(phonePpsVisitDao.countVisitByTargetId(phonePpcVisit.getTargetId()));
		List<PhonePpcVisitDto> list=phonePpsVisitDao.queryVisitByTargetId(phonePpcVisit, page);
		//统计被该公司访问的次数
		for(PhonePpcVisitDto ppvt:list){
			ppvt.setNumber(phonePpsVisitDao.countVisitByBoth(ppvt.getPhonePpcVisit().getTargetId(), ppvt.getPhonePpcVisit().getCid()));
		}
		page.setRecords(list);
		return page;
	}

}
