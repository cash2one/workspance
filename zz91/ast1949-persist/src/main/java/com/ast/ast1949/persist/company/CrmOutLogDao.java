package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CrmOutLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmOutLogDto;

/**
 * author:kongsj date:2013-5-17
 */
public interface CrmOutLogDao {
	public Integer insert(CrmOutLog crmOutLog);

	public CrmOutLog queryById(Integer id);

	public List<CrmOutLogDto> queryDtoList(CrmOutLogDto crmOutLogDto,PageDto<CrmOutLogDto> page);

	public Integer queryDtoCount(CrmOutLogDto crmOutLogDto);
	
	public CrmOutLog queryCrmOutLogByCompanyId(Integer companyId);
	
}
