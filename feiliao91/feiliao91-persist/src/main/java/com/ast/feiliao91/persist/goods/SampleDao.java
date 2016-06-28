package com.ast.feiliao91.persist.goods;

import java.util.List;

import com.ast.feiliao91.domain.goods.Sample;

public interface SampleDao {
	/**
	 * 新建样品
	 * @param Sample
	 * @return
	 */
	public Integer insertSample(Sample Sample);
	/**
	 * 批量插入样品
	 * @param list
	 * @return
	 */
	public Integer iterateInsert(List<Sample> list);
}
