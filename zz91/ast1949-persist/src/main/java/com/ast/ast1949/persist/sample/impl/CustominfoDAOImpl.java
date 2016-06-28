package com.ast.ast1949.persist.sample.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.Custominfo;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.CustominfoDAO;

@Component("custominfoDao")
public class CustominfoDAOImpl extends BaseDaoSupport implements CustominfoDAO {

	public Integer insert(Custominfo record) {
		return (Integer)getSqlMapClientTemplate().insert("sample_custominfo.insert", record);
	}

	public int updateByPrimaryKey(Custominfo record) {
		int rows = getSqlMapClientTemplate().update("sample_custominfo.updateByPrimaryKey", record);
		return rows;
	}

	public int updateByPrimaryKeySelective(Custominfo record) {
		int rows = getSqlMapClientTemplate().update("sample_custominfo.updateByPrimaryKeySelective", record);
		return rows;
	}

	public Custominfo selectByPrimaryKey(Integer id) {
		Custominfo key = new Custominfo();
		key.setId(id);
		Custominfo record = (Custominfo) getSqlMapClientTemplate().queryForObject("sample_custominfo.selectByPrimaryKey",
				key);
		return record;
	}

	public int deleteByPrimaryKey(Integer id) {
		Custominfo key = new Custominfo();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("sample_custominfo.deleteByPrimaryKey", key);
		return rows;
	}

	@Override
	public Custominfo selectByOrderSeq(String orderSeq) {
		Custominfo key = new Custominfo();
		key.setOrderSeq(orderSeq);
		Custominfo record = (Custominfo) getSqlMapClientTemplate().queryForObject("sample_custominfo.selectByOrderSeq",	key);
		return record;
	}

	@Override
	public Custominfo selectByOrderSeqAndFlagAndType(String orderSeq, String flag, String infoType) {
		Custominfo key = new Custominfo();
		key.setOrderSeq(orderSeq);
		key.setFlag(flag);
		key.setInfoType(infoType);
		Custominfo record = (Custominfo) getSqlMapClientTemplate().queryForObject("sample_custominfo.selectByOrderSeqAndFlagAndType",	key);
		return record;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Custominfo> selectByOrderSeqAndType(String orderSeq, String type){
		Custominfo key=new Custominfo();
		key.setInfoType(type);
		key.setOrderSeq(orderSeq);
		return getSqlMapClientTemplate().queryForList("sample_custominfo.selectByOrderSeqAndType",key);
		
	}
}