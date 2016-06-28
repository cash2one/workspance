/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.persist.market.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.market.MarketPic;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.market.MarketPicDao;
import com.ast.ast1949.util.Assert;

@Component("marketPicDao")
public class MarketPicDaoImpl extends BaseDaoSupport implements MarketPicDao {
	private final static String sqlPreFix = "marketPic";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketPic> queryPicByMarketId(Integer marketId) {
		
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryPicByMarketId"), marketId);
	}

	@Override
	public Integer insert(MarketPic marketPic) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insert"), marketPic);
	}
	final private int DEFAULT_BATCH_SIZE = 20;
	@Override
	public Integer batchDelMarketPicById(Integer[] entities) {
		Assert.notNull(entities, "entities is not null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += delMarketPicById(entities[i],2);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.", e);
		}
		return impacted;
	}
    @Override
	public Integer delMarketPicById( Integer id,Integer checkStatus) {
    	Map<String,Integer> map =  new HashMap<String,Integer>();
    	map.put("id", id);
    	map.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "delMarketPicById"), map);
	}
    @Override
    public Integer updateMarketDefaultPic(Integer marketId,String isDefault){
    	Map<String, Object>map=new HashMap<String, Object>();
    	map.put("marketId", marketId);
    	map.put("isDefault", isDefault);
    	return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateMarketDefaultPic"), map);
    }
    
    @Override
    public Integer updateMarketDefaultPicById(Integer id,String isDefault){
    	Map<String, Object>map=new HashMap<String, Object>();
    	map.put("id", id);
    	map.put("isDefault", isDefault);
    	return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateMarketDefaultPicById"), map);
    }

	@Override
	public void updateMarketIdById(Integer marketId, Integer id) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("marketId", marketId);
		map.put("id", id);
		getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateMarketIdById"), map);
	}

	@Override
	public MarketPic queryPicInfoByMarketId(Integer marketId) {
		return (MarketPic) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryPicInfoByMarketId"), marketId);
	}

	@Override
	public void updateDefaultById(Integer id, String isDefault) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("isDefault", isDefault);
	    getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateDefaultById"), map);
	}



}
