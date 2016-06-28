package com.ast.ast1949.service.sample.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.sample.SampleRelateProductDao;
import com.ast.ast1949.service.sample.SampleRelateProductService;
import com.zz91.util.datetime.DateUtil;

@Component("sampleRelateProductService")
public class SampleRelateProductServiceImpl implements SampleRelateProductService{
	
	@Resource
	private SampleRelateProductDao sampleRelateProductDao;

	@Override
	public Integer countTodayAdd() {
		Date date = new Date();
		String from  = DateUtil.toString(date, "yyyy-MM-dd");
		String to  =  DateUtil.toString(DateUtil.getDateAfterDays(date, 1), "yyyy-MM-dd");
		Integer i = sampleRelateProductDao.countAddByDate(from, to);
		if (i>0) {
			return i;
		}
		return 0;
	}

	@Override
	public Integer queryBySampleIdForProductId(Integer sampleId) {
		return  sampleRelateProductDao.queryBySampleIdForProductId(sampleId);
	}
	
}
