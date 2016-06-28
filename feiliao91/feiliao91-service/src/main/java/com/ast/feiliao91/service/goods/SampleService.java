package com.ast.feiliao91.service.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Sample;

public interface SampleService {
	/**
	 * 
	 * @param sample
	 * @return
	 */
	public Integer insertGoods(Sample sample);
	/**
	 * 批量插入样品
	 * @param list
	 * @return
	 */
	public Integer iterateInsert(List<Sample> list);
}
