/*
 * 文件名称：TradePropertyDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-20 下午3:11:09
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.TradePropertyDao;
import com.zz91.ep.domain.trade.TradeProperty;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：专业属性
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("tradePropertyDao")
public class TradePropertyDaoImpl extends BaseDao implements TradePropertyDao {

	final static String SQL_PREFIX="tradeproperty";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeProperty> queryPropertyByCategoryCode(String categoryCode) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPropertyByCategoryCode"), categoryCode);
	}

}
