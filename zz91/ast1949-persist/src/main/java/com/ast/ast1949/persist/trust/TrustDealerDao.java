/**
 * @author kongsj
 * @date 2015年5月11日
 * 
 */
package com.ast.ast1949.persist.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.dto.PageDto;

public interface TrustDealerDao {
	public Integer insert(TrustDealer trustDealer);

	public Integer update(TrustDealer trustDealer);

	public TrustDealer queryById(Integer id);

	public List<TrustDealer> queryByCondition(TrustDealer trustDealer,PageDto<TrustDealer> page);

	public Integer queryCountByCondition(TrustDealer trustDealer);
	
	public List<TrustDealer> queryAllDealer();
	
	public Integer deleteDealer(Integer id);

}
