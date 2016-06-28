/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-15 上午11:33:34
 */
package com.zz91.ep.admin.service.trade;

import com.zz91.ep.domain.comp.CompanyIndustryChain;

public interface CompanyIndustryChainService {
	
	/**
	 * 新增公司产业链
	 * @param chain
	 * @return
	 */
	public Integer createCompIndustryChain(CompanyIndustryChain chain);
	
	/**
	 * 更改产业链
	 * @param chain
	 * @return
	 */
	public Integer updateCompIndustryChain(CompanyIndustryChain chain);
	
	/**
	 * 更新删除状态
	 * @param cid
	 * @param chainId
	 * @param delStatus 
	 * @return
	 */
	public Integer updateDelStatusByCidAndChainId(Integer cid,Integer chainId, Short delStatus);

}
