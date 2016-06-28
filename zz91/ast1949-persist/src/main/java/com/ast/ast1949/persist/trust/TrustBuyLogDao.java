/**
 * @author kongsj
 * @date 2015年5月8日
 * 
 */
package com.ast.ast1949.persist.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustBuyLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCsLogDto;
import com.ast.ast1949.dto.trust.TrustCsLogSearchDto;

public interface TrustBuyLogDao {

	public TrustBuyLog queryById(Integer id);

	public Integer insert(TrustBuyLog trustBuyLog);

	public List<TrustBuyLog> queryByCondition(TrustBuyLog trustBuyLog,PageDto<TrustBuyLog> page);
	

	public Integer queryCountByCondition(TrustBuyLog TrustBuyLog);
	
	public Integer countByCompanyIdForOneMonth(Integer companyId);
	
	/**
	 * 给后台统计小计工作检索使用的后台专用方法
	 * @return
	 */
	public List<TrustBuyLog> queryByConditionByAdmin(TrustCsLogSearchDto searchDto,PageDto<TrustCsLogDto> page);
	
	public Integer queryCountByConditionByAdmin(TrustCsLogSearchDto searchDto);

	public Integer queryRecentStar(Integer buyId, Integer id);
}
