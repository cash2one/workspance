/**
 * @author kongsj
 * @date 2015年5月8日
 * 
 */
package com.ast.ast1949.persist.trust;

import java.util.List;

import com.ast.ast1949.domain.trust.TrustCompanyLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCsLogDto;
import com.ast.ast1949.dto.trust.TrustCsLogSearchDto;

public interface TrustCompanyLogDao {

	public TrustCompanyLog queryById(Integer id);

	public Integer insert(TrustCompanyLog trustCompanyLog);

	public List<TrustCompanyLog> queryByCondition(TrustCompanyLog trustCompanyLog,PageDto<TrustCompanyLog> page);

	public Integer queryCountByCondition(TrustCompanyLog trustCompanyLog);

	public Integer countByCompanyIdForOneMonth(Integer companyId);

	public List<TrustCompanyLog> queryByConditionByAdmin(TrustCsLogSearchDto searchDto,PageDto<TrustCsLogDto> page);
	
	public Integer queryCountByConditionByAdmin(TrustCsLogSearchDto searchDto);

	public Integer queryRecentStar(Integer companyId, Integer id);
}