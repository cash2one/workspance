/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-11-9 上午11:53:32
 */
package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.comp.IndustryChain;
import com.zz91.ep.dto.PageDto;

public interface IndustryChainDao {
	
	/**
	 * 查询产业链
	 * @param areaCode 地区编号
	 * @param page
	 * @param cid 
	 * @return
	 */
	public List<IndustryChain> queryIndustryChains(String areaCode,PageDto<IndustryChain> page, Integer cid);
	
	/**
	 * 查询产业链总数
	 * @param areaCode 地区编号
	 * @param cid 
	 * @return
	 */
	public Integer queryIndustryChainsCount(String areaCode, Integer cid);
	
	/**
	 * 新增产业链
	 * @param chain
	 * @return
	 */
	public Integer insertIndustryChain(IndustryChain chain);
	
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
	 * @param showIndex 首页展示
	 * @return
	 */
	public Integer updateShowIndexById(Integer id,Short showIndex);

	/**
	 * 查询产业链信息
	 * @return
	 */
	public List<IndustryChain> querySimpChain();

	/**
	 * 根据地区查询产业链信息
	 * @param areaCode
	 * @return
	 */
	public List<IndustryChain> querySimpChainByAreaCode(String areaCode);

}
