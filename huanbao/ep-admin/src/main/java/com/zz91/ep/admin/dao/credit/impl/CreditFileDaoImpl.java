package com.zz91.ep.admin.dao.credit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.credit.CreditFileDao;
import com.zz91.ep.domain.comp.CreditFile;

/**
 * 
 * @author tulf
 * 诚信文件相关操作
 * 2011-09-16
 */
@Repository("creditFileDao")
public class CreditFileDaoImpl extends BaseDao implements CreditFileDao{
	
	final static String SQL_PREFIX="creditFile";
	
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
	public Integer createCreditFile(CreditFile creditFile) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "createCreditFile"), creditFile);
	}

//	@Override
//	public Integer updateCreditFileName(Integer id, Integer cid, String fileName) {
//		Map<String, Object> root=new HashMap<String, Object>();
//		root.put("cid", cid);
//		root.put("id", id);
//		root.put("fileName", fileName);
//		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCreditFileName"), root);
//	}

	@Override
	public Integer deleteCreditById(Integer id, Integer cid) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("id", id);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteCreditById"), root);
	}

	@Override
	public Integer updateCheckStatus(Integer id, Integer cid, Short status,String person) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("cid", cid);
		root.put("status", status);
		root.put("person", person);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCheckStatus"), root);
	}

	@Override
	public Integer queryCreditFileCount(Integer cid, Short checkStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("checkStatus", checkStatus);
 		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCreditFileCount"), root);
	}
	
}
