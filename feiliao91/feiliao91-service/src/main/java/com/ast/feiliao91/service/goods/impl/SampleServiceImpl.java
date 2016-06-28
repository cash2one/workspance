package com.ast.feiliao91.service.goods.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.Sample;
import com.ast.feiliao91.persist.goods.SampleDao;
import com.ast.feiliao91.service.goods.SampleService;

@Component("sampleService")
public class SampleServiceImpl implements SampleService {
	
	@Resource
	private SampleDao sampleDao;
	
	@Override
	public Integer insertGoods(Sample sample) {
		return sampleDao.insertSample(sample);
	}
	@Override
	public Integer iterateInsert(List<Sample> list){
		return sampleDao.iterateInsert(list);
	}
}
