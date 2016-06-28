/**
 * @author kongsj
 * @date 2014年12月1日
 * 
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsSystemMessage;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsSystemMessageDao;

@Component("bbsSystemMessageDao")
public class BbsSystemMessageDaoImpl extends BaseDaoSupport implements BbsSystemMessageDao{

	final static String SQL_FIX = "bbsSystemMessage";
	
	@Override
	public Integer updateForRead(Integer id) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateForRead"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsSystemMessage> queryForList(	BbsSystemMessage bbsSystemMessage, PageDto<BbsSystemMessage> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bbsSystemMessage", bbsSystemMessage);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryForList"), map);
	}

	@Override
	public Integer queryForListCount(BbsSystemMessage bbsSystemMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bbsSystemMessage", bbsSystemMessage);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryForListCount"), map);
	}

}
