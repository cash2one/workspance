package com.ast.ast1949.persist.sample;

import java.util.List;

import com.ast.ast1949.domain.sample.Custominfo;

public interface CustominfoDAO {
	Integer insert(Custominfo record);

	int updateByPrimaryKey(Custominfo record);

	int updateByPrimaryKeySelective(Custominfo record);

	Custominfo selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	Custominfo selectByOrderSeq(String orderSeq);

	Custominfo selectByOrderSeqAndFlagAndType(String orderSeq, String flag, String type);
	
	public List<Custominfo> selectByOrderSeqAndType(String orderSeq, String type);
}