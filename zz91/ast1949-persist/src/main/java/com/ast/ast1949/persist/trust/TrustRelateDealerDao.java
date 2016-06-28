/**
 * @author shiqp
 * @date 2015-05-14
 */
package com.ast.ast1949.persist.trust;

import com.ast.ast1949.domain.trust.TrustRelateDealer;

public interface TrustRelateDealerDao {
	/**
	 * 根据流水线检索交易员id
	 * @param buyNo
	 * @return
	 */
	public Integer queryRelateDealerByBuyNo(String buyNo);
	
	/**
	 * 建立采购与交易员的关系
	 * @param dealer
	 * @return
	 */
	public Integer insertRelateDealer(TrustRelateDealer dealer);
	
	/**
	 * 修改采购与交易员的关系
	 * @param dealerId
	 * @param buyNo
	 * @return
	 */
	public Integer updateRelateDealer(Integer dealerId,String buyNo);

}
