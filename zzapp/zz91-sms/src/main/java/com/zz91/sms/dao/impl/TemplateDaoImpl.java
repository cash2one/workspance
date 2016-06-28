package com.zz91.sms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.sms.dao.BaseDaoSupport;
import com.zz91.sms.dao.TemplateDao;
import com.zz91.sms.domain.Template;

@Component("templateDao")
public class TemplateDaoImpl extends BaseDaoSupport implements TemplateDao {
	
	final static String SQL_PREFIX = "template";
	
	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"), id);
	}

	@Override
	public Integer insert(Template template) {
		return (Integer)getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"), template);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Template> query() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "query"));
	}

	@Override
	public Integer update(Template template) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "update"), template);
	}

	@Override
	public Template queryOne(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (Template)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryOne"), map);
	}
	
	
	/*****************************************************/

	@Override
	public Template queryTemplateByCode(String code) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("code", code);
		return (Template)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryTemplateByCode"), map);
	}

}
