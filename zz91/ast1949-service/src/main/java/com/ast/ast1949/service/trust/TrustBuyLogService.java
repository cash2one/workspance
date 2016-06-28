/**
 * @author kongsj
 * @date 2015年5月7日
 * 
 */
package com.ast.ast1949.service.trust;

import com.ast.ast1949.domain.trust.TrustBuyLog;
import com.ast.ast1949.dto.PageDto;

public interface TrustBuyLogService {

	public Integer createLog(TrustBuyLog trustBuyLog);

	public PageDto<TrustBuyLog> pageLog(TrustBuyLog trustBuyLog,
			PageDto<TrustBuyLog> page);
	
	public Integer countLog(Integer companyId);
}
