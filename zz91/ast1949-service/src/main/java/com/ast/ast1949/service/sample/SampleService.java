package com.ast.ast1949.service.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;

public interface SampleService {
	
	public final static Integer IS_DEL = 1;
	public final static Integer IS_NO_DEL = 0;
	
	public Integer createSample(Sample sample,Integer productId);
			
	public Integer editSample(Sample sample);
	
	public Sample queryByIdOrProductId(Integer id,Integer productId);
	
	public Integer checkSample(Integer productId,Integer isDel,String unpassReason);
	
	public void countSampleInfo(Map<String, Object>out,Integer companyId);

	public List<ProductsDto> queryListByCompanyId(Integer companyId,Integer productId);
	
	public Integer countAmountByCompanyId(Integer companyId);
	
	public PageDto<Sample> queryListByFilter(PageDto<Sample> page, Map<String, Object> map);
	
	public Integer updateSampleForUnpassReason(Integer id,String unpassReason);
	
}
