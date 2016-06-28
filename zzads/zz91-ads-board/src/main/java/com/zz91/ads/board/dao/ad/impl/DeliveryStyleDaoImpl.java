/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-15.
 */
package com.zz91.ads.board.dao.ad.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.BaseDaoSupport;
import com.zz91.ads.board.dao.ad.DeliveryStyleDao;
import com.zz91.ads.board.domain.ad.DeliveryStyle;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("deliveryStyleDao")
public class DeliveryStyleDaoImpl extends BaseDaoSupport implements DeliveryStyleDao {

	final static String sqlPreFix = "deliveryStyle";

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryStyle> queryDeliveryStyle() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryDeliveryStyle"));
	}

	@Override
	public Integer deleteStyle(Integer id) {
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(sqlPreFix, "deleteStyle"), id);
	}

	@Override
	public Integer insertStyle(DeliveryStyle style) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insertStyle"), style);
	}

	@Override
	public Integer updateStyle(DeliveryStyle style) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateStyle"), style);
	}

}
