package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneSeoKeyWords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.PhoneSeoKeyWordDao;
@Component("phoneSeoKeyWordDao")
public class PhoneSeoKeyWordDaoImpl extends BaseDaoSupport implements PhoneSeoKeyWordDao {
	final static String SQL_FIX = "phoneSeoKeyWords";
	@Override
	public PhoneSeoKeyWords queryPhoneSeoKeyWordById(Integer id) {
		
		return (PhoneSeoKeyWords) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryPhoneSeoKeyWordById"), id);
	}

	@Override
	public Integer updateSeoKeyWord(PhoneSeoKeyWords phoneSeoKeyWords) {
		
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateSeoKeyWord"), phoneSeoKeyWords);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneSeoKeyWords> queryList(PhoneSeoKeyWords phoneSeoKeyWords,
			PageDto<PhoneSeoKeyWords> page) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("phoneSeoKeyWords", phoneSeoKeyWords);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryList"),map);
	}

	@Override
	public Integer queryListCount(PhoneSeoKeyWords phoneSeoKeyWords) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("phoneSeoKeyWords", phoneSeoKeyWords);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@Override
	public PhoneSeoKeyWords queryKeyWords(String pinyin, String title) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pinYin", pinyin);
		map.put("title", title);
		return (PhoneSeoKeyWords) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryKeyWords"), map);
	}

	@Override
	public Integer insert(PhoneSeoKeyWords phoneSeoKeyWords) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), phoneSeoKeyWords);
	}

	@Override
	public Integer delSeoKeyWords(Integer id) {
		
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "delSeoKeyWords"), id);
	}


}
