package com.ast.ast1949.service.sample.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinScoresall;
import com.ast.ast1949.persist.sample.WeixinScoresallDao;
import com.ast.ast1949.service.sample.WeixinScoresallService;

@Component("weixinScoresallService")
public class WeixinScoresallServiceImpl implements WeixinScoresallService {

	@Resource
	private WeixinScoresallDao weixinScoresallDao;

	@Override
	public int insert(WeixinScoresall record) {
		return weixinScoresallDao.insert(record);
	}

	@Override
	public int updateByPrimaryKey(WeixinScoresall record) {
		return weixinScoresallDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinScoresall record) {
		return weixinScoresallDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public WeixinScoresall selectByPrimaryKey(Integer id) {
		return weixinScoresallDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinScoresallDao.deleteByPrimaryKey(id);
	}
}
