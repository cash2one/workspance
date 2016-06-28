/**
 * @author kongsj
 * @date 2015年5月11日
 * 
 */
package com.ast.ast1949.service.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.dto.PageDto;

public interface TrustDealerService {

	public Integer createOneDealer(String name,String tel,String qq);
	
	public Integer updateOneDealer(TrustDealer trustDealer);
	
	public PageDto<TrustDealer> pageByCondition(TrustDealer trustDealer,PageDto<TrustDealer> page);
	
	public List<TrustDealer> queryAllDealer();
	
	public Integer deleteDealer(Integer id); 

}
