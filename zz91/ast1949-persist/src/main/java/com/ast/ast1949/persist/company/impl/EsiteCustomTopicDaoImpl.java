/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-16
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteCustomTopicDo;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.EsiteCustomTopicDao;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-16
 */
@Component("esiteCustomSTopicDao")
public class EsiteCustomTopicDaoImpl extends BaseDaoSupport implements
		EsiteCustomTopicDao {

	final static String SQL_PREFIX = "esiteCustomTopic";

	@Override
	public Integer insertTopic(EsiteCustomTopicDo topic) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertTopic"), topic);
	}

	@Override
	public List<EsiteCustomTopicDo> queryTopicByCompany(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryTopicByCompany"), companyId);
	}

	@Override
	public Integer deleteTopicById(Integer id, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("companyId", companyId);
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteTopicById"), root);
	}

	@Override
	public Integer countTopicByCompanyId(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "countTopicByCompanyId"), companyId);
	}

}
