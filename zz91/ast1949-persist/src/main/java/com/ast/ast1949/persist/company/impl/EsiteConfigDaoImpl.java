/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-15
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteConfigDo;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.EsiteConfigDao;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-15
 */
@Component("esiteConfigDao")
public class EsiteConfigDaoImpl extends BaseDaoSupport implements
		EsiteConfigDao {

	final static String SQL_PREFIX = "esiteConfig";

	@Override
	public Integer insertColumnConfig(EsiteConfigDo config) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertColumnConfig"), config);
	}

	@Override
	public EsiteConfigDo queryColumnConfigByCompanyId(Integer companyId,
			String columnCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("columnCode", columnCode);
		return (EsiteConfigDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryColumnConfigByCompanyId"),
				root);
	}

	@Override
	public Integer updateColumnConfigById(EsiteConfigDo config) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "updateColumnConfigById"), config);
	}

	@Override
	public Integer deleteColumnConfigByCompany(Integer companyId,
			String columnCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("columnCode", columnCode);
		return getSqlMapClientTemplate().delete(
				addSqlKeyPreFix(SQL_PREFIX, "deleteColumnConfigByCompany"),
				root);
	}

	@Override
	public Integer updateBannelPicByCompanyId(String pic, Integer cid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("pic", pic);
		root.put("cid", cid);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateBannelPicByCompanyId"),root);
	}

	@Override
	public String queryBannelPic(Integer cid) {
		return (String)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryBannelPic"),cid);
	}

	@Override
	public Integer updateIsShowForHeadPic(Integer cid, Integer isShow) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("isShow", isShow);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsShowForHeadPic"),root);
	}

}
