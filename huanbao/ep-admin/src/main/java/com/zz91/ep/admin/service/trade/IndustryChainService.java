/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-9 上午09:47:11
 */
package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.dto.PageDto;

public interface IndustryChainService {
	
	/**
	 * 查询产业链
	 * @param cid 
	 * @return
	 */
	public PageDto<IndustryChain> pageIndustryChains(String areaCode,PageDto<IndustryChain> page, Integer cid);
	
	
	/**
	 * 新增产业链
	 * @param chain
	 * @return
	 */
	public Integer createIndustryChain(IndustryChain chain);
	
	/**
	 * 修改产业链
	 * @param chain
	 * @return
	 */
	public Integer updateIndustryChain(IndustryChain chain);
	
	/**
	 *	删除产业链
	 * @param id
	 * @param status
	 * @return
	 */
	public Integer updateDelStatusById(Integer id,Short status);
	
	/**
	 * 是否在首页显示
	 * @param id
	 * @param showIndex
	 * @return
	 */
	public Integer updateShowIndexById(Integer id,Short showIndex);


	/**
	 * 查询产业链地区
	 * @return
	 */
	public List<IndustryChain> querySimpChain();


	/**
	 * 根据地区查询产业链
	 * @param areaCode
	 * @return
	 */
	public List<IndustryChain> querySimpChainByAreaCode(String areaCode);
	
}
