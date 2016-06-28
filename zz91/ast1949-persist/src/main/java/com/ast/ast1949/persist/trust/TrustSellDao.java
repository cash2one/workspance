/**
 * @author kongsj
 * @date 2015年5月9日
 * 
 */
package com.ast.ast1949.persist.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustSell;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustSellDto;

public interface TrustSellDao {
	/**
	 * 发布一条供货信息
	 */
	public Integer insert(TrustSell trustSell);

	/**
	 * 检索该公司供货信息
	 */
	public List<TrustSellDto> queryByCondition(TrustBuySearchDto searchDto,PageDto<TrustSellDto> page);

	public Integer queryCountByCondition(TrustBuySearchDto searchDto);

	/**
	 * 更改供货状态
	 */
	public Integer updateByStatus(Integer id, String status);

	public TrustSell queryById(Integer id);
	
	/**
	 * 检索该公司供货信息
	 * @param sell
	 * @param page
	 * @return
	 */
	public List<TrustSellDto> querySupplyByCondition(TrustSell sell,PageDto<TrustSellDto> page);
	/**
	 * 检索该公司供货信息数
	 * @param sell
	 * @return
	 */
	public Integer countSupplyByCondition(TrustSell sell);
	/**
	 * 检索该公司供过货的采购
	 * @param companyId
	 * @return
	 */
	public List<Integer> queryBuyIdByCompanyId(Integer companyId);

	public Integer countByCompanyId(Integer companyId);
}
