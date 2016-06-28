package com.ast.feiliao91.service.company.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.FeedBack;
import com.ast.feiliao91.persist.company.FeedBackDao;
import com.ast.feiliao91.service.company.FeedBackService;


@Component("feedBackService")
public class FeedBackServiceImpl implements FeedBackService{
    
	@Resource
	private FeedBackDao feedBackDao;
	@Override
	public Integer insert(FeedBack feedback) {
		return feedBackDao.insert(feedback);
	}

	@Override
	public FeedBack selectById(Integer id) {
		return  feedBackDao.selectById(id);
	}
}
