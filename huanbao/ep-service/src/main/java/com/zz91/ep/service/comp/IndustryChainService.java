package com.zz91.ep.service.comp;

import java.util.List;

import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.domain.comp.IndustryChainDto;

public interface IndustryChainService {
	
	/**
	 * 根据地区编号查询相应的产业链
	 * @param size
	 * @param areaCode
	 * @return
	 */
	List<IndustryChain> queryIndustryChains(Integer size,String areaCode);
	
	/**
	 * 根据产业链id查询产业链name
	 * @param id
	 * @return
	 */
	IndustryChain queryIndustryChainName(Integer id);
	
	/**
	 * 根据cid 查询
	 * @param cid
	 * @return
	 */
	List<IndustryChainDto> queryIndustryChainByCid(Integer cid,String areaCode);
}
