package com.ast.ast1949.service.sample.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinScore;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.sample.WeixinScoreDao;
import com.ast.ast1949.service.sample.WeixinScoreService;

@Component("weixinScoreService")
public class WeixinScoreServiceImpl implements WeixinScoreService {

	@Resource
	private WeixinScoreDao weixinScoreDao;

	@Override
	public int  insert(WeixinScore record) {
		return weixinScoreDao.insert(record);
	}

	@Override
	public int updateByPrimaryKey(WeixinScore record) {
		return weixinScoreDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinScore record) {
		return weixinScoreDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public WeixinScore selectByPrimaryKey(Integer id) {
		return weixinScoreDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinScoreDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageDto<WeixinScore> queryListByFilter(PageDto<WeixinScore> page, Map<String, Object> filterMap) {
		if (page.getSort() == null) {
			page.setSort("id");
		}

		filterMap.put("page", page);
		 page.setTotalRecords(weixinScoreDao.queryListByFilterCount(filterMap));
		 page.setRecords(weixinScoreDao.queryListByFilter(filterMap));
		return page;
	}

	@Override
	public Integer totalAvailableScore(String  account) {
		return  weixinScoreDao.totalAvailableScore(account);
	}
}
