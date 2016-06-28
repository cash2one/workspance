/*
 * 文件名称：TradePropertyServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-20 下午3:07:36
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.trade.TradePropertyDao;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.service.trade.TradePropertyService;
import com.zz91.util.Assert;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：专业属性
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("tradePropertyService")
public class TradePropertyServiceImpl implements TradePropertyService {

	@Resource
	private TradePropertyDao tradePropertyDao;
	
	@Override
	public List<TradeProperty> queryPropertyByCategoryCode(String categoryCode) {
		Assert.notNull(categoryCode, "the categoryCode can not be null");
		return tradePropertyDao.queryPropertyByCategoryCode(categoryCode);
	}

}
