package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneBlacklist;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneBlacklistDao;
import com.zz91.util.lang.StringUtils;

@Component("phoneBlacklistDao")
public class PhoneBlacklistDaoImpl extends BaseDaoSupport implements
		PhoneBlacklistDao {

	final static String SQL_FIX = "phoneBlacklist";
	final private int DEFAULT_BATCH_SIZE = 20;

	@Override
	public Integer insert(PhoneBlacklist phoneBlacklist) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), phoneBlacklist);
	}
	
	@Override
	public Integer batchInsert(List<PhoneBlacklist> list) {
		int impacted = 0;
		int batchNum = (list.size() + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > list.size() ? list.size() : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += insert(list.get(i));
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
		}
		return impacted;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneBlacklist> query(PhoneBlacklist phoneBlacklist,
			PageDto<PhoneBlacklist> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneBlacklist", phoneBlacklist);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "query"), map);
	}

	@Override
	public Integer queryCount(PhoneBlacklist phoneBlacklist) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneBlacklist", phoneBlacklist);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCount"), map);
	}
	
	@Override
	public PhoneBlacklist queryById(Integer id) {
		return (PhoneBlacklist) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}
	
	@Override
	public Integer batchDelete(String[] ids) {
		int impacted = 0;
		int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > ids.length ? ids.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					if (StringUtils.isNumber(ids[i])) {
						impacted += deleteById(Integer.valueOf(ids[i]));
					}
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
		}
		return impacted;
	}
	
	@Override
	public Integer deleteById(Integer id){
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "deleteById"), id);
	}
	
	@Override
	public Integer isExistByPhone(String phone,Integer phoneLogId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("phoneLogId", phoneLogId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "isExistByPhone"), map);
	}
}
