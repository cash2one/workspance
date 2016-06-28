package com.ast.ast1949.service.sample;

import java.util.Map;

import com.ast.ast1949.domain.sample.Accountseq;
import com.ast.ast1949.dto.PageDto;

public interface AccountseqService {
	Integer insert(Accountseq accountseq);

	int updateByPrimaryKey(Accountseq record);

	int updateByPrimaryKeySelective(Accountseq record);

	Accountseq selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	PageDto<Accountseq> queryListByFilter(PageDto<Accountseq> page, Map<String, Object> map);
}
