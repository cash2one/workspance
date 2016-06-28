package com.ast.feiliao91.persist.goods.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.Sample;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.goods.SampleDao;

@Component("sampleDao")
public class SampleDaoImpl extends BaseDaoSupport implements SampleDao {
	
	final static String SQL_PREFIX="sample";
	
	@Override
	public Integer insertSample(Sample Sample) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertSample"), Sample);
	}
	
	@Override
	public Integer iterateInsert(List<Sample> list){
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "iterateInsert"), list);
	}

}
