package com.ast.ast1949.persist.price.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.price.PriceOffer;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
/**
 * @author shiqp
 * @date 2015-05-04
 */
import com.ast.ast1949.persist.price.PriceOfferDao;

@Component("priceOfferDao")
public class PriceOfferDaoImpl extends BaseDaoSupport implements PriceOfferDao {
	final static String SQL_PREFIX="priceOffer";
	
	@Override
	public Integer insertPriceOffer(PriceOffer offer) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertPriceOffer"), offer);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceOffer> queryOfferByCompanyId(PageDto<PriceOffer> page,Integer companyId, String keywords) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page", page);
		map.put("companyId", companyId);
		map.put("keywords", keywords);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryOfferByCompanyId"), map);
	}
	
	@Override
	public Integer countOfferByCompanyId(Integer companyId,String keywords) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("keywords", keywords);
		return (Integer) getSqlMapClientTemplate()	.queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countOfferByCompanyId"),map);
	}

	@Override
	public PriceOffer queryOfferById(Integer id) {
		return (PriceOffer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOfferById"),id);
	}

	@Override
	public Integer updateOfferById(PriceOffer offer) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateOfferById"),offer);
	}

	@Override
	public void updateDownloadNumById(Integer id, Integer downloadNum) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", id);
		map.put("downloadNum", downloadNum);
		getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateDownloadNumById"),map);
	}

	@Override
	public Integer updateIsDelByid(Integer id, Integer isDel) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("id", id);
		map.put("isDel", isDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsDelByid"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceOffer> listOfferByCondition(PageDto<PriceOffer> page, PriceOffer priceOffer, String from, String to,String menberShip) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("priceOffer", priceOffer);
		map.put("from", from);
		map.put("to", to);
		map.put("menberShip", menberShip);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "pageOfferByCondition"),map);
	}

	@Override
	public Integer countOfferByCondition(PriceOffer priceOffer, String from, String to, String menberShip) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("priceOffer", priceOffer);
		map.put("from", from);
		map.put("to", to);
		map.put("menberShip", menberShip);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countOfferByCondition"),map);
	}

	@Override
	public Integer updateCheckInfo(Integer id, Integer checkStatus,String checkPerson,String checkReason,Integer isDel) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("checkStatus", checkStatus);
		map.put("checkPerson", checkPerson);
		map.put("checkReason", checkReason);
		map.put("isDel", isDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckInfo"),map);
	}

}
