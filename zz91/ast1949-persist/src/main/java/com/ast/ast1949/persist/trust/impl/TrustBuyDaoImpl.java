/**
 * @author kongsj
 * @date 2015年5月8日
 * 
 */
package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuyDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustBuyDao;

@Component("trustBuyDao")
public class TrustBuyDaoImpl extends BaseDaoSupport implements TrustBuyDao {
	final static String SQL_FIX = "trustBuy";
	
	final private int DEFAULT_BATCH_SIZE = 20;
	
	@Override
	public TrustBuy queryById(Integer id){
		return (TrustBuy) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"),id);
	}
	
	@Override
	public Integer insert(TrustBuy trustBuy){
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), trustBuy);
	}
	
	@Override
	public Integer update(TrustBuy trustBuy){
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), trustBuy);
	}
	
	@Override
	public Integer updateStatusByAdmin(Integer id, String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateStatusByAdmin"), map);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TrustBuy> queryByCondition(TrustBuySearchDto trustBuySearchDto,PageDto<TrustBuyDto> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trustBuySearchDto", trustBuySearchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}
	
	@Override
	public Integer queryCountByCondition(TrustBuySearchDto trustBuySearchDto){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trustBuySearchDto", trustBuySearchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}
	
	@Override
	public Integer queryMaxId(){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryMaxId"));
	}

	@Override
	public Integer batchRefresh(Integer[] arrayIds) {
		int impacted = 0;
		int batchNum = (arrayIds.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > arrayIds.length ? arrayIds.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateForRefresh"),arrayIds[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete products failed.", e);
		}
		return impacted;
	}
	
	@Override
	public Integer batchUpdatePauseById(Integer[] ids,Integer status) {
		int impacted = 0;
		int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > ids.length ? ids.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					Map<String, Object> map = new HashMap<String, Object>(); 
					map.put("id", ids[i]);
					map.put("status", status);
					impacted += getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updatePauseById"),map);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch upadte trust_buy failed.", e);
		}
		return impacted;
	}
	
	@Override
	public Integer updateIsDelByAdmin(Integer id){
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateIsDelByAdmin"), id);
	}

	@Override
	public Integer countByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyId"), companyId);
	}

	@Override
	public Integer relateCompanyByMobile(Integer companyId, String mobile) {
		Map<String , Object> map =new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("mobile", mobile);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "relateCompanyByMobile"), map);
	}
	
}
