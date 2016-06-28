/*
 * 文件名称：IbdCategoryDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午5:49:43
 * 版本号　：1.0.0
 */
package com.zz91.ep.admin.dao.trade.impl;

import org.springframework.stereotype.Repository;
import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.IbdCompanyDao;
import com.zz91.ep.domain.trade.IbdCompany;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：行业买家库
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("ibdCompanyDao")
public class IbdCompanyDaoImpl extends BaseDao implements IbdCompanyDao {

	final static String SQL_PREFIX = "ibdcompany";

//	@Override
//	public Integer queryCountByCategoryCode(String categoryCode) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCountByCategoryCode"), categoryCode);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<IbdCompany> queryCompanyByCategoryAndKewords(String categoryCode, String keywords, PageDto<IbdCompany> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("categoryCode", categoryCode);
//		root.put("keywords", keywords);
//		root.put("page", page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompanyByCategoryAndKewords"), root);
//	}

//	@Override
//	public Integer queryCompanyByCategoryAndKewordsCount(String categoryCode, String keywords) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("categoryCode", categoryCode);
//		root.put("keywords", keywords);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompanyByCategoryAndKewordsCount"), root);
//	}

//	@Override
//	public IbdCompany queryIbdCompanyById(Integer id) {
//		return (IbdCompany) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryIbdCompanyById"), id);
//	}

//	@Override
//	public IbdCompany queryContactByCid(Integer id) {
//		return (IbdCompany) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryContactByCid"), id);
//	}

	@Override
	public Integer insertIbdCompanyByAdmin(IbdCompany comp) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertIbdCompanyByAdmin"), comp);
	}

}