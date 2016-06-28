/**
 * @author shiqp
 * @date 2015-05-14
 */
package com.ast.ast1949.persist.trust;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.trust.TrustRelateSell;

public interface TrustRelateSellDao {
	/**
	 * 关联供货与采购之间的关系
	 * @param trustRelateSell
	 * @return
	 */
	public Integer insert(TrustRelateSell trustRelateSell);
	/**
	 * 根据采购id获取被采纳的供应
	 * @param buyId
	 * @return
	 */
	public List<TrustRelateSell> querySellsByBuyId(Integer buyId);
	
	/**
	 * 根据sell_id获取关系信息
	 * @param sellId
	 * @return
	 */
	public TrustRelateSell queryBySellId(Integer sellId);
	
	/**
	 * 根据companyId,检索该公司
	 * @param companyId
	 * @return
	 */
	public List<Map<String, String>> queryByCompanyIdAndBuyId(Integer companyId,Integer buyId);
	
}
