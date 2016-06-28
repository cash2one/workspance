/**
 * @author kongsj
 * @date 2014年7月24日
 * 
 */
package com.zz91.ep.service.common;

import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompanyNormDto;
import com.zz91.ep.dto.search.SearchCompanyDto;

public interface CoreSeekService {
	
	public PageDto<CompanyNormDto> pageCompany(SearchCompanyDto search, PageDto<CompanyNormDto> page);

}
