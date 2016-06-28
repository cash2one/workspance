package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.Accountseq;

public interface AccountseqDAO {
	Integer insert(Accountseq record);

	int updateByPrimaryKey(Accountseq record);

	int updateByPrimaryKeySelective(Accountseq record);

	Accountseq selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	List<Accountseq> queryListByFilter(Map<String, Object> filterMap);

	Integer queryListByFilterCount(Map<String, Object> filterMap);
}