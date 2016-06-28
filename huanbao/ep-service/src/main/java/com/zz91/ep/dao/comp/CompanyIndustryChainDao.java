package com.zz91.ep.dao.comp;

import java.util.List;

public interface CompanyIndustryChainDao {
	
	/**
	 * 插入公司产业信息
	 * @param industryId
	 * @param cid
	 * @return
	 */
	Integer insertIndustryChain(Integer industryId,Integer cid);
	
	/**
	 * 根据cid查询产业链id
	 * @param cid
	 * @return
	 */
	List<Integer> queryIndustryChainIdByCid(Integer cid);
	
	/**
	 * 根据cid查询id
	 * @param 
	 * @return
	 */
	List<Integer> queryIdByCid(Integer cid);
	
	/**
	 * 根据id修改CompanyIndustryChain状态
	 * @param id
	 * @return
	 */
	Integer updateCompanyIndustryChain(Integer id,Integer chainId,Short delStatus);
	
}
