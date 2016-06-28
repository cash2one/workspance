package com.ast.ast1949.service.sample.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.WeixinPrizelog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.sample.WeixinPrizelogDao;
import com.ast.ast1949.service.sample.WeixinPrizelogService;

@Component("weixinPrizelogService")
public class WeixinPrizelogServiceImpl implements WeixinPrizelogService {

	@Resource
	private WeixinPrizelogDao weixinPrizelogDao;

	@Override
	public int insert(WeixinPrizelog record) {
		return weixinPrizelogDao.insert(record);
	}

	@Override
	public int updateByPrimaryKey(WeixinPrizelog record) {
		return weixinPrizelogDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(WeixinPrizelog record) {
		return weixinPrizelogDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public WeixinPrizelog selectByPrimaryKey(Integer id) {
		return weixinPrizelogDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return weixinPrizelogDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageDto<WeixinPrizelog> queryListByFilter(PageDto<WeixinPrizelog> page, Map<String, Object> filterMap) {
		if (page.getSort() == null) {
			page.setSort("id");
		}

		filterMap.put("page", page);
		 page.setTotalRecords(weixinPrizelogDao.queryListByFilterCount(filterMap));
		 page.setRecords(weixinPrizelogDao.queryListByFilter(filterMap));
		return page;
	}

	/**
	 * 已兑换的积分数
	 */
	@Override
	public Integer totalConvertScore(String account) {
		Integer num = weixinPrizelogDao.totalConvertScore(account);
		if (num == null)
			return 0;
		return num;
	}
}
