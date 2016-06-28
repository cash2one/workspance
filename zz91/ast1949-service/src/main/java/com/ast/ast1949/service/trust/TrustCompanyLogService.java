/**
 * @author kongsj
 * @date 2015年5月7日
 * 
 */
package com.ast.ast1949.service.trust;

import com.ast.ast1949.domain.trust.TrustCompanyLog;
import com.ast.ast1949.dto.PageDto;

public interface TrustCompanyLogService {

	public Integer createLog(TrustCompanyLog trustCompanyLog);

	public PageDto<TrustCompanyLog> pageLog(TrustCompanyLog trustCompanyLog,PageDto<TrustCompanyLog> page);
	
	public Integer countLog(Integer companyId);
}
