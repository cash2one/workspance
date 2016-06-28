package com.ast.ast1949.persist.sample;

import com.ast.ast1949.domain.sample.SampleRelateProduct;

public interface SampleRelateProductDao {

	public Integer insert(SampleRelateProduct sampleRelateProduct);

	public Integer queryBySampleIdForProductId(Integer sampleId);
	
	public Integer queryByProductIdForSampleId(Integer productId);
	
	public Integer buildRelateByProductIdAndSampleId(Integer sampleId,Integer productId);
	
	public Integer countAddByDate(String from ,String to);
}
