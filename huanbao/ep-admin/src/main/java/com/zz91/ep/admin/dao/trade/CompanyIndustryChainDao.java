/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-15 下午01:32:45
 */
package com.zz91.ep.admin.dao.trade;

import com.zz91.ep.domain.comp.CompanyIndustryChain;

public interface CompanyIndustryChainDao {
	
	public Integer insertCompIndustryChain(CompanyIndustryChain chain);
	
	public Integer updateCompIndustryChain(CompanyIndustryChain chain);
	
	public Integer updateDelStatusByCidAndChainId(Integer cid,Integer chainId, Short delStatus);
	
	public Integer queryCountByCidAndChainId(Integer cid,Integer chainId);
}
