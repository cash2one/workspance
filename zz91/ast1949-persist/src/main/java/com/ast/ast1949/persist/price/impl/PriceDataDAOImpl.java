/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-12.
 */
package com.ast.ast1949.persist.price.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ast.ast1949.domain.price.PriceDataDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.price.PriceDataDAO;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Component("priceDataDAO")
public class PriceDataDAOImpl extends SqlMapClientDaoSupport implements PriceDataDAO {
	

	public Integer deletePriceDataById(Integer id) {
		Assert.notNull(id,"the id must not be null");
		return getSqlMapClientTemplate().delete("priceData.deletePriceDataById", id);
	}

	public Integer deletePriceDataByPriceId(Integer id) {
		Assert.notNull(id,"the id must not be null");
		return getSqlMapClientTemplate().delete("priceData.deletePriceDataByPriceId", id);
	}

	public Integer insertPriceData(PriceDataDO priceData) {
		Assert.notNull(priceData,"the object of priceData must not be null");
		return (Integer) getSqlMapClientTemplate().insert("priceData.insertPriceData", priceData);
	}

	@SuppressWarnings("unchecked")
	public List<PriceDataDO> queryPriceDataByPriceId(Integer id, PageDto<PriceDataDO> page) {
		Assert.notNull(id,"the id must not be null");
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList("priceData.queryPriceDataByPriceId", map);
	}
	
	public Integer countPriceDataByPriceId(Integer id) {
		Assert.notNull(id,"the id must not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject("priceData.countPriceDataByPriceId", id);
	}

	public PriceDataDO queryPriceDataByPriceIdAndCompanyPriceId(
			Map<String, Object> param) {
		Assert.notNull(param,"the param must not be null");
		return (PriceDataDO) getSqlMapClientTemplate().queryForObject("priceData.queryPriceDataByPriceIdAndCompanyPriceId", param);
	}

	public Integer updatePriceDataById(PriceDataDO priceData) {
		Assert.notNull(priceData,"the object of priceData must not be null");
		return getSqlMapClientTemplate().update("priceData.updatePriceDataById", priceData);
	}

	public PriceDataDO queryPriceDataById(Integer id) {
		Assert.notNull(id,"the id must not be null");
		return (PriceDataDO) getSqlMapClientTemplate().queryForObject("priceData.queryPriceDataById", id);
	}

}
