package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.Sample;

public interface SampleDao {
	public Integer insert(Sample sample);
	
	public Integer update(Sample sample);
	
	public Sample queryByProductId(Integer productId);
	
	public Sample queryById(Integer id);

	public Integer updateDelStatus(Integer id, Integer isDel);
	
	public Integer updateSampleForUnpassReason(Integer id,String unpassReason);

	public Integer countByCompanyId(Integer companyId);

	public List<Sample> queryListByCompanyId(Integer companyId,Integer size);
	
	public Integer countAmountByCompanyId(Integer companyId);

	public List<Sample> queryListByFilter(Map<String, Object> filterMap);

	public Integer queryListByFilterCount(Map<String, Object> filterMap);
}
