package com.ast.ast1949.persist.trust;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.trust.TrustCrm;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCrmDto;
import com.ast.ast1949.dto.trust.TrustCrmSearchDto;

public interface TrustCrmDao {
	public TrustCrm queryById(Integer id);

	public TrustCrm queryByCompanyId(Integer companyId);

	public Integer insert(TrustCrm trustCrm);

	public List<TrustCrm> queryByCondition(TrustCrmSearchDto searchDto, PageDto<TrustCrmDto> page);

	public Integer queryCountByCondition(TrustCrmSearchDto searchDto);

	public Integer updateStar(Integer companyId, Integer star);

	public Integer updateContact(Integer companyId, Date gmtNextVisit);

	public Integer updateStatus(Integer companyId, Integer isPublic, Integer isRubbish);

	public Integer updateTrustAccount(Integer companyId, String crmAccount);

	public Integer selectDayLog(Map<String, Object> map);
}
