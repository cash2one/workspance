/*
 * 文件名称：CreditFileDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-19 上午11:06:15
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.CreditFileDao;
import com.zz91.ep.domain.comp.CreditFile;


/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：荣誉证书
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("creditFileDao")
public class CreditFileDaoImpl extends BaseDao implements CreditFileDao {

	final static String SQL_PREFIX="creditfile";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditFile> queryCreditFileByCid(Integer cid, String category,
			Short checkStatus) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("category", category);
		root.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCreditFileByCid"), root);
	}
	
	@Override
	public Integer updateCreditFileName(Integer id, Integer cid, String fileName) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("id", id);
		root.put("fileName", fileName);
		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCreditFileName"), root);
	}

	@Override
	public Integer deleteCreditById(Integer id, Integer cid) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("id", id);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteCreditById"), root);
	}
	
	@Override
	public Integer createCreditFile(CreditFile creditFile) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createCreditFile"), creditFile);
	}
	
	
}
