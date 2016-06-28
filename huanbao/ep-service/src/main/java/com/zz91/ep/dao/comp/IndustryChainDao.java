package com.zz91.ep.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.IndustryChain;


public interface IndustryChainDao {
	/**
	 * 根据地区查找产业链
	 * @param size
	 * @param areaCode
	 * @return
	 */
	public List<IndustryChain>	queryIndustryChains(Integer size,String areaCode);
	
	/**
	 * 根据产业链id查询产业链
	 * @param id
	 * @return
	 */
	IndustryChain queryIndustryChainById(Integer id);
	

}
