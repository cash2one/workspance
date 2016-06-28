package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustPic;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustPicDao;
@Component("trustPicDao")
public class TrustPicDaoImpl extends BaseDaoSupport implements TrustPicDao {
	final static String SQL_FIX = "trustPic";

	@Override
	public Integer createTradePic(TrustPic trustPic) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insertTrustPic"), trustPic);
	}

	@Override
	public Integer updateTradeInfo(TrustPic trustPic) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updatePicInfo"), trustPic);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustPic> querypicList(PageDto<TrustPic> page, Integer tradeId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("tradeId", tradeId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "querypicList"), map);
	}

	@Override
	public Integer countpicList(Integer tradeId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countpicList"), tradeId);
	}

	@Override
	public Integer updateTradeIdByPicAddress(Integer tradeId, String picAddress) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tradeId", tradeId);
		map.put("picAddress", picAddress);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateTradeIdByPicAddress"), map);
	}

	@Override
	public TrustPic queryOnePic(Integer tradeId) {
		return (TrustPic) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryOnePic"), tradeId);
	}

	@Override
	public TrustPic queryById(Integer id) {
		return (TrustPic) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

}
