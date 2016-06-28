/**
 * @author kongsj
 * @date 2015年5月8日
 * 
 */
package com.ast.ast1949.persist.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuyDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;

public interface TrustBuyDao {

	public TrustBuy queryById(Integer id);

	public Integer insert(TrustBuy trustBuy);

	public Integer updateStatusByAdmin(Integer id, String status);

	public List<TrustBuy> queryByCondition(TrustBuySearchDto trustBuySearchDto,PageDto<TrustBuyDto> page);

	public Integer queryCountByCondition(TrustBuySearchDto trustBuySearchDto);

	public Integer queryMaxId();

	public Integer update(TrustBuy trustBuy);
	
	public Integer batchRefresh(Integer [] arrayIds);

	public Integer updateIsDelByAdmin(Integer id);
	
	public Integer countByCompanyId(Integer companyid);
	
	public Integer relateCompanyByMobile(Integer companyId,String mobile);

	public Integer batchUpdatePauseById(Integer[] ids, Integer status);
}
