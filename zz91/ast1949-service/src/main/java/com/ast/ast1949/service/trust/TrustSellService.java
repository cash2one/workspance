/**
 * @author kongsj
 * @date 2015年5月9日
 * 
 */
package com.ast.ast1949.service.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustSell;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustSellDto;

public interface TrustSellService {
	final static String STATUS_00 = "00"; // “已报价”
	final static String STATUS_01 = "01"; // “报价被采纳”
	final static String STATUS_99 = "99"; // “报价被否决”
	
	/**
	 * 发布一条供货信息
	 */
	public Integer publishTrustSell(Integer companyId,Integer buyId,String content);
	
	/**
	 * 后台发布一条供货，帐号必须存在，buyID必须存在，content必须存在
	 */
	public Integer publishTrustSellByAccount(String account, Integer buyId, String content);
	
	/**
	 * 检索该公司供货信息
	 */
	public PageDto<TrustSellDto> pageByCondition(TrustBuySearchDto searchDto,PageDto<TrustSellDto>page);
	
	/**
	 * 更改供货状态
	 */
	public Integer editByStatus(Integer id,String status);
	
	/**
	 * 检索该公司的供货信息
	 * @param sell
	 * @param page
	 * @return
	 */
	public PageDto<TrustSellDto> pageSupplyByCondition(TrustSell sell,PageDto<TrustSellDto> page);
	
	/**
	 * 检索该公司所有供货信息条数
	 * @param companyId
	 * @return
	 */
	public Integer countByCompanyId(Integer companyId);
	/**
	 * 检索该公司供过货的采购
	 * @param companyId
	 * @return
	 */
	public List<Integer> queryBuyIdByCompanyId(Integer companyId);
}
