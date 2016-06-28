/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.persist.information.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.PeriodicalDetails;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.information.PeriodicalDetailsDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("periodicalDetailsDAO")
public class PeriodicalDetailsDAOImpl extends SqlMapClientDaoSupport implements PeriodicalDetailsDAO {

	public Integer batchInsertPeriodicalDetails(List<PeriodicalDetails> details) {
		Assert.notNull(details, "the details list can not be null");
		Integer impact=0;
		try {
			getSqlMapClient().startBatch();
				for(PeriodicalDetails d:details){
					if((Integer)getSqlMapClientTemplate().insert("periodicalDetails.insertPeriodicalDetails", d)>0){
						impact+=1;
					}
				}
			getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			throw new PersistLayerException("an error occur when batch insert periodical details",e);
		}
		return impact;
	}

	public Integer deleteDetails(Integer[] detailsIdArray) {
		Assert.notNull(detailsIdArray, "the details list can not be null");
		Integer impact=0;
		try {
			getSqlMapClient().startBatch();
				for(Integer i:detailsIdArray){
					if(getSqlMapClientTemplate().delete("periodicalDetails.deleteDetailsById",i)>0){
						impact+=1;
					}

				}
			getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			throw new PersistLayerException("an error occur when batch delete periodical details",e);
		}
		return impact;
	}

	public List<PeriodicalDetails> listPreviewDetailsByPeriodicalId(
			Integer periodicalId) {
		Assert.notNull(periodicalId, "the periodicalId list can not be null");
		return getSqlMapClientTemplate().queryForList("periodicalDetails.listPreviewDetailsByPeriodicalId",periodicalId);
	}

	public Integer updateBaseDetails(PeriodicalDetails details) {
		Assert.notNull(details, "the details list can not be null");
		return getSqlMapClientTemplate().update("periodicalDetails.updateBaseDetails", details);
	}

	public Integer updatePageType(Integer id, Integer pageType) {
		Assert.notNull(pageType	, "the pageType list can not be null");
		Assert.notNull(id , "the id list can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		if(pageType==null||"".equals(pageType)){
			pageType=PAGE_TYPE_BODY;
		}
		root.put("pageType", pageType);

		return getSqlMapClientTemplate().update("periodicalDetails.updatePageType", root);
	}

	public Integer countPageDetailsByPeriodicalId(Integer periodicalId) {
		Assert.notNull(periodicalId, "the periodicalId list can not be null");
		return (Integer) getSqlMapClientTemplate().queryForObject("periodicalDetails.countPageDetailsByPeriodicalId", periodicalId);
	}

	public List<PeriodicalDetails> pageDetailsByPeriodicalId(
			Integer periodicalId, PageDto page) {
		Assert.notNull(periodicalId, "the periodicalId list can not be null");
		Assert.notNull(page, "the page list can not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("periodicalId",periodicalId);
		root.put("page",page);
		return getSqlMapClientTemplate().queryForList("periodicalDetails.pageDetailsByPeriodicalId",root);
	}

	public Integer deleteDetailsByPeriodicalId(Integer periodicalId) {
		Assert.notNull(periodicalId, "the periodicalId list can not be null");
		return getSqlMapClientTemplate().delete("periodicalDetails.deleteDetailsByPeriodicalId", periodicalId);
	}

	public PeriodicalDetails selectDetailsById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (PeriodicalDetails) getSqlMapClientTemplate().queryForObject("periodicalDetails.selectDetailsById", id);
	}

	@SuppressWarnings("unchecked")
	public List<PeriodicalDetails> pageDetailsByPeriodicalIdAndType(Integer periodicalId,
			Integer type) {
		Assert.notNull(type, "type is not null");
		Assert.notNull(periodicalId, "periodicalId is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("periodicalId", periodicalId);
		return getSqlMapClientTemplate().queryForList("periodicalDetails.pageDetailsByPeriodicalIdAndType", map);
	}

}
