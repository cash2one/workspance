package com.ast.ast1949.persist.market.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.MyrcMessage;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.market.MyrcMessageDao;
@Component("myrcMessageDao")
public class MyrcMessageDaoImpl extends BaseDaoSupport implements MyrcMessageDao {
	final static String SQL_PREFIX="myrcMessage";

	@Override
	public Integer insertMyrcMessage(MyrcMessage myrcMessage) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertMyrcMessage"), myrcMessage);
	}

	@Override
	public Integer queryMyrcMessageByCIdAndType(Integer companyId, String type,String content) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("type", type);
		map.put("content", content);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMyrcMessageByCIdAndType"), map);
	}

}
