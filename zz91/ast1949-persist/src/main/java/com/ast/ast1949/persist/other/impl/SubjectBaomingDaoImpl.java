package com.ast.ast1949.persist.other.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.other.SubjectBaomingDao;

@Component("SubjectBaomingDao")
public class SubjectBaomingDaoImpl extends BaseDaoSupport implements SubjectBaomingDao{
	final static String SQL_FIX = "subjectBaoming";
	@Override
	public Integer createNewBaoming(String title, String content) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("content", content);
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "createNewBaoming"),map);
	}	
}
