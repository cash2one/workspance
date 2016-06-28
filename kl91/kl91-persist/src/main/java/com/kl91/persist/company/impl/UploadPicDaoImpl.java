package com.kl91.persist.company.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.UploadPic;
import com.kl91.persist.BaseDaoSupport;
import com.kl91.persist.company.UploadPicDao;

@Component("uploadPicDao")
public class UploadPicDaoImpl extends BaseDaoSupport implements UploadPicDao{

	private static String SQL_PREFIX = "uploadPic";
	@Override
	public Integer deleteById(Integer id) {
		
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"),id);
	}

	@Override
	public Integer insert(UploadPic uploadPic) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"),uploadPic);
	}

	@Override
	public UploadPic queryById(Integer id) {
		return (UploadPic) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"),id);
	}

	@Override
	public Integer update(UploadPic uploadPic) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "update"),uploadPic);
	}

	@Override
	public Integer updateTargetId(Integer id, Integer targetId,Integer targetType) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("id", id);
		root.put("targetId", targetId);
		root.put("targetType", targetType);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateTarget"),root);
	}

	@Override
	public UploadPic queryByTargetId(Integer targetId) {
		return (UploadPic) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryByTargetId"),targetId);
	}

}
