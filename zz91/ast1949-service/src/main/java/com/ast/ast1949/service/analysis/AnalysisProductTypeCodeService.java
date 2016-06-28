package com.ast.ast1949.service.analysis;

import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisProductTypeCode;
import com.ast.ast1949.dto.analysis.AnalysisProductTypeCodeDto;

/**
@author	kongsj
@date	2012-9-11
 */
public interface AnalysisProductTypeCodeService {
	public List<AnalysisProductTypeCode> queryAnalysisProductTypeCode(String account ,String start,String end);
	
	public List<AnalysisProductTypeCodeDto> domainToDto(AnalysisProductTypeCode analysisProductTypeCode);
}
